package ru.sliva.creative.mode

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player

class KitPVP : Mode("KitPVP", "Сражения между игроками", Material.IRON_SWORD) {

    override fun onPlayerJoin(p: Player, world : World, isOwner: Boolean) {
        p.sendMessage("KitPVP")
    }
}