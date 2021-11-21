package ru.sliva.core;

import org.bukkit.plugin.java.JavaPlugin;
import ru.sliva.module.ModuleManager;
import ru.sliva.testmodule.TestModule;

public class Core extends JavaPlugin {

    private static Core instance;

    @Override
    public void onEnable() {
        instance = this;
        ModuleManager.registerModule(new TestModule(this));
        ModuleManager.enableModules();
    }

    public static Core getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        ModuleManager.disableModules();
    }
}
