package ru.sliva.module;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;

public class Module implements Listener {

    private final String name;
    private boolean enabled = false;
    private final ModuleLogger logger;
    private final Plugin plugin;
    private final File folder;
    private final ModulePriority priority;
    private ModuleConfig config;

    public Module(@NotNull Plugin plugin, String name, ModulePriority priority) {
        this.name = name;
        this.plugin = plugin;
        this.priority = priority;
        this.logger = new ModuleLogger(this);
        this.folder = new File(plugin.getDataFolder(), name);
    }

    public Module(@NotNull Plugin plugin, String name) {
        this(plugin, name, ModulePriority.NORMAL);
    }

    @SuppressWarnings("unchecked") // Module is always properly casted.
    public static <T> @Nullable T getInstance(final Class<T> moduleClass) {
        if(!moduleClass.equals(Module.class)) {
            for(Module module : ModuleManager.getModules()) {
                if(module.getClass().equals(moduleClass)) {
                    return (T) module;
                }
            }
        }
        return null;
    }

    public void onFirstEnable() {}

    public void onEnable() {}

    public void onDisable() {}

    public void onReload() {
        reload();
    }

    public final void enable() {
        final long time = System.currentTimeMillis();
        if(folder.mkdirs()) {
            onFirstEnable();
        }
        config = new ModuleConfig(this, "config.yml");
        registerListener(this);
        onEnable();
        enabled = true;
        logger.info("Module enabled in " + (System.currentTimeMillis() - time) + " milliseconds.");
    }

    public final void disable() {
        onDisable();
        enabled = false;
        logger.info("Module disabled.");
    }

    public final void reload() {
        onDisable();
        onEnable();
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final @NotNull ModuleLogger getLogger() {
        return logger;
    }

    public final @NotNull String getName() {
        return name;
    }

    public final @NotNull Plugin getBukkitPlugin() {
        return plugin;
    }

    public final @NotNull File getFolder() {
        return folder;
    }

    public final @NotNull ModuleConfig getConfig() {
        return config;
    }

    public final @NotNull ModulePriority getPriority() {
        return priority;
    }

    @Nullable
    public final InputStream getResource(@NotNull String fileName) {
        return plugin.getResource(name + File.separator + fileName);
    }

    public final void saveResource(@NotNull String resourcePath, boolean replace) {
        plugin.saveResource(name + File.separator + resourcePath, replace);
    }

    public final void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public final void registerCommand(ModuleCommand command) {
        Bukkit.getCommandMap().register(name, command);
    }
}
