package ru.sliva.modules.motd;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.destroystokyo.paper.profile.PlayerProfile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.sliva.module.Module;
import ru.sliva.module.ModuleConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModuleMotd extends Module {

    private static ModuleConfig config;
    private static Component motd;
    private static String playerCount;
    private static List<String> players = new ArrayList<>();

    public ModuleMotd(@NotNull Plugin plugin) {
        super(plugin, "Motd");
    }

    @Override
    public void onEnable() {
        config = getConfig();
        motd = getMotdFromConfig();
        playerCount = getPlayerCountFromConfig();
        players = getPlayersFromConfig();
    }

    @Override
    public void onDisable() {

    }

    public static void setMotdToConfig(@NotNull String firstLine, @NotNull String secondLine) {
        config.set("first-line", firstLine);
        config.set("second-line", secondLine);
        config.saveConfig();
        motd = getMotdFromConfig();
    }

    public static @NotNull Component getMotdFromConfig() {
        Component component = Component.text(Objects.requireNonNull(config.getColorizedString("first-line")));
        component = component.append(Component.newline());
        component = component.append(Component.text(Objects.requireNonNull(config.getColorizedString("second-line"))));
        return component;
    }

    public static void setPlayerCountToConfig(@NotNull String playerCount) {
        config.set("online-count", playerCount);
        config.saveConfig();
        ModuleMotd.playerCount = getPlayerCountFromConfig();
    }

    public static @NotNull String getPlayerCountFromConfig() {
        return Objects.requireNonNull(config.getColorizedString("online-count"));
    }

    public static void setPlayersToConfig(@NotNull List<String> players) {
        config.set("players", players);
        config.saveConfig();
        ModuleMotd.players = getPlayersFromConfig();
    }

    public static @NotNull List<String> getPlayersFromConfig() {
        return config.getColorizedList("players");
    }

    public static void setMotd(Component motd) {
        ModuleMotd.motd = motd;
    }

    public static Component getMotd() {
        return motd;
    }

    public static void setPlayerCount(String playerCount) {
        ModuleMotd.playerCount = playerCount;
    }

    public static String getPlayerCount() {
        return playerCount;
    }

    public static void setPlayers(List<String> players) {
        ModuleMotd.players = players;
    }

    public static List<String> getPlayers() {
        return players;
    }

    @EventHandler
    public void onPing(PaperServerListPingEvent e) {
        e.motd(motd);
        e.setProtocolVersion(-1);
        e.setVersion(playerCount);
        e.setNumPlayers(0);
        e.setMaxPlayers(1);
        List<PlayerProfile> profiles = e.getPlayerSample();
        profiles.clear();
        for (String s : players) {
            profiles.add(createProfile(s));
        }
    }

    public final PlayerProfile createProfile(@NotNull String name) {
        return Bukkit.createProfile(name);
    }
}
