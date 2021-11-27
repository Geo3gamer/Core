package ru.sliva.modules.menu;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.sliva.module.Module;
import ru.sliva.module.ModulePriority;

public class ModuleMenu extends Module {

    public ModuleMenu(@NotNull Plugin plugin) {
        super(plugin, "Menu", ModulePriority.CRITICAL);
    }

}
