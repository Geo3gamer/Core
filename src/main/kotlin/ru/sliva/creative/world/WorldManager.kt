package ru.sliva.creative.world

import com.github.f4b6a3.uuid.UuidCreator
import org.apache.commons.io.FileUtils
import org.bukkit.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerChangedWorldEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffectType
import ru.sliva.core.CoreAPI
import ru.sliva.core.configuration.SimpleConfiguration
import ru.sliva.core.plugin.CorePlugin
import ru.sliva.creative.generator.SimpleGenerator
import ru.sliva.creative.mode.Mode
import java.io.File
import java.util.*

class WorldManager(main: CorePlugin) : SimpleConfiguration(File(main.dataFolder, "worlds.yml")), Runnable, Listener {

    val worldMap = HashMap<CreativeWorld, Mode>()
    val hubListener = HubWorldListener(this)

    fun getWorld(p: Player, number: Int): CreativeWorld? {
        val uuid = getWorldUUID(p.uniqueId, number)
        val section = getConfigurationSection(uuid.toString())
        if(section != null) {
            val name = section.getString("name")
            val gen = CoreAPI.creative.generatorMap.getByName(section.getString("generator"))
            if(name != null && gen != null) {
                return CreativeWorld(UUID.fromString(section.getString("ownerUUID")), uuid.toString(), name, gen)
            }
        }
        return null
    }

    fun createNewWorld(p : Player, number : Int, generator : SimpleGenerator) : CreativeWorld? {
        val uuid = getWorldUUID(p.uniqueId, number)
        val section = createSection(uuid.toString())
        section.set("ownerUUID", p.uniqueId.toString())
        section.set("name", "Мир ${p.name}")
        section.set("generator", generator.name)
        saveConfig()
        val world = WorldCreator(uuid.toString()).type(WorldType.FLAT).generator(generator).createWorld()
        while(world == null);
        cleanWorld(world)
        Bukkit.unloadWorld(world, true)
        val cworld = getWorld(p, number)
        if(cworld != null) {
            return cworld
        }
        return null
    }

    fun cleanWorld(world: World) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
        world.setGameRule(GameRule.DO_FIRE_TICK, false)
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true)
        world.setGameRule(GameRule.DO_INSOMNIA, false)
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false)
        world.setGameRule(GameRule.DO_PATROL_SPAWNING, false)
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false)
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false)
        world.worldBorder.size = 160.0
    }

    fun deleteWorld(world : CreativeWorld) {
        val name = world.worldName
        set(name, null)
        saveConfig()
        val w = Bukkit.getWorld(name)
        if(w != null) Bukkit.unloadWorld(w, false)
        val folder = File(Bukkit.getWorldContainer(), name)
        FileUtils.deleteDirectory(folder)
    }

    fun teleport(world : CreativeWorld, mode : Mode, p: Player) {
        val name = world.worldName
        var w = Bukkit.getWorld(name)
        if(w == null) {
            w = WorldCreator(name).generator(world.generator).createWorld()
            while(w == null);
            worldMap[world] = mode
            mode.onWorldLoad(w)
        }
        p.inventory.clear()
        for(effect in PotionEffectType.values()) {
            p.removePotionEffect(effect)
        }
        p.teleport(w.spawnLocation)
        mode.onPlayerJoin(p, w, p.uniqueId == world.owner.uniqueId)
    }

    fun getMainWorld() : World {
        val world = Bukkit.getWorld("world")
        while (world == null);
        return world
    }

    private fun getWorldUUID(playerUUID : UUID, number : Int) : UUID {
        return UuidCreator.getNameBasedMd5("$playerUUID-$number")
    }

    override fun run() {
        if (worldMap.isNotEmpty()) {
            for (cworld in worldMap.keys) {
                val world = Bukkit.getWorld(cworld.worldName)
                if (world != null) {
                    val mode = worldMap.getValue(cworld)
                    mode.tickSecond(world)
                    if (world.players.size < 1) {
                        mode.onWorldUnload(world)
                        worldMap.remove(cworld)
                        Bukkit.unloadWorld(world, true)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onProjectileHit(e : ProjectileHitEvent) {
        for(cworld in worldMap.keys) {
            val mode = worldMap.getValue(cworld)
            val world = Bukkit.getWorld(cworld.worldName)
            if(world != null) {
                if(e.entity.world == world) {
                    mode.onProjectileHit(e)
                }
            }
        }
    }

    @EventHandler
    fun worldChanged(e : PlayerChangedWorldEvent) {
        for(cworld in worldMap.keys) {
            val mode = worldMap.getValue(cworld)
            val world = Bukkit.getWorld(cworld.worldName)
            if(world != null) {
                if(e.from == world) {
                    val p = e.player
                    mode.onPlayerQuit(p, world,p.uniqueId == cworld.owner.uniqueId)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerInteract(e : PlayerInteractEvent) {
        for(cworld in worldMap.keys) {
            val mode = worldMap.getValue(cworld)
            val world = Bukkit.getWorld(cworld.worldName)
            if(world != null) {
                if(e.player.world == world) {
                    mode.onPlayerInteract(e, e.player.uniqueId == cworld.owner.uniqueId)
                }
            }
        }
    }

    @EventHandler
    fun onEntityDamage(e : EntityDamageEvent) {
        for(cworld in worldMap.keys) {
            val mode = worldMap.getValue(cworld)
            val world = Bukkit.getWorld(cworld.worldName)
            if(world != null) {
                if(e.entity.world == world) {
                    mode.onEntityDamage(e)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerDeath(e : PlayerDeathEvent) {
        for(cworld in worldMap.keys) {
            val mode = worldMap.getValue(cworld)
            val world = Bukkit.getWorld(cworld.worldName)
            if(world != null) {
                if(e.entity.world == world) {
                    mode.onPlayerDeath(e)
                }
            }
        }
    }

    init {
        Bukkit.getScheduler().runTaskTimer(main, this, 0, 20)
        Bukkit.getPluginManager().registerEvents(this, main)
        Bukkit.getPluginManager().registerEvents(hubListener, main)
    }
}