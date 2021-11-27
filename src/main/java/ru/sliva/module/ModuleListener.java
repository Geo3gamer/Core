package ru.sliva.module;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.PluginDisableEvent;

public class ModuleListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        if(!ModuleManager.isLoaded()) {
            e.kickMessage(Component.text("Server is loading...").color(NamedTextColor.RED));
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if(!ModuleManager.isLoaded()) {
            e.kickMessage(Component.text("Server is loading...").color(NamedTextColor.RED));
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler
    public void onPluginDisableEvent(PluginDisableEvent e) {
        if(ModuleManager.isLoaded()) {
            for(Module module : ModuleManager.getModulesByPlugin(e.getPlugin())) {
                ModuleManager.disableModule(module);
                ModuleManager.unloadModule(module);
            }
        }
    }
}
