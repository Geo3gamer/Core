package ru.sliva.testmodule;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import ru.sliva.menu.Menu;

public class TestMenu extends Menu {

    public TestMenu(@NotNull Player p) {
        super(p, "test", 54);
    }

    @Override
    public void onClick(@NotNull InventoryClickEvent e) {
        e.setCancelled(true);
        getPlayer().sendMessage(Component.text("test"));
    }
}
