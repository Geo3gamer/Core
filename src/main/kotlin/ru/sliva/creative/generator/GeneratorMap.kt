package ru.sliva.creative.generator

import java.util.*

class GeneratorMap {

    val list = LinkedList<SimpleGenerator>()

    init {
        addDefaults()
    }

    private fun addDefaults() {
        list.add(FlatGenerator())
        list.add(EmptyGenerator())
    }

    fun getByName(name : String?) : SimpleGenerator? {
        if(name != null) {
            for(gen in list) {
                if(gen.name == name) {
                    return gen
                }
            }
        }
        return null
    }
}