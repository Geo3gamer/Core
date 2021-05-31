package ru.sliva.core.menu

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import ru.sliva.core.CoreAPI

abstract class Menu(name: String, slots: Int) : MenuItemStackUtils(), Listener {

    val inventory: Inventory = Bukkit.createInventory(null, slots, name)

    abstract fun onClick(e: InventoryClickEvent)

    open fun onClose(e: InventoryCloseEvent) {}

    @EventHandler
    fun onClickEvent(e: InventoryClickEvent) {
        if (e.clickedInventory == inventory) {
            onClick(e)
        }
    }

    @EventHandler
    fun onCloseEvent(e: InventoryCloseEvent) {
        if (e.inventory == inventory) {
            onClose(e)
        }
    }

    init {
        Bukkit.getPluginManager().registerEvents(this, CoreAPI.corePlugin)
    }
}