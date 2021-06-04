package ru.sliva.creative.mode

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent

class TestMode : Mode("Тестовый режим", "Для тестирования логики Mode", Material.ANVIL) {

    override fun onEntityDamage(e: EntityDamageEvent) {
        for(ps in e.entity.world.players) {
            ps.sendMessage("Entity damage")
        }
    }

    override fun onPlayerInteract(e: PlayerInteractEvent, isOwner: Boolean) {
        val p = e.player
        p.sendMessage("Interact")
    }

    override fun onPlayerDeath(e: PlayerDeathEvent) {
        val p = e.entity
        p.sendMessage("Death")
    }

    override fun onPlayerJoin(p: Player, world : World, isOwner: Boolean) {
        for(ps in world.players) {
            ps.sendMessage("${p.name} joined as $isOwner owner")
        }
    }

    override fun onPlayerQuit(p: Player, world : World, isOwner: Boolean) {
        for(ps in world.players) {
            ps.sendMessage("${p.name} left as $isOwner owner")
        }
    }

    override fun onProjectileHit(e: ProjectileHitEvent) {
        val world = e.entity.world
        for(ps in world.players) {
            ps.sendMessage("Pj hit")
        }
    }

    override fun onWorldLoad(w: World) {
        for(ps in w.players) {
            ps.sendMessage("World loaded")
        }
    }

    override fun onWorldUnload(w: World) {
        for(ps in w.players) {
            ps.sendMessage("World unloaded")
        }
    }

    override fun tickSecond(w : World) {
        for(ps in w.players) {
            ps.sendMessage("Tick")
        }
    }
}