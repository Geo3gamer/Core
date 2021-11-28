package ru.sliva.api;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.jetbrains.annotations.NotNull;
import ru.sliva.module.Module;

import java.util.Arrays;
import java.util.List;

public final class Events {

    private static final SimplePluginManager pluginManager = (SimplePluginManager) Bukkit.getPluginManager();

    public static List<Listener> registerListener(@NotNull Plugin plugin, @NotNull Listener... listeners) {
        List<Listener> listenerList = Arrays.asList(listeners);
        listenerList.forEach(listener -> pluginManager.registerEvents(listener, plugin));
        return listenerList;
    }

    public static void unregisterListener(@NotNull Listener... listeners) {
        for(Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
    }

    public static void unregisterListeners(@NotNull Module module) {
        unregisterListener(module.getListeners().toArray(new Listener[0]));
        module.getListeners().clear();
    }
}
