package ru.sliva.creative

import ru.sliva.core.plugin.CorePlugin
import ru.sliva.creative.mode.ModeMap
import ru.sliva.creative.world.WorldManager
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import ru.sliva.creative.command.Hub
import ru.sliva.creative.generator.GeneratorMap

class Creative(private val main: CorePlugin) {

    val modeMap = ModeMap()
    val worldManager = WorldManager(main)
    val generatorMap = GeneratorMap()

    init {
        val map = Bukkit.getCommandMap()
        map.register("creative", Hub(this))
    }

}