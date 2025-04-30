package com.aube.mysize.presentation.model

data class PersonalToneAnalysisResult(
    val temperatureRatio: Map<Temperature, Float>,
    val seasonRatio: Map<Season, Float>,
    val detailRatio: Map<Detail, Float>,
    val bestMatchedColors: List<Int>,
    val recommendedColors: List<Int>,
    val mainTone: ToneCombination
)