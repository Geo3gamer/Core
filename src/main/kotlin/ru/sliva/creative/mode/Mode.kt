package ru.sliva.creative.mode

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent

open class Mode(val name: String, val description: String, val icon: Material) {
    open fun onWorldLoad(w: World) {}
    open fun onWorldUnload(w: World) {}
    open fun onPlayerJoin(p: Player, world : World, isOwner: Boolean) {}
    open fun onPlayerQuit(p: Player, world : World, isOwner: Boolean) {}
    open fun tickSecond(w : World) {}
    open fun onProjectileHit(e : ProjectileHitEvent) {}
    open fun onPlayerInteract(e : PlayerInteractEvent) {}
    open fun onEntityDamage(e : EntityDamageEvent) {}
    open fun onPlayerDeath(e : PlayerDeathEvent) {}
}