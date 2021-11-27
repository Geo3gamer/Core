package ru.sliva.modules.testmodule;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import ru.sliva.modules.menu.DynamicMenu;

import java.util.Objects;

public class PreviousMenu extends DynamicMenu {

    public PreviousMenu(@NotNull Player p) {
        super(p, "previous", 54, true);
    }

    @Override
    public void drawInventory(Inventory inv) {
        inv.addItem(new ItemStack(Material.COAL));
    }

    @Override
    public void onClick(@NotNull InventoryClickEvent e) {
        e.setCancelled(true);
        getPlayer().sendMessage(Objects.requireNonNull(e.getCurrentItem()).getType().toString().toLowerCase());
    }

    @Override
    public void onClose(@NotNull InventoryCloseEvent e) {
        getPlayer().sendMessage(Component.text("testpreviousclose"));
        unload();
    }
}
