package ru.sliva.core.menu

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

open class MenuItemStackUtils {

    fun createItemStack(type: Material, name: String, lore: List<String>, count: Int = 1, glint: Boolean = false): ItemStack {
        val item = ItemStack(type, count)
        val meta = item.itemMeta
        meta.addItemFlags(*ItemFlag.values())
        meta.setDisplayName(name)
        meta.lore = lore;
        item.itemMeta = meta
        if (glint) item.addEnchantment(Enchantment.DURABILITY, 1)
        return item
    }
}