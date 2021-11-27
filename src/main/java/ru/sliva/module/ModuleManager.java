package ru.sliva.module;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

public final class ModuleManager {

    private static final List<Module> modules = new ArrayList<>();
    private static boolean loaded = false;

    public static void registerModule(@NotNull Module module) {
        modules.add(module);
    }

    public static boolean isLoaded() {
        return loaded;
    }

    @Contract(pure = true)
    public static @NotNull @UnmodifiableView List<Module> getModules() {
        return Collections.unmodifiableList(modules);
    }

    public static @Nullable Module getModuleByName(String name) {
        for(Module module : modules) {
            if(module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public static @NotNull List<Module> getModulesByPlugin(@NotNull Plugin plugin) {
        List<Module> list = new ArrayList<>();
        for(Module module : modules) {
            if(Objects.equals(module.getBukkitPlugin(), plugin)) {
                list.add(module);
            }
        }
        return list;
    }

    public static void enableModules() {
        try {
            for(Module module : modules) {
                if(module.getPriority() == ModulePriority.CRITICAL) {
                    enableModule(module);
                }
            }
            for(Module module : modules) {
                if(module.getPriority() == ModulePriority.NORMAL) {
                    enableModule(module);
                }
            }
            loaded = true;
            Bukkit.getLogger().info("All modules were enabled.");
        } catch (Throwable e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while enabling modules: ", e);
            disableModules();
            Bukkit.shutdown();
        }
    }

    public static void disableModules() {
        try {
            for(Module module : modules) {
                if(module.getPriority() == ModulePriority.NORMAL) {
                    disableModule(module);
                }
            }
            for(Module module : modules) {
                if(module.getPriority() == ModulePriority.CRITICAL) {
                    disableModule(module);
                }
            }
            loaded = false;
        } catch (Throwable e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while disabling modules: ", e);
        }
    }

    public static void reloadModules() {
        try {
            for(Module module : modules) {
                reloadModule(module);
            }
        } catch (Throwable e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error while reloading modules: ", e);
        }
    }

    public static void unloadModules() {
        modules.clear();
    }

    public static void unloadModule(@NotNull Module module) {
        modules.remove(module);
    }

    public static void enableModule(@NotNull Module module) {
        if(!module.isEnabled()) {
            module.enable();
        }
    }

    public static void disableModule(@NotNull Module module) {
        if(module.isEnabled()) {
            module.disable();
        }
    }

    public static void reloadModule(@NotNull Module module) {
        module.reload();
    }
}
