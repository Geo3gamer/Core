package ru.sliva.core.plugin

import org.bukkit.Bukkit
import org.bukkit.generator.ChunkGenerator
import org.bukkit.plugin.java.JavaPlugin
import ru.sliva.core.Core
import ru.sliva.core.CoreAPI
import ru.sliva.core.command.Reload
import ru.sliva.creative.generator.FlatGenerator


class CorePlugin : JavaPlugin() {

    override fun onEnable() {
        dataFolder.mkdirs()

        val map = Bukkit.getCommandMap()
        map.register(name, Reload())

        CoreAPI.core = Core(this)
    }

    override fun getDefaultWorldGenerator(worldName: String, id: String?): ChunkGenerator {
        return FlatGenerator()
    }
}