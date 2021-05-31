package ru.sliva.creative.menu.myworlds

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import ru.sliva.core.CoreAPI
import ru.sliva.core.menu.Menu
import ru.sliva.creative.generator.SimpleGenerator
import java.util.*

class CreateWorld(private val mw : MyWorlds, private val p : Player, private val number : Int) : Menu("Выберите генерацию", 9), Runnable {

    private val list = LinkedList<SimpleGenerator>()
    private var new = false

    override fun onClick(e: InventoryClickEvent) {
        e.isCancelled = true
        if(e.currentItem != null) {
            val worldManager = CoreAPI.creative.worldManager
            val gen = list[e.slot]
            p.closeInventory()
            p.sendTitle("", "§aСоздание мира...", 10, 40, 10)
            worldManager.createNewWorld(p, number, gen)
            new = true
            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
        }
    }

    override fun onClose(e: InventoryCloseEvent) {
        if(e.reason == InventoryCloseEvent.Reason.PLAYER) {
            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
        }
    }

    init {
        val inv = inventory
        for((i, gen) in CoreAPI.creative.generatorMap.list.withIndex()) {
            val item = createItemStack(gen.icon, "§f${gen.name}", Collections.emptyList(), i + 1)
            list.add(gen)
            inv.setItem(i, item)
        }
    }

    override fun run() {
        if(new) {
            p.openInventory(MyWorlds(p).inventory)
        } else {
            p.openInventory(mw.inventory)
        }
    }
}