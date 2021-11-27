package ru.sliva.modules.testmodule;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.sliva.module.Module;

public class TestModule extends Module {

    public TestModule(@NotNull Plugin plugin) {
        super(plugin, "Test");
    }

    @Override
    public void onEnable() {
        registerCommand(new TestCommand(this));
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(Component.text("listener works."));
        new TestMenu(p).openInventory();
    }
}
