package ru.sliva.modules.testmodule;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.sliva.module.Module;
import ru.sliva.module.ModuleCommand;

import java.util.Arrays;
import java.util.List;

public class TestCommand extends ModuleCommand {

    public TestCommand(Module module) {
        super(module, "test", "Testing command", "/test test");
    }

    @Override
    public boolean exec(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if(sender instanceof Player) {
            sender.sendMessage("tsetplayer");
        } else {
            sender.sendMessage("tsetconsole");
        }
        return true;
    }

    @Override
    public @Nullable List<String> complete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return Arrays.asList("1", "2", "3");
    }
}
