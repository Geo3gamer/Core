package ru.sliva.api.menu;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.sliva.api.Events;
import ru.sliva.api.Schedule;
import ru.sliva.core.Core;

import java.util.Objects;

public abstract class Menu implements Listener {

    private final @NotNull Inventory inv;
    private @Nullable Menu previousMenu;
    private final @NotNull Player p;

    public Menu(@NotNull Player p, String name, int slots) {
        inv = Bukkit.createInventory(null, slots, Component.text(name));
        this.p = p;
        redrawInventory();
        Events.registerListener(Core.getInstance(), this);
    }

    public final void openInventory() {
        p.openInventory(inv);
    }

    public final @NotNull Player getPlayer() {
        return p;
    }

    public final void setPreviousMenu(@NotNull Menu previousMenu) {
        this.previousMenu = previousMenu;
    }

    public abstract void drawInventory(Inventory inv);

    public abstract void onClick(@NotNull InventoryClickEvent e);

    public void onClose(@NotNull InventoryCloseEvent e) {}

    @EventHandler
    public final void onClickEvent(@NotNull InventoryClickEvent e) {
        if(Objects.equals(e.getWhoClicked(), p)) {
            if(Objects.equals(e.getClickedInventory(), inv)) {
                onClick(e);
            }
        }
    }

    @EventHandler
    public void onCloseEvent(@NotNull InventoryCloseEvent e) {
        if(Objects.equals(e.getPlayer(), p)) {
            if(Objects.equals(e.getInventory(), inv)) {
                onClose(e);
                if(e.getReason() == InventoryCloseEvent.Reason.PLAYER) {
                    openPreviousMenu();
                }
            }
        }
    }

    public final void openPreviousMenu() {
        Schedule.later(new PreviousMenuRunnable(), 1);
    }

    public final void redrawInventory() {
        inv.clear();
        drawInventory(inv);
    }

    public final class PreviousMenuRunnable implements Runnable {

        @Override
        public void run() {
            if(previousMenu != null) {
                if(previousMenu instanceof DynamicMenu) {
                    ((DynamicMenu) previousMenu).setClosed(false);
                }
                previousMenu.openInventory();
            }
        }
    }
}
