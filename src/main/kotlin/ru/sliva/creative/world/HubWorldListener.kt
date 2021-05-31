package ru.sliva.creative.world

import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.world.WorldLoadEvent
import org.bukkit.inventory.ItemStack
import ru.sliva.creative.menu.Worlds

class HubWorldListener(private val worldManager : WorldManager) : Listener {

    @EventHandler
    fun onWorldLoad(e : WorldLoadEvent) {
        val world = e.world
        if(world.name == "world") {
            worldManager.cleanWorld(world)
        }
    }

    @EventHandler
    fun onJoin(e : PlayerJoinEvent) {
        val p = e.player
        initHubWorld(p)
        if(p.isOp) {
            p.setDisplayName("§f${p.name}")
            p.setPlayerListName("§f${p.name}")
        } else {
            p.setDisplayName("§7${p.name}")
            p.setPlayerListName("§7${p.name}")
        }
    }

    fun initHubWorld(p : Player) {
        p.inventory.clear()
        p.teleport(worldManager.getMainWorld().spawnLocation)
        p.gameMode = GameMode.ADVENTURE
        for(item in HubItem.values()) {
            p.inventory.addItem(item.item)
        }
    }

    @EventHandler
    fun onInteract(e : PlayerInteractEvent) {
        val p = e.player
        val item = p.inventory.itemInMainHand
        if(p.world == worldManager.getMainWorld()) {
            if(e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
                if(item == HubItem.WORLDS.item) {
                    p.openInventory(Worlds(p).inventory)
                }
                if(e.hasBlock()) {
                    val block = e.clickedBlock
                    if(block != null) {
                        if(block.type.isInteractable) {
                            e.isCancelled = true
                        }
                    }
                }
            }
        }
    }

    private enum class HubItem private constructor(private val material : Material, private val itemName : String, private val lore : String) {

        WORLDS(Material.COMPASS, "§fМиры", "§7Список миров сервера");

        val item = ItemStack(material)

        init {
            val meta = item.itemMeta
            meta.lore = listOf(lore)
            meta.setDisplayName(itemName)
            item.itemMeta = meta
        }

    }

}