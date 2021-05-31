package ru.sliva.creative.generator

import org.bukkit.Material
import org.bukkit.World
import org.bukkit.block.Biome
import java.util.*

class EmptyGenerator : BorderGenerator(5, Biome.THE_VOID, "Пустой мир", Material.COBBLESTONE) {

    override fun generate(world: World, random: Random, x: Int, z: Int, biome: BiomeGrid): ChunkData {
        val chunk = createChunkData(world)
        chunk.setBlock(0, 1, 0, Material.GLASS)
        chunk.setBlock(0, 1, 15, Material.GLASS)
        chunk.setBlock(15, 1, 0, Material.GLASS)
        chunk.setBlock(15, 1, 15, Material.GLASS)
        return chunk
    }
}