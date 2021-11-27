package ru.sliva.modules.modulemanager;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.sliva.module.Module;
import ru.sliva.module.ModulePriority;

public class ModuleModuleManager extends Module {

    public ModuleModuleManager(@NotNull Plugin plugin) {
        super(plugin, "ModuleManager", ModulePriority.CRITICAL);
    }

    @Override
    public void onEnable() {
        registerCommand(new ManagerCommand(this));
    }
}
