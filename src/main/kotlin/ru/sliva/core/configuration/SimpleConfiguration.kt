package ru.sliva.core.configuration

import org.bukkit.configuration.file.YamlConfiguration
import java.io.IOException
import org.bukkit.configuration.InvalidConfigurationException
import java.io.File

open class SimpleConfiguration(private val configFile: File) : YamlConfiguration() {

    fun saveConfig() {
        try {
            save(configFile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun reloadConfig() {
        try {
            load(configFile)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InvalidConfigurationException) {
            e.printStackTrace()
        }
    }

    init {
        try {
            if (!configFile.exists()) {
                configFile.createNewFile()
            }
            reloadConfig()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}