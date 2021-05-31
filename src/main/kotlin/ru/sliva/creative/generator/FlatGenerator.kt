package ru.sliva.creative.generator

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import java.util.*

class FlatGenerator : BorderGenerator(5, Biome.MUSHROOM_FIELDS, "Супер-плоский", Material.GRASS_BLOCK) {

    override fun generate(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData {
        val chunk = createChunkData(world)
        for (cx in 0..15) {
            for (cz in 0..15) {
                chunk.setBlock(cx, 0, cz, Material.BEDROCK)
                for (h in 1..2) {
                    chunk.setBlock(cx, h, cz, Material.DIRT)
                }
                chunk.setBlock(cx, 3, cz, Material.GRASS_BLOCK)
            }
        }
        return chunk
    }
}