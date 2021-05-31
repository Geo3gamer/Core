package ru.sliva.creative.menu.myworlds

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import ru.sliva.core.CoreAPI
import ru.sliva.core.menu.Menu
import ru.sliva.creative.menu.Worlds
import ru.sliva.creative.world.CreativeWorld
import java.util.*

class MyWorlds(private val p: Player) : Menu("Мои миры", 9), Runnable {

    private val list = LinkedList<CreativeWorld?>()

    override fun onClick(e: InventoryClickEvent) {
        e.isCancelled = true
        if(e.currentItem != null && e.currentItem!!.type != Material.AIR) {
            val world = list[e.slot]
            if(world == null) {
                p.closeInventory()
                p.openInventory(CreateWorld(this, p, e.slot + 1).inventory)
            } else {
                if(e.isLeftClick) {
                    p.closeInventory()
                    p.openInventory(SelectMode(this, p, world).inventory)
                } else if(e.isRightClick) {
                    p.closeInventory()
                    p.openInventory(DeleteWorld(p, this, world).inventory)
                }
            }
        }
    }

    override fun onClose(e: InventoryCloseEvent) {
        if(e.reason == InventoryCloseEvent.Reason.PLAYER) {
            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
        }
    }

    override fun run() {
        p.openInventory(Worlds(p).inventory)
    }

    init {
        val inv = inventory
        val worldManager = CoreAPI.creative.worldManager
        for(i in 0..2) {
            val world = worldManager.getWorld(p, i + 1)
            val material : Material
            val itemName : String
            val lore : List<String>
            if(world == null) {
                material = Material.GLASS
                itemName = "§fПусто"
                lore = listOf("§7Создать новый мир.")
            } else {
                material = world.generator.icon
                itemName = "§f${world.name}"
                lore = listOf("§7Владелец: ${world.owner.name}", "§bЛКМ §7- перейти к этому миру", "§eПКМ §7- удалить мир.")
            }
            list.add(world)
            inv.setItem(i, createItemStack(material, itemName, lore, i + 1))
        }
    }
}