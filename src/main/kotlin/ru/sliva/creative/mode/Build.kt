package ru.sliva.creative.mode

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.GameMode
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import ru.sliva.creative.menu.Worlds
import ru.sliva.creative.world.HubWorldListener

class Build : Mode("Строительство", "Постройте арену для игры", Material.BRICKS) {

    override fun onWorldLoad(w: World) {
        w.isAutoSave = true
        w.pvp = false
    }

    override fun onPlayerJoin(p: Player, world: World, isOwner: Boolean) {
        val inv = p.inventory
        inv.addItem(BuildItem.WORLDS.item)
        if (isOwner) {
            p.gameMode = GameMode.CREATIVE
            inv.setItem(8, BuildItem.SETTINGS.item)
        } else {
            p.gameMode = GameMode.ADVENTURE
        }
        p.allowFlight = true
        p.sendMessage("Build")
    }

    override fun onPlayerInteract(e: PlayerInteractEvent, isOwner: Boolean) {
        val p = e.player
        val item = p.inventory.itemInMainHand
        if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
            if (item == BuildItem.WORLDS.item) {
                p.openInventory(Worlds(p).inventory)
            }
            if(item == BuildItem.SETTINGS.item) {
                p.sendMessage("TODO")
            }
            if (e.hasBlock()) {
                val block = e.clickedBlock
                if (block != null) {
                    if (block.type.isInteractable) {
                        e.isCancelled = true
                    }
                }
            }
        }
    }

    private enum class BuildItem private constructor(private val material: Material, private val itemName: String, private val lore: String) {

        WORLDS(Material.COMPASS, "§fМиры", "§7Список миров сервера"),
        SETTINGS(Material.CLOCK, "§fНастроить мир", "§7Настройте мир под себя");

        val item = ItemStack(material)

        init {
            val meta = item.itemMeta
            meta.lore = listOf(lore)
            meta.setDisplayName(itemName)
            item.itemMeta = meta
        }

    }
}