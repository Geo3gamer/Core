package ru.sliva.creative.menu.myworlds

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import ru.sliva.core.CoreAPI
import ru.sliva.core.menu.Menu
import ru.sliva.creative.world.CreativeWorld

class DeleteWorld(private val p : Player, private val mw : MyWorlds, private val w : CreativeWorld) : Menu("Вы точно хотите удалить мир?",9), Runnable {

    private var new = false

    override fun onClick(e: InventoryClickEvent) {
        e.isCancelled = true
        if(e.slot <= 3) {
            p.closeInventory()
            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
        }
        if(e.slot >= 5) {
            p.closeInventory()
            CoreAPI.creative.worldManager.deleteWorld(w)
            new = true
            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
        }
    }

    override fun onClose(e: InventoryCloseEvent) {
        if(e.reason == InventoryCloseEvent.Reason.PLAYER) {
            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
        }
    }

    override fun run() {
        if(new) {
            p.openInventory(MyWorlds(p).inventory)
        } else {
            p.openInventory(mw.inventory)
        }
    }

    init {
        val inv = inventory
        val noItem = createItemStack(Material.RED_STAINED_GLASS_PANE, "§cНет", listOf("§7Не удалять мир."))
        val yesItem = createItemStack(Material.GREEN_STAINED_GLASS_PANE, "§aДа", listOf("§7Удалить мир."))
        val signItem = createItemStack(Material.OAK_SIGN, "§fУдалить мир?", emptyList())
        for(i in 0..3) {
            inv.setItem(i, noItem)
        }
        inv.setItem(4, signItem)
        for(i in 5..8) {
            inv.setItem(i, yesItem)
        }
    }
}