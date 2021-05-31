package ru.sliva.creative.mode

import java.util.*

class ModeMap {

    val list = LinkedList<Mode>()

    private fun addDefaults() {
        list.add(Build())
        list.add(KitPVP())
        list.add(TestMode())
    }

    init {
        addDefaults()
    }
}