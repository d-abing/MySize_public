package com.aube.mysize.utils.save

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.mapSaver
import com.aube.mysize.domain.model.size.SizeCategory

val sizeMapSaver = mapSaver(
    save = { it.mapKeys { entry -> entry.key.name } },
    restore = { map ->
        map.mapKeys { entry -> SizeCategory.valueOf(entry.key) }
            .mapValues { it.value as List<String> }
            .toMutableMap()
    }
)

val setSaver = Saver<Set<String>, List<String>>(
    save = { it.toList() },
    restore = { it.toSet() }
)