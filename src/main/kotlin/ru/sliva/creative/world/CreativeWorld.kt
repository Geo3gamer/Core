package ru.sliva.creative.world

import java.util.UUID
import org.bukkit.World
import org.bukkit.OfflinePlayer
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import ru.sliva.creative.generator.SimpleGenerator

class CreativeWorld(private val ownerUUID: UUID, val worldName: String, val name : String, val generator : SimpleGenerator) {

    val owner: OfflinePlayer
        get() = Bukkit.getOfflinePlayer(ownerUUID)
}