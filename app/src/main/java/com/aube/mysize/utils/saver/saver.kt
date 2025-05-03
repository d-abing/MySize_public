package com.aube.mysize.utils.saver

import androidx.compose.runtime.saveable.Saver

val mapSaver = Saver<MutableMap<String, Int>, Map<String, Int>>(
    save = { it.toMap() },
    restore = { it.toMutableMap() }
)

val setSaver = Saver<Set<String>, List<String>>(
    save = { it.toList() },
    restore = { it.toSet() }
)