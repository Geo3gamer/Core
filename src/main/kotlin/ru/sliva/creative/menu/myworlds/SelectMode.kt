package ru.sliva.creative.menu.myworlds

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import ru.sliva.core.CoreAPI
import ru.sliva.core.menu.Menu
import ru.sliva.creative.mode.Mode
import ru.sliva.creative.world.CreativeWorld
import java.util.*

class SelectMode(private val mw : MyWorlds, private val p : Player, private val world : CreativeWorld) : Menu("Выберите режим игры", 9), Runnable {

    private val list = LinkedList<Mode>()

    override fun onClick(e: InventoryClickEvent) {
        e.isCancelled = true
        if(e.currentItem != null && e.slot <= list.size) {
            val mode = list[e.slot]
            p.closeInventory()
            p.sendTitle("", "§aЗагрузка мира...", 10, 40,     10)

            CoreAPI.creative.worldManager.teleport(world, mode, p)
        }
    }

    override fun onClose(e: InventoryCloseEvent) {
        if(e.reason == InventoryCloseEvent.Reason.PLAYER) {
            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
        }
    }

    override fun run() {
        p.openInventory(mw.inventory)
    }

    init {
        val inv = inventory
        for((i, mode) in CoreAPI.creative.modeMap.list.withIndex()) {
            val item = createItemStack(mode.icon, "§f${mode.name}", listOf("§7${mode.description}"), i + 1)
            inv.setItem(i, item)
            list.add(mode)
        }
    }
}