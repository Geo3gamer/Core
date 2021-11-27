package ru.sliva.modules.testmodule;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.modules.menu.Menu;

public class TestMenu extends Menu {

    public TestMenu(@NotNull Player p) {
        super(p, "test", 54);
    }

    @Override
    public void drawInventory(Inventory inv) {
        inv.addItem(new ItemStack(Material.ACACIA_LOG));
    }

    @Override
    public void onClick(@NotNull InventoryClickEvent e) {
        e.setCancelled(true);
        getPlayer().sendMessage(Component.text("testclick"));
    }

    @Override
    public void onClose(@NotNull InventoryCloseEvent e) {
        getPlayer().sendMessage(Component.text("testclose"));
        setPreviousMenu(new PreviousMenu(getPlayer()));
    }
}
