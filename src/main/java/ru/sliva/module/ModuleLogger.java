package ru.sliva.module;

import org.jetbrains.annotations.NotNull;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ModuleLogger extends Logger {

    private final String name;

    protected ModuleLogger(final @NotNull Module module) {
        super(module.getBukkitPlugin().getDescription().getName(), null);
        setParent(module.getBukkitPlugin().getLogger());
        this.name = "[" + module.getName() + "] ";
    }

    @Override
    public void log(@NotNull LogRecord record) {
        record.setMessage(name + record.getMessage());
        super.log(record);
    }
}
