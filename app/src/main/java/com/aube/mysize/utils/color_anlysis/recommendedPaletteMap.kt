package com.aube.mysize.utils.color_anlysis

import com.aube.mysize.presentation.model.clothes.Detail
import com.aube.mysize.presentation.model.clothes.Season
import com.aube.mysize.presentation.model.clothes.Temperature
import com.aube.mysize.presentation.model.clothes.ToneCombination

val recommendedPaletteMap: Map<ToneCombination, List<Int>> = mapOf(
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.VIVID) to listOf(
        0xFFFF4C4C.toInt(),
        0xFFFF9900.toInt(),
        0xFFFFEB3B.toInt(),
        0xFFB2FF59.toInt(),
        0xFF40C4FF.toInt(),
        0xFF00B0FF.toInt(),
        0xFFD500F9.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFFFD700.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.MUTED) to listOf(
        0xFFEF9A9A.toInt(),
        0xFFFFCC80.toInt(),
        0xFFFFF59D.toInt(),
        0xFFAED581.toInt(),
        0xFF81D4FA.toInt(),
        0xFF7986CB.toInt(),
        0xFFCE93D8.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFFFD700.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.DEEP) to listOf(
        0xFFB71C1C.toInt(),
        0xFFEF6C00.toInt(),
        0xFFF9A825.toInt(),
        0xFF9CCC65.toInt(),
        0xFF4DB6AC.toInt(),
        0xFF0288D1.toInt(),
        0xFF8E24AA.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFFFD700.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.SPRING, Detail.SOFT) to listOf(
        0xFFFF8A80.toInt(),
        0xFFFFB74D.toInt(),
        0xFFFFF176.toInt(),
        0xFFAED581.toInt(),
        0xFF4DD0E1.toInt(),
        0xFF64B5F6.toInt(),
        0xFFB39DDB.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFFFD700.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.VIVID) to listOf(
        0xFFD32F2F.toInt(),
        0xFFF57C00.toInt(),
        0xFFFFC107.toInt(),
        0xFF7CB342.toInt(),
        0xFF009688.toInt(),
        0xFF1976D2.toInt(),
        0xFFAB47BC.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFFFD700.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.MUTED) to listOf(
        0xFFE57373.toInt(),
        0xFFFFB74D.toInt(),
        0xFFFFF176.toInt(),
        0xFFC5E1A5.toInt(),
        0xFFAED581.toInt(),
        0xFF80CBC4.toInt(),
        0xFFCE93D8.toInt(),
        0xFFF5F5F5.toInt(),
        0xFF424242.toInt(),
        0xFFFFECB3.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.DEEP) to listOf(
        0xFFC62828.toInt(),
        0xFFEF6C00.toInt(),
        0xFFFFC107.toInt(),
        0xFF558B2F.toInt(),
        0xFF2E7D32.toInt(),
        0xFF00695C.toInt(),
        0xFF6A1B9A.toInt(),
        0xFFF5F5F5.toInt(),
        0xFF212121.toInt(),
        0xFFFFD700.toInt()
    ),
    ToneCombination(Temperature.WARM, Season.AUTUMN, Detail.SOFT) to listOf(
        0xFFE57373.toInt(),
        0xFFFFB74D.toInt(),
        0xFFFFF176.toInt(),
        0xFF9CCC65.toInt(),
        0xFF4DB6AC.toInt(),
        0xFF90A4AE.toInt(),
        0xFFB39DDB.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFFFD700.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.VIVID) to listOf(
        0xFFEC407A.toInt(),
        0xFFFFA000.toInt(),
        0xFFFFF176.toInt(),
        0xFF80CBC4.toInt(),
        0xFF4FC3F7.toInt(),
        0xFF7986CB.toInt(),
        0xFFBA68C8.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFB0BEC5.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.MUTED) to listOf(
        0xFFE57373.toInt(),
        0xFFFFCC80.toInt(),
        0xFFFFF9C4.toInt(),
        0xFFA5D6A7.toInt(),
        0xFFB3E5FC.toInt(),
        0xFF9FA8DA.toInt(),
        0xFFE1BEE7.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF212121.toInt(),
        0xFFCFD8DC.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.DEEP) to listOf(
        0xFFC62828.toInt(),
        0xFFEF6C00.toInt(),
        0xFFFDD835.toInt(),
        0xFF2E7D32.toInt(),
        0xFF039BE5.toInt(),
        0xFF3F51B5.toInt(),
        0xFF7E57C2.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF000000.toInt(),
        0xFFB0BEC5.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.SUMMER, Detail.SOFT) to listOf(
        0xFFF8BBD0.toInt(),
        0xFFFFE0B2.toInt(),
        0xFFFFF9C4.toInt(),
        0xFFDCEDC8.toInt(),
        0xFFB2EBF2.toInt(),
        0xFFB3E5FC.toInt(),
        0xFFE1BEE7.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF9E9E9E.toInt(),
        0xFFE0E0E0.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.WINTER, Detail.VIVID) to listOf(
        0xFFD32F2F.toInt(),
        0xFFFFA000.toInt(),
        0xFFFFEB3B.toInt(),
        0xFF388E3C.toInt(),
        0xFF0288D1.toInt(),
        0xFF303F9F.toInt(),
        0xFF8E24AA.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF000000.toInt(),
        0xFFB0BEC5.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.WINTER, Detail.MUTED) to listOf(
        0xFFE57373.toInt(),
        0xFFFFCC80.toInt(),
        0xFFFFF176.toInt(),
        0xFF81C784.toInt(),
        0xFF81D4FA.toInt(),
        0xFF9FA8DA.toInt(),
        0xFFE1BEE7.toInt(),
        0xFFF5F5F5.toInt(),
        0xFF424242.toInt(),
        0xFFCFD8DC.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.WINTER, Detail.DEEP) to listOf(
        0xFFB71C1C.toInt(),
        0xFFE65100.toInt(),
        0xFFFBC02D.toInt(),
        0xFF1B5E20.toInt(),
        0xFF01579B.toInt(),
        0xFF1A237E.toInt(),
        0xFF4A148C.toInt(),
        0xFFECEFF1.toInt(),
        0xFF000000.toInt(),
        0xFFB0BEC5.toInt()
    ),
    ToneCombination(Temperature.COOL, Season.WINTER, Detail.SOFT) to listOf(
        0xFFF48FB1.toInt(),
        0xFFFFE0B2.toInt(),
        0xFFFFF9C4.toInt(),
        0xFFC8E6C9.toInt(),
        0xFFB2EBF2.toInt(),
        0xFFB3E5FC.toInt(),
        0xFFD1C4E9.toInt(),
        0xFFFFFFFF.toInt(),
        0xFF9E9E9E.toInt(),
        0xFFCFD8DC.toInt()
    )
)
