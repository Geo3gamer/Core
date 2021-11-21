package ru.sliva.module;

import com.google.common.base.Charsets;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class ModuleConfig extends YamlConfiguration {

    private final File file;
    private final Module module;

    public ModuleConfig(@NotNull Module module, @NotNull String filename) {
        this.module = module;
        this.file = new File(module.getFolder(), filename);
        reloadConfig();
    }

    public void reloadConfig() {
        try {
            if(!file.exists()) {
                if(!saveDefaultConfig()) {
                    file.createNewFile();
                }
            }

            load(file);

            final InputStream defConfigStream = module.getResource(file.getName());

            if (defConfigStream != null) {
                setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
            }
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean saveDefaultConfig() {
        if(module.getResource(file.getName()) != null) {
            module.saveResource(file.getName(), true);
            return true;
        }
        return false;
    }

    public @Nullable String getColorizedString(@NotNull String path) {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getString(path)));
    }

    public @NotNull List<String> getColorizedList(@NotNull String path) {
        List<String> list = getStringList(path);
        for(int i = 0; i < list.size(); i++) {
            list.set(0, ChatColor.translateAlternateColorCodes('&', list.get(0)));
        }
        return list;
    }
}