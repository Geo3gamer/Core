package ru.sliva.api;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import ru.sliva.core.Core;
import ru.sliva.module.Module;
import ru.sliva.module.ModuleCommand;

import java.util.*;

public final class Commands implements Listener {

    private static final SimpleCommandMap bukkitCommandMap = (SimpleCommandMap) Bukkit.getCommandMap();

    public Commands(@NotNull Core instance) {
        Bukkit.getPluginManager().registerEvents(this, instance);
    }

    public static void unregisterCommand(ModuleCommand cmd) {
        Map<String, Command> knownCommands = bukkitCommandMap.getKnownCommands();
        knownCommands.remove(cmd.getName());
        for (String alias : cmd.getAliases()) {
            Command command = knownCommands.get(alias);
            if (command instanceof ModuleCommand moduleCommand) {
                if (Objects.equals(moduleCommand.getModule(), cmd.getModule())) {
                    knownCommands.remove(alias);
                }
            }
        }
        updateCommands();
    }

    public static void unregisterAll() {
        Map<String, Command> knownCommands = bukkitCommandMap.getKnownCommands();
        knownCommands.clear();
        updateCommands();
    }

    public static List<ModuleCommand> registerCommand(ModuleCommand... cmds) {
        List<ModuleCommand> commandList = Arrays.asList(cmds);
        commandList.forEach(command -> bukkitCommandMap.register(command.getModule().getName(), command));
        return commandList;
    }

    public static void unregisterCommands(Module module) {
        module.getCommands().forEach(Commands::unregisterCommand);
        module.getCommands().clear();
    }

    public static List<String> getValidCommands() {
        List<String> list = new ArrayList<>();
        for(Command cmd : bukkitCommandMap.getCommands()) {
            list.add(cmd.getName());
        }
        return list;
    }

    public static boolean isValidCommand(String name) {
        for(String s : getValidCommands()) {
            if(s.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static void updateCommands() {
        Bukkit.getOnlinePlayers().forEach(Player::updateCommands);
    }
}
