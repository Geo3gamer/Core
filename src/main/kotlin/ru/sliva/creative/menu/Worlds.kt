package ru.sliva.creative.menu

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import ru.sliva.core.CoreAPI
import ru.sliva.core.menu.Menu
import ru.sliva.creative.menu.myworlds.MyWorlds
import ru.sliva.creative.world.CreativeWorld
import java.util.*

class Worlds(val p : Player) : Menu("Миры", 54), Runnable {

    private val list = LinkedList<CreativeWorld>()

    override fun onClick(e: InventoryClickEvent) {
        e.isCancelled = true
        if(e.currentItem != null) {
            val worldManager = CoreAPI.creative.worldManager
            when(e.slot) {
                47 -> {
                    worldManager.hubListener.initHubWorld(p)
                }
                51 -> {
                    p.closeInventory()
                    p.openInventory(MyWorlds(p).inventory)
                }
                else -> {
                    if(e.slot < 45 && e.slot <= list.size) {
                        val worldMap = worldManager.worldMap
                        val world = list[e.slot]
                        if(worldMap.containsKey(world)) {
                            val mode = worldMap.getValue(world)
                            p.closeInventory()
                            worldManager.teleport(world, mode, p)
                        } else {
                            Bukkit.getScheduler().runTaskLater(CoreAPI.corePlugin, this, 1)
                        }
                    }
                }
            }
        }
    }

    override fun run() {
        p.openInventory(Worlds(p).inventory)
    }

    init {
        val inv = inventory
        val worlds = CoreAPI.creative.worldManager.worldMap
        for ((i, cworld) in worlds.keys.withIndex()) {
            val mode = worlds.getValue(cworld)
            val world = Bukkit.getWorld(cworld.worldName)
            if (world != null) {
                val item = createItemStack(mode.icon, "§f${cworld.name}", listOf("§7Владелец: ${cworld.owner.name}", ""), world.players.size)
                val lore = item.lore
                if (lore != null) {
                    for (ps in world.players) {
                        lore.add("§8> §r${ps.displayName}")
                    }
                    item.lore = lore
                }
                list.add(cworld)
                inv.setItem(i, item)
            }
        }
        for (i in 45..53) {
            val item: ItemStack = when (i) {
                47 -> createItemStack(Material.ENDER_EYE, "§fТелепортироваться на спаун", emptyList())
                51 -> createItemStack(Material.PAPER, "§fМои миры", listOf("§7Список ваших миров."))
                else -> createItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ", emptyList())
            }
            inv.setItem(i, item)
        }
    }
}