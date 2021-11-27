package ru.sliva.modules.modulemanager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.sliva.module.Module;
import ru.sliva.module.ModuleCommand;
import ru.sliva.module.ModuleManager;
import ru.sliva.module.ModulePriority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerCommand extends ModuleCommand {

    public ManagerCommand(@NotNull Module module) {
        super(module, "module", "Manages all modules", "/module <subcommand> <module>", "modulemanager.module", List.of("modmanager"));
    }

    @Override
    public boolean exec(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(args.length < 1) {
            NamedTextColor serverLoaded = ModuleManager.isLoaded() ? NamedTextColor.WHITE : NamedTextColor.YELLOW;
            List<Module> modules = ModuleManager.getModules();
            Component component = Component.text("Modules (" + modules.size() + "): ").color(serverLoaded);
            for(int i = 0; i < modules.size(); i++) {
                Module module = modules.get(i);
                NamedTextColor color = module.isEnabled() ? NamedTextColor.GREEN : NamedTextColor.RED;
                if(module.getPriority() == ModulePriority.CRITICAL) color = NamedTextColor.YELLOW;
                component = component.append(Component.text(module.getName()).color(color));
                if(i < modules.size() - 1) {
                    component = component.append(Component.text(", ").color(NamedTextColor.WHITE));
                } else {
                    component = component.append(Component.text(".").color(NamedTextColor.WHITE));
                }
            }
            component = component.append(Component.newline());
            component = component.append(Component.newline());
            component = component.append(Component.text("■ - disabled module").color(NamedTextColor.RED));
            component = component.append(Component.newline());
            component = component.append(Component.text("■ - critical module").color(NamedTextColor.YELLOW));
            component = component.append(Component.newline());
            component = component.append(Component.text("■ - enabled module").color(NamedTextColor.GREEN));
            component = component.append(Component.newline());
            component = component.append(Component.newline());
            component = component.append(Component.text("For more commands use " + getUsage()).color(NamedTextColor.GOLD));
            sender.sendMessage(component);
            return true;
        } else {
            String subcommand = args[0];
            if(args.length > 1) {
                Module module = ModuleManager.getModuleByName(args[1]);
                if(module == null) {
                    sender.sendMessage(Component.text("No module found with this name.").color(NamedTextColor.RED));
                    return true;
                }
                if(module.getPriority() == ModulePriority.CRITICAL) {
                    sender.sendMessage(Component.text("Module " + module.getName() + " is critical.").color(NamedTextColor.RED));
                    return true;
                }
                if(subcommand.equalsIgnoreCase("enable")) {
                    if(!module.isEnabled()) {
                        ModuleManager.enableModule(module);
                        sender.sendMessage(Component.text("Module " + module.getName() + " was enabled.").color(NamedTextColor.GREEN));
                        return true;
                    }
                    sender.sendMessage(Component.text("Module " + module.getName() + " already enabled.").color(NamedTextColor.RED));
                    return true;
                } else if(subcommand.equalsIgnoreCase("disable")) {
                    if(module.isEnabled()) {
                        ModuleManager.disableModule(module);
                        sender.sendMessage(Component.text("Module " + module.getName() + " was disabled.").color(NamedTextColor.GREEN));
                        return true;
                    }
                    sender.sendMessage(Component.text("Module " + module.getName() + " already disabled.").color(NamedTextColor.RED));
                    return true;
                } else if(subcommand.equalsIgnoreCase("reload")) {
                    ModuleManager.reloadModule(module);
                    sender.sendMessage(Component.text("Module " + module.getName() + " was reloaded.").color(NamedTextColor.GREEN));
                    return true;
                } else if(subcommand.equalsIgnoreCase("unload")) {
                    ModuleManager.disableModule(module);
                    ModuleManager.unloadModule(module);
                    sender.sendMessage(Component.text("Module " + module.getName() + " was unloaded.").color(NamedTextColor.GREEN));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> complete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1) {
            return Arrays.asList("enable", "disable", "reload", "unload");
        }
        if(args.length == 2) {
            List<Module> modules = ModuleManager.getModules();
            List<String> list = new ArrayList<>();
            String subcommand = args[0];
            if(subcommand.equalsIgnoreCase("enable")) {
                for(Module module : modules) {
                    if(!module.isEnabled()) {
                        list.add(module.getName());
                    }
                }
                return list;
            } else if(subcommand.equalsIgnoreCase("disable")) {
                for(Module module : modules) {
                    if(module.isEnabled()) {
                        list.add(module.getName());
                    }
                }
                return list;
            } else if(subcommand.equalsIgnoreCase("reload") || subcommand.equalsIgnoreCase("unload")) {
                for(Module module : modules) {
                    list.add(module.getName());
                }
                return list;
            }
        }
        return null;
    }
}
