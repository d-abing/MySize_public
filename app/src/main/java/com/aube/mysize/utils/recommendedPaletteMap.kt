package com.aube.mysize.utils

import com.aube.mysize.presentation.model.Detail
import com.aube.mysize.presentation.model.Season
import com.aube.mysize.presentation.model.Temperature
import com.aube.mysize.presentation.model.ToneCombination

val recommendedPaletteMap: Map<ToneCombination, List<Int>> = mapOf(
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.VIVID) to listOf(
        0xFFFFC1CC.toInt(), 0xFFFFE4B5.toInt(), 0xFFFFDAB9.toInt(),
        0xFFFFB6C1.toInt(), 0xFFFFF176.toInt(), 0xFFFFF8E1.toInt(),
        0xFFFFD54F.toInt(), 0xFFFFAB91.toInt(), 0xFFF48FB1.toInt(), 0xFFFFECB3.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.MUTED) to listOf(
        0xFFFFE0B2.toInt(), 0xFFFFCCBC.toInt(), 0xFFF8BBD0.toInt(),
        0xFFFFE082.toInt(), 0xFFFFCDD2.toInt(), 0xFFFFF59D.toInt(),
        0xFFFFF3E0.toInt(), 0xFFFFE0B2.toInt(), 0xFFFFEBEE.toInt(), 0xFFFFFDE7.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.DEEP) to listOf(
        0xFFFF8A65.toInt(), 0xFFFF7043.toInt(), 0xFFFF5722.toInt(),
        0xFFFFA726.toInt(), 0xFFFF9800.toInt(), 0xFFFFB74D.toInt(),
        0xFFFF7043.toInt(), 0xFFFF8A65.toInt(), 0xFFD84315.toInt(), 0xFFFFAB91.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.SOFT) to listOf(
        0xFFFFF3E0.toInt(), 0xFFFFECB3.toInt(), 0xFFFFF8E1.toInt(),
        0xFFFFFDE7.toInt(), 0xFFFFE0B2.toInt(), 0xFFFFCC80.toInt(),
        0xFFFFD180.toInt(), 0xFFFFE57F.toInt(), 0xFFFFF9C4.toInt(), 0xFFFFF176.toInt()
    ),

    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.VIVID) to listOf(
        0xFFD84315.toInt(), 0xFFFF7043.toInt(), 0xFFFFA726.toInt(),
        0xFFFF8A65.toInt(), 0xFFFFB74D.toInt(), 0xFFFF9800.toInt(),
        0xFFE65100.toInt(), 0xFFFFB300.toInt(), 0xFFFFC107.toInt(), 0xFFFFE082.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.MUTED) to listOf(
        0xFFA1887F.toInt(), 0xFFBCAAA4.toInt(), 0xFFD7CCC8.toInt(),
        0xFFE0B2AA.toInt(), 0xFFD7CCC8.toInt(), 0xFFFFE0B2.toInt(),
        0xFFBCAAA4.toInt(), 0xFFFFCCBC.toInt(), 0xFFFFF3E0.toInt(), 0xFFFFECB3.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.DEEP) to listOf(
        0xFF4E342E.toInt(), 0xFF5D4037.toInt(), 0xFF6D4C41.toInt(),
        0xFF8D6E63.toInt(), 0xFFA1887F.toInt(), 0xFF795548.toInt(),
        0xFFBF360C.toInt(), 0xFF6D4C41.toInt(), 0xFF4E342E.toInt(), 0xFFD84315.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.SOFT) to listOf(
        0xFFFFF3E0.toInt(), 0xFFFFECB3.toInt(), 0xFFFFE0B2.toInt(),
        0xFFFBE9E7.toInt(), 0xFFFFF8E1.toInt(), 0xFFFFFDE7.toInt(),
        0xFFFFD180.toInt(), 0xFFFFCC80.toInt(), 0xFFFFE57F.toInt(), 0xFFFFF9C4.toInt()
    ),

    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.VIVID) to listOf(
        0xFF81D4FA.toInt(), 0xFFBA68C8.toInt(), 0xFF4FC3F7.toInt(),
        0xFF9575CD.toInt(), 0xFFCE93D8.toInt(), 0xFF7986CB.toInt(),
        0xFF64B5F6.toInt(), 0xFF90CAF9.toInt(), 0xFFB39DDB.toInt(), 0xFFB388FF.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.MUTED) to listOf(
        0xFFB0BEC5.toInt(), 0xFFCFD8DC.toInt(), 0xFFECEFF1.toInt(),
        0xFF90A4AE.toInt(), 0xFFB2DFDB.toInt(), 0xFFB3E5FC.toInt(),
        0xFFB2EBF2.toInt(), 0xFFDCEDC8.toInt(), 0xFFE1BEE7.toInt(), 0xFFF8BBD0.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.DEEP) to listOf(
        0xFF512DA8.toInt(), 0xFF303F9F.toInt(), 0xFF1976D2.toInt(),
        0xFF283593.toInt(), 0xFF1A237E.toInt(), 0xFF311B92.toInt(),
        0xFF3F51B5.toInt(), 0xFF0D47A1.toInt(), 0xFF01579B.toInt(), 0xFF6200EA.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.SOFT) to listOf(
        0xFFE3F2FD.toInt(), 0xFFE8EAF6.toInt(), 0xFFF3E5F5.toInt(),
        0xFFFCE4EC.toInt(), 0xFFE1F5FE.toInt(), 0xFFE0F7FA.toInt(),
        0xFFF8BBD0.toInt(), 0xFFBBDEFB.toInt(), 0xFFB3E5FC.toInt(), 0xFFD1C4E9.toInt()
    ),

    ToneCombination(Temperature.COOL, Season.WINTER, Detail.VIVID) to listOf(
        0xFF00BCD4.toInt(), 0xFF448AFF.toInt(), 0xFF536DFE.toInt(),
        0xFF651FFF.toInt(), 0xFF3D5AFE.toInt(), 0xFF00ACC1.toInt(),
        0xFF1DE9B6.toInt(), 0xFF00E5FF.toInt(), 0xFF18FFFF.toInt(), 0xFF40C4FF.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.WINTER, Detail.MUTED) to listOf(
        0xFFB0BEC5.toInt(), 0xFFCFD8DC.toInt(), 0xFFECEFF1.toInt(),
        0xFF90A4AE.toInt(), 0xFFB2DFDB.toInt(), 0xFFB3E5FC.toInt(),
        0xFFB2EBF2.toInt(), 0xFFDCEDC8.toInt(), 0xFFE1BEE7.toInt(), 0xFFF8BBD0.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.WINTER, Detail.DEEP) to listOf(
        0xFF263238.toInt(), 0xFF37474F.toInt(), 0xFF455A64.toInt(),
        0xFF212121.toInt(), 0xFF1B1B1B.toInt(), 0xFF000000.toInt(),
        0xFF424242.toInt(), 0xFF303030.toInt(), 0xFF2C3E50.toInt(), 0xFF546E7A.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.WINTER, Detail.SOFT) to listOf(
        0xFFE0F7FA.toInt(), 0xFFB2EBF2.toInt(), 0xFFB3E5FC.toInt(),
        0xFFBBDEFB.toInt(), 0xFFE1F5FE.toInt(), 0xFFE3F2FD.toInt(),
        0xFFFCE4EC.toInt(), 0xFFEDE7F6.toInt(), 0xFFF8BBD0.toInt(), 0xFFD1C4E9.toInt()
    ),
)
