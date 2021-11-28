package ru.sliva.api;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import ru.sliva.module.Module;
import ru.sliva.module.ModuleCommand;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Commands {

    private static final SimpleCommandMap bukkitCommandMap = (SimpleCommandMap) Bukkit.getCommandMap();

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

    public static List<ModuleCommand> registerCommand(ModuleCommand... cmds) {
        List<ModuleCommand> commandList = Arrays.asList(cmds);
        commandList.forEach(command -> bukkitCommandMap.register(command.getModule().getName(), command));
        return commandList;
    }

    public static void unregisterCommands(Module module) {
        module.getCommands().forEach(Commands::unregisterCommand);
        module.getCommands().clear();
    }

    public static void updateCommands() {
        Bukkit.getOnlinePlayers().forEach(Player::updateCommands);
    }
}
