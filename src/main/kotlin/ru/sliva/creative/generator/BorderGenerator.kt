package ru.sliva.creative.generator

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import java.util.*
import kotlin.math.abs

abstract class BorderGenerator(private val chunks: Int, private val biomes : Biome, name: String, icon: Material) : SimpleGenerator(name, icon) {

    override fun generateChunkData(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData {
        var rx = x
        var rz = z

        if (x >= 0) rx++
        if (z >= 0) rz++

        for (cx in 0..15) {
            for (cz in 0..15) {
                var cy = 0
                while (cy < world.maxHeight) {
                    biome.setBiome(cx, cy, cz, biomes)
                    cy += 4
                }
            }
        }

        if (abs(rx) <= chunks && abs(rz) <= chunks) {
            return generate(world, random, x, z, biome)
        }
        return createChunkData(world)
    }

    abstract fun generate(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData
}