package ru.sliva.core

import org.bukkit.Bukkit
import ru.sliva.core.plugin.CorePlugin
import ru.sliva.creative.Creative

class Core(val corePlugin: CorePlugin) {
    val creative = Creative(corePlugin)
}