package ru.sliva.testmodule;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.sliva.module.Module;
import ru.sliva.module.ModuleConfig;

import java.util.logging.Logger;

public class TestModule extends Module {

    public TestModule(Plugin plugin) {
        super("test", plugin);
    }

    @Override
    public void onEnable() {
        ModuleConfig config = getConfig();
        config.set("test", "value");
        config.saveConfig();
        Logger log = getLogger();
        registerCommand(new TestCommand(this));
        log.info("module enabled successfully");
    }

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(Component.text("listener works."));
    }
}
