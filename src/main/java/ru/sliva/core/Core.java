package ru.sliva.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sliva.module.ModuleListener;
import ru.sliva.module.ModuleManager;
import ru.sliva.modules.modulemanager.ModuleModuleManager;
import ru.sliva.modules.motd.ModuleMotd;
import ru.sliva.modules.testmodule.TestModule;

public class Core extends JavaPlugin {

    private static Core instance;

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new ModuleListener(), this);
        ModuleManager.registerModule(new ModuleModuleManager(this));
        ModuleManager.registerModule(new TestModule(this));
        ModuleManager.registerModule(new ModuleMotd(this));
        ModuleManager.enableModules();
    }

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        ModuleManager.disableModules();
        ModuleManager.unloadModules();
    }
}
