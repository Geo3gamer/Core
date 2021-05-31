package ru.sliva.creative.generator

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.World
import org.bukkit.generator.ChunkGenerator
import java.util.*

open class SimpleGenerator(val name : String, val icon : Material) : ChunkGenerator() {

    override fun getFixedSpawnLocation(world: World, random: Random): Location? {
        if (!world.isChunkLoaded(0, 0)) {
            world.loadChunk(0, 0)
        }
        val highestBlock = world.getHighestBlockYAt(0, 0)

        return if (highestBlock <= 0 && world.getBlockAt(0, 0, 0).type == Material.AIR) {
            Location(world, 0.0, 4.0, 0.0)
        } else Location(world, 0.0, highestBlock.toDouble() + 1, 0.0)
    }
}