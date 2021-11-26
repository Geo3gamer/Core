package ru.sliva.testmodule;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.sliva.module.Module;

public class TestModule extends Module {

    public TestModule(Plugin plugin) {
        super("test", plugin);
    }

    @Override
    public void onEnable() {

    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(Component.text("listener works."));
        new TestMenu(p).openInventory();
    }
}
