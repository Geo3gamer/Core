package ru.sliva.module;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public abstract class ModuleCommand extends Command {

    private final Module module;

    public ModuleCommand(@NotNull Module module, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.module = module;
    }

    public ModuleCommand(@NotNull Module module, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull String permission, @NotNull List<String> aliases) {
        this(module, name, description, usageMessage, aliases);
        setPermission(permission);
    }

    public ModuleCommand(@NotNull Module module, @NotNull String name, @NotNull String description, @NotNull String usageMessage) {
        this(module, name, description, usageMessage, Collections.emptyList());
    }

    public ModuleCommand(@NotNull Module module, @NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull String permission) {
        this(module, name, description, usageMessage, permission, Collections.emptyList());
    }

    public final @NotNull Module getModule() {
        return module;
    }

    public abstract boolean exec(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args);

    public @Nullable List<String> complete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return null;
    }

    public final @NotNull List<String> playerComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return super.tabComplete(sender, alias, args);
    }

    @Override
    public final boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        boolean success;

        if (!module.isEnabled()) {
            sender.sendMessage("Cannot execute command '" + commandLabel + "' in module " + module.getName() + " - module is disabled.");
            return true;
        }

        if (!testPermission(sender)) {
            return true;
        }

        try {
            success = exec(sender, commandLabel, args);
        } catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in module " + module.getName(), ex);
        }

        if (!success && usageMessage.length() > 0) {
            for (String line : usageMessage.split("\n")) {
                sender.sendMessage(line);
            }
        }

        return success;
    }

    @Override
    public final @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        List<String> completions;
        try {
            completions = complete(sender, alias, args);
            if(completions == null) {
                completions = Collections.emptyList();
            }
        } catch (Throwable ex) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            for (String arg : args) {
                message.append(arg).append(' ');
            }
            message.deleteCharAt(message.length() - 1).append("' in module ").append(module.getName());
            throw new CommandException(message.toString(), ex);
        }
        return completions;
    }
}
