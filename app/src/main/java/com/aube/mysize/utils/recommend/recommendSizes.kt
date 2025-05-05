package com.aube.mysize.utils.recommend

import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.RecommendedSizeResult
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.model.SizeDetail
import com.aube.mysize.utils.roundTo1Decimal
import com.aube.mysize.utils.size.outerTypes

fun recommendTopSizes(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height
    val weight = bodySize.weight
    val gender = bodySize.gender

    val shoulder = bodySize.shoulder
    val chestCircumference = bodySize.chest
    val sleeveLength = bodySize.arm

    var baseShoulder = when (gender) {
        "남성" -> (height * 0.23).toFloat()
        "여성" -> (height * 0.21).toFloat()
        else -> 22f
    }

    var baseChest = when (gender) {
        "남성" -> (((chestCircumference ?: ((weight + height) * 0.25))).toFloat() / 2) - 1.5f
        "여성" -> (((chestCircumference ?: ((weight + height) * 0.23))).toFloat() / 2) - 1.5f
        else -> (((chestCircumference ?: ((weight + height) * 0.24))).toFloat() / 2) - 1.5f
    }

    val baseSleeve = sleeveLength ?: (height * 0.43).toFloat()

    if (shoulder != null) baseShoulder = (baseShoulder + shoulder) / 2
    if (chestCircumference != null) baseChest = (baseChest + chestCircumference / 2 - 1.5f) / 2

    val typeToSizeMap = mapOf(
        "맨투맨" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "니트" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to (baseChest + 1f).roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "후드티" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to (baseChest + 2f).roundTo1Decimal().toString(),
            "소매길이" to (baseSleeve + 1f).roundTo1Decimal().toString()
        )),
        "셔츠/블라우스" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
        "베스트/뷔스티에" to SizeDetail(mapOf(
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
        "긴소매 티" to SizeDetail(mapOf(
            "가슴단면" to baseChest.roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "반소매 티" to SizeDetail(mapOf(
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
        "카라 티" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
        "민소매 티" to SizeDetail(mapOf(
            "가슴단면" to (baseChest - 1f).roundTo1Decimal().toString()
        )),
    )

    val mostSelectedLabel = mapOf(
        "맨투맨" to "L",
        "셔츠/블라우스" to "100",
        "후드티" to "L",
        "긴소매 티" to "M",
        "반소매 티" to "M",
        "카라 티" to "L",
        "민소매 티" to "S",
    )

    return RecommendedSizeResult.Success(SizeCategory.TOP, typeToSizeMap, mostSelectedLabel)
}

fun recommendBottomSizes(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height
    val weight = bodySize.weight
    val gender = bodySize.gender

    val waistCircumference = bodySize.waist
    val hipCircumference = bodySize.hip

    var baseWaist = when (gender) {
        "남성" -> (weight * 0.35f + height * 0.15f)
        "여성" -> (weight * 0.33f + height * 0.13f)
        else -> (weight * 0.34f + height * 0.14f)
    } / 2 - 1f

    var baseHip = when (gender) {
        "남성" -> (weight * 0.4f + height * 0.2f)
        "여성" -> (weight * 0.42f + height * 0.18f)
        else -> (weight * 0.41f + height * 0.19f)
    } / 2

    if (waistCircumference != null) baseWaist = (baseWaist + waistCircumference / 2 - 1f) / 2
    if (hipCircumference != null) baseHip = (baseHip + hipCircumference / 2) / 2

    val typeToSizeMap = mapOf(
        "청바지" to SizeDetail(mapOf(
            "허리단면" to baseWaist.roundTo1Decimal().toString(),
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
        "슬랙스" to SizeDetail(mapOf(
            "허리단면" to (baseWaist - 1f).roundTo1Decimal().toString(),
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
        "면바지" to SizeDetail(mapOf(
            "허리단면" to baseWaist.roundTo1Decimal().toString(),
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
        "반바지" to SizeDetail(mapOf(
            "허리단면" to baseWaist.roundTo1Decimal().toString()
        )),
        "트레이닝팬츠" to SizeDetail(mapOf(
            "허리단면" to (baseWaist + 1f).roundTo1Decimal().toString()
        )),
        "조거팬츠" to SizeDetail(mapOf(
            "허리단면" to baseWaist.roundTo1Decimal().toString()
        )),
        "레깅스" to SizeDetail(mapOf(
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
        "미니스커트" to SizeDetail(mapOf(
            "허리단면" to baseWaist.roundTo1Decimal().toString()
        )),
        "미디스커트" to SizeDetail(mapOf(
            "허리단면" to baseWaist.roundTo1Decimal().toString(),
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
        "롱스커트" to SizeDetail(mapOf(
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
    )

    val mostSelectedLabel = mapOf(
        "청바지" to "28",
        "슬랙스" to "M",
        "반바지" to "M",
        "트레이닝팬츠" to "L",
        "조거팬츠" to "L",
        "레깅스" to "FREE",
        "미니스커트" to "S",
        "롱스커트" to "M",
    )

    return RecommendedSizeResult.Success(SizeCategory.BOTTOM, typeToSizeMap, mostSelectedLabel)
}

fun recommendOuterSizes(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height
    val weight = bodySize.weight
    val gender = bodySize.gender

    val shoulder = bodySize.shoulder
    val chestCircumference = bodySize.chest
    val sleeveLength = bodySize.arm

    var baseShoulder = when (gender) {
        "남성" -> (height * 0.23).toFloat()
        "여성" -> (height * 0.21).toFloat()
        else -> 22f
    }

    var baseChest = when (gender) {
        "남성" -> (((chestCircumference ?: ((weight + height) * 0.25))).toFloat() / 2) - 1.5f
        "여성" -> (((chestCircumference ?: ((weight + height) * 0.23))).toFloat() / 2) - 1.5f
        else -> (((chestCircumference ?: ((weight + height) * 0.24))).toFloat() / 2) - 1.5f
    }

    val baseSleeve = sleeveLength ?: (height * 0.44).toFloat()

    if (shoulder != null) baseShoulder = (baseShoulder + shoulder) / 2
    if (chestCircumference != null) baseChest = (baseChest + chestCircumference / 2 - 1.5f) / 2

    val typeToSizeMap = mapOf(
        "환절기 코트" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "겨울 코트" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 2f).roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "롱 패딩" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 3f).roundTo1Decimal().toString()
        )),
        "숏 패딩" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 2f).roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "패딩 베스트" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 1f).roundTo1Decimal().toString()
        )),
        "카디건" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 1f).roundTo1Decimal().toString(),
            "소매길이" to (baseSleeve - 1f).roundTo1Decimal().toString()
        )),
        "폴리스" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 2f).roundTo1Decimal().toString()
        )),
        "후드 집업" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to (baseChest + 1f).roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "블루종" to SizeDetail(mapOf(
            "어깨너비" to (baseShoulder + 1f).roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
        "무스탕" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 2f).roundTo1Decimal().toString()
        )),
        "퍼 재킷" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 3f).roundTo1Decimal().toString()
        )),
        "아노락 재킷" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 1.5f).roundTo1Decimal().toString()
        )),
        "트레이닝 재킷" to SizeDetail(mapOf(
            "어깨너비" to baseShoulder.roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "사파리 재킷" to SizeDetail(mapOf(
            "가슴단면" to (baseChest + 1f).roundTo1Decimal().toString(),
            "소매길이" to baseSleeve.roundTo1Decimal().toString()
        )),
        "스타디움 재킷" to SizeDetail(mapOf(
            "어깨너비" to (baseShoulder + 1f).roundTo1Decimal().toString(),
            "가슴단면" to (baseChest + 1.5f).roundTo1Decimal().toString()
        )),
        "레더 재킷" to SizeDetail(mapOf(
            "어깨너비" to (baseShoulder + 0.5f).roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
        "트러커 재킷" to SizeDetail(mapOf(
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
        "블레이저 재킷" to SizeDetail(mapOf(
            "어깨너비" to (baseShoulder + 1f).roundTo1Decimal().toString(),
            "가슴단면" to baseChest.roundTo1Decimal().toString()
        )),
    )

    val mostSelectedLabel = outerTypes.associateWith { "L" } // 예시로 모두 L 처리

    return RecommendedSizeResult.Success(SizeCategory.OUTER, typeToSizeMap, mostSelectedLabel)
}

fun recommendOnePieceSizes(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height
    val weight = bodySize.weight
    val gender = bodySize.gender

    val chestCircumference = bodySize.chest
    val waistCircumference = bodySize.waist
    val hipCircumference = bodySize.hip

    var baseChest = when (gender) {
        "남성" -> (weight * 0.38f + height * 0.22f)
        "여성" -> (weight * 0.36f + height * 0.20f)
        else -> (weight * 0.37f + height * 0.21f)
    } / 2 - 1.5f

    var baseWaist = when (gender) {
        "남성" -> (weight * 0.34f + height * 0.18f)
        "여성" -> (weight * 0.33f + height * 0.16f)
        else -> (weight * 0.335f + height * 0.17f)
    } / 2 - 1f

    var baseHip = when (gender) {
        "남성" -> (weight * 0.42f + height * 0.20f)
        "여성" -> (weight * 0.44f + height * 0.18f)
        else -> (weight * 0.43f + height * 0.19f)
    } / 2

    if (chestCircumference != null) {
        baseChest = (baseChest + chestCircumference / 2 - 1.5f) / 2
    }
    if (waistCircumference != null) {
        baseWaist = (baseWaist + waistCircumference / 2 - 1f) / 2
    }
    if (hipCircumference != null) {
        baseHip = (baseHip + hipCircumference / 2) / 2
    }

    val typeToSizeMap = mapOf(
        "원피스" to SizeDetail(mapOf(
            "가슴단면" to baseChest.roundTo1Decimal().toString(),
            "허리단면" to baseWaist.roundTo1Decimal().toString(),
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
        "점프수트" to SizeDetail(mapOf(
            "가슴단면" to baseChest.roundTo1Decimal().toString(),
            "허리단면" to baseWaist.roundTo1Decimal().toString(),
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        )),
        "멜빵바지" to SizeDetail(mapOf(
            "허리단면" to baseWaist.roundTo1Decimal().toString(),
            "엉덩이단면" to baseHip.roundTo1Decimal().toString()
        ))
    )

    val mostSelectedLabel = mapOf(
        "원피스" to "FREE",
        "점프수트" to "M",
    )

    return RecommendedSizeResult.Success(SizeCategory.ONE_PIECE, typeToSizeMap, mostSelectedLabel)
}

fun recommendShoeSizes(bodySize: BodySize): RecommendedSizeResult {
    val footLength = bodySize.footLength ?: return RecommendedSizeResult.Failure("발 길이 정보가 필요합니다")
    val footWidth = bodySize.footWidth

    val baseLength = footLength.roundTo1Decimal()
    val baseWidth = footWidth?.roundTo1Decimal()

    val typeToSizeMap = mapOf(
        "운동화" to buildShoeSizeDetail(baseLength, baseWidth),
        "구두" to buildShoeSizeDetail((baseLength - 0.5f).roundTo1Decimal(), baseWidth),
        "부츠" to buildShoeSizeDetail((baseLength + 0.5f).roundTo1Decimal(), baseWidth),
        "슬리퍼" to buildShoeSizeDetail(baseLength, baseWidth),
        "샌들" to buildShoeSizeDetail(baseLength, baseWidth),
        "로퍼" to buildShoeSizeDetail((baseLength - 0.5f).roundTo1Decimal(), baseWidth),
        "플랫슈즈" to buildShoeSizeDetail((baseLength - 1f).roundTo1Decimal(), baseWidth),
    )

    val mostSelectedLabel = mapOf(
        "운동화" to "260",
        "구두" to "255",
        "샌들" to "260",
    )

    return RecommendedSizeResult.Success(SizeCategory.SHOE, typeToSizeMap, mostSelectedLabel)
}

private fun buildShoeSizeDetail(length: Float, width: Float?): SizeDetail {
    val measurements = mutableMapOf("길이" to length.roundTo1Decimal().toString())
    width?.let {
        measurements["너비"] = it.roundTo1Decimal().toString()
    }
    return SizeDetail(measurements)
}

fun recommendAccessorySizes(bodySize: BodySize): RecommendedSizeResult {
    val gender = bodySize.gender
    val neck = bodySize.neck ?: if(gender == "여성") 35f else 38f

    val necklaceLength = (neck + 5f).roundTo1Decimal()

    val typeToSizeMap = mapOf(
        "목걸이" to SizeDetail(mapOf("체인 길이" to necklaceLength.toString())),
    )

    val mostSelectedLabel = mapOf(
        "목걸이" to "FREE",
    )

    return RecommendedSizeResult.Success(SizeCategory.ACCESSORY, typeToSizeMap, mostSelectedLabel)
}
