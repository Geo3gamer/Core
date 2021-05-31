package ru.sliva.core

import ru.sliva.core.plugin.CorePlugin
import ru.sliva.creative.Creative

object CoreAPI {

    lateinit var core: Core

    @JvmStatic
    val corePlugin: CorePlugin
        get() = core.corePlugin

    @JvmStatic
    val creative: Creative
        get() = core.creative
}