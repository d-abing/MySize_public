package com.aube.mysize.presentation.model

data class ToneCombination(
    val temperature: Temperature,
    val season: Season,
    val detailTone: Detail
) {
    val displayName: String
        get() = "${temperature.toDisplayName()} ${season.toDisplayName()} ${detailTone.toDisplayName()}"
}