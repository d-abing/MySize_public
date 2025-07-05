package com.aube.mysize.utils.recommend

import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.presentation.model.recommend.Gender
import com.aube.mysize.presentation.model.recommend.RecommendedSizeResult
import com.aube.mysize.presentation.model.recommend.SizeDetail

fun buildTopSizeReference(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height.toDouble()
    val weight = bodySize.weight.toDouble()
    val gender = bodySize.gender
    val isFemale = gender == Gender.FEMALE

    val types = listOf(
        "긴소매티", "g맨투맨", "니트", "후드티",
        "셔츠/블라우스", "베스트/뷔스티에",
        "반소매티", "민소매티"
    )

    fun calculate(type: String): SizeDetail {
        // 신체 추정
        val shoulder = bodySize.shoulder?.toDouble() ?: if (isFemale) height * 0.24 else height * 0.26
        val chest = bodySize.chest?.toDouble() ?: if (isFemale) (height + weight) * 0.45 else (height + weight) * 0.5
        val arm = bodySize.arm?.toDouble() ?: if (isFemale) height * 0.30 else height * 0.33

        val weightFactor = when {
            weight >= 90 -> 1.10
            weight >= 75 -> 1.05
            weight <= 55 -> 0.95
            else -> 1.0
        }

        val ease = when (type) {
            "긴소매티" -> listOf(1.0, 3.0, 2.0, 2.0)
            "맨투맨" -> if (isFemale) listOf(4.0, 6.0, 3.0, 3.0) else listOf(2.0, 4.0, 2.0, 2.0)
            "니트" -> listOf(2.0, 4.0, 2.0, 2.0)
            "후드티" -> if (isFemale) listOf(4.0, 6.0, 3.0, 3.0) else listOf(3.0, 5.0, 2.0, 2.0)
            "셔츠/블라우스" -> if (isFemale) listOf(2.0, 4.0, 2.0, 2.0) else listOf(1.5, 3.0, 2.0, 1.5)
            "베스트/뷔스티에" -> listOf(0.0, 3.0, 0.0, 2.0)
            "반소매티" -> listOf(1.5, 3.5, 0.0, 2.0)
            "민소매티" -> listOf(0.0, 3.0, 0.0, 1.5)
            else -> listOf(0.0, 0.0, 0.0, 0.0)
        }

        val (shoulderEase, chestEase, sleeveEase, lengthEase) = ease

        val measurements = mutableMapOf<String, String>()

        if (type != "베스트/뷔스티에" && type != "민소매티") {
            measurements["어깨너비"] = ((shoulder + shoulderEase) * weightFactor).toInt().toString()
        }

        measurements["가슴단면"] = ((chest / 2.0 + chestEase) * weightFactor).toInt().toString()

        if (type != "베스트/뷔스티에" && type != "반소매티" && type != "민소매티") {
            measurements["소매길이"] = ((arm + sleeveEase) * weightFactor).toInt().toString()
        }

        val totalLength = (height * 0.45 + lengthEase) * weightFactor
        measurements["총장"] = totalLength.toInt().toString()

        return SizeDetail(measurements)
    }

    val typeToSizeMap = types.associateWith { calculate(it) }

    return RecommendedSizeResult.Success(
        category = SizeCategory.TOP,
        typeToSizeMap = typeToSizeMap
    )
}


fun buildBottomSizeReference(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height.toDouble()
    val weight = bodySize.weight.toDouble()
    val gender = bodySize.gender
    val isFemale = gender == Gender.FEMALE

    val types = listOf(
        "청바지", "슬랙스", "면바지", "반바지", "트레이닝 팬츠",
        "레깅스", "미니스커트", "미디스커트", "롱스커트"
    )

    fun calculate(type: String): SizeDetail {
        val waist = bodySize.waist?.toDouble() ?: if (isFemale) (height + weight) * 0.25 else (height + weight) * 0.23
        val hip = bodySize.hip?.toDouble() ?: if (isFemale) (height + weight) * 0.30 else (height + weight) * 0.28
        val leg = bodySize.leg?.toDouble() ?: if (isFemale) height * 0.45 else height * 0.48

        val weightFactor = when {
            weight >= 90 -> 1.10
            weight >= 75 -> 1.05
            weight <= 55 -> 0.95
            else -> 1.0
        }

        val ease = when (type) {
            "청바지" -> listOf(2.5, 3.5, 1.5, 2.5)
            "슬랙스" -> listOf(2.0, 3.0, 1.0, 2.0)
            "면바지" -> listOf(2.0, 3.0, 1.5, 2.0)
            "반바지" -> listOf(2.0, 2.5, 0.0, 1.0)
            "트레이닝 팬츠" -> listOf(3.0, 4.0, 2.0, 2.5)
            "레깅스" -> listOf(1.0, 1.5, 1.0, 1.0)
            "미니스커트" -> listOf(1.5, 2.0, 0.0, 1.0)
            "미디스커트" -> listOf(2.0, 2.5, 0.0, 2.0)
            "롱스커트" -> listOf(2.5, 3.0, 0.0, 3.0)
            else -> listOf(0.0, 0.0, 0.0, 0.0)
        }

        val (waistEase, hipEase, inseamEase, lengthEase) = ease

        val measurements = mutableMapOf<String, String>()
        measurements["허리단면"] = ((waist / 2.0 + waistEase) * weightFactor).toInt().toString()
        measurements["엉덩이단면"] = ((hip / 2.0 + hipEase) * weightFactor).toInt().toString()

        if (type != "미니스커트" && type != "미디스커트" && type != "롱스커트") {
            measurements["밑위길이"] = ((leg + inseamEase) * weightFactor).toInt().toString()
        }

        val totalLength = (height * 0.53 + lengthEase) * weightFactor
        measurements["총장"] = totalLength.toInt().toString()

        return SizeDetail(measurements)
    }

    val typeToSizeMap = types.associateWith { calculate(it) }

    return RecommendedSizeResult.Success(
        category = SizeCategory.BOTTOM,
        typeToSizeMap = typeToSizeMap
    )
}


fun buildOuterSizeReference(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height.toDouble()
    val weight = bodySize.weight.toDouble()
    val gender = bodySize.gender
    val isFemale = gender == Gender.FEMALE

    val types = listOf(
        "환절기 코트", "겨울 코트", "롱 패딩", "숏 패딩",
        "패딩 베스트", "카디건", "폴리스", "후드 집업", "재킷"
    )

    fun calculate(type: String): SizeDetail {
        val shoulder = bodySize.shoulder?.toDouble() ?: if (isFemale) height * 0.24 else height * 0.26
        val chest = bodySize.chest?.toDouble() ?: if (isFemale) (height + weight) * 0.45 else (height + weight) * 0.5
        val arm = bodySize.arm?.toDouble() ?: if (isFemale) height * 0.30 else height * 0.33

        val weightFactor = when {
            weight >= 90 -> 1.12
            weight >= 75 -> 1.07
            weight <= 55 -> 0.93
            else -> 1.0
        }

        val ease = when (type) {
            "환절기 코트" -> listOf(3.0, 5.0, 2.5, 3.0)
            "겨울 코트" -> listOf(4.0, 6.0, 3.0, 3.5)
            "롱 패딩" -> listOf(4.0, 7.0, 3.0, 4.0)
            "숏 패딩" -> listOf(3.5, 6.0, 3.0, 3.0)
            "패딩 베스트" -> listOf(0.0, 5.0, 0.0, 2.0)
            "카디건" -> listOf(2.0, 4.0, 2.0, 2.5)
            "폴리스" -> listOf(2.5, 5.0, 2.5, 2.5)
            "후드 집업" -> listOf(3.0, 5.5, 2.5, 2.5)
            "재킷" -> listOf(2.5, 4.5, 2.0, 2.5)
            else -> listOf(0.0, 0.0, 0.0, 0.0)
        }

        val (shoulderEase, chestEase, sleeveEase, lengthEase) = ease

        val measurements = mutableMapOf<String, String>()
        measurements["어깨너비"] = ((shoulder + shoulderEase) * weightFactor).toInt().toString()
        measurements["가슴단면"] = ((chest / 2.0 + chestEase) * weightFactor).toInt().toString()
        measurements["소매길이"] = ((arm + sleeveEase) * weightFactor).toInt().toString()

        val totalLength = (height * 0.5 + lengthEase) * weightFactor
        measurements["총장"] = totalLength.toInt().toString()

        return SizeDetail(measurements)
    }

    val typeToSizeMap = types.associateWith { calculate(it) }

    return RecommendedSizeResult.Success(
        category = SizeCategory.OUTER,
        typeToSizeMap = typeToSizeMap
    )
}

fun buildOnePieceSizeReference(bodySize: BodySize): RecommendedSizeResult {
    val height = bodySize.height.toDouble()
    val weight = bodySize.weight.toDouble()
    val gender = bodySize.gender
    val isFemale = gender == Gender.FEMALE

    val types = listOf("원피스", "점프수트", "멜빵바지")

    fun calculate(type: String): SizeDetail {
        val shoulder = bodySize.shoulder?.toDouble() ?: if (isFemale) height * 0.24 else height * 0.26
        val chest = bodySize.chest?.toDouble() ?: if (isFemale) (height + weight) * 0.45 else (height + weight) * 0.5
        val waist = bodySize.waist?.toDouble() ?: if (isFemale) (height + weight) * 0.25 else (height + weight) * 0.23
        val hip = bodySize.hip?.toDouble() ?: if (isFemale) (height + weight) * 0.30 else (height + weight) * 0.28

        val weightFactor = when {
            weight >= 90 -> 1.12
            weight >= 75 -> 1.07
            weight <= 55 -> 0.93
            else -> 1.0
        }

        val ease = when (type) {
            "원피스" -> listOf(2.0, 4.0, 3.0, 3.5, 3.5)
            "점프수트" -> listOf(2.5, 4.5, 3.5, 4.0, 4.0)
            "멜빵바지" -> listOf(1.5, 3.5, 3.0, 3.5, 3.5)
            else -> listOf(0.0, 0.0, 0.0, 0.0, 0.0)
        }

        val (shoulderEase, chestEase, waistEase, hipEase, lengthEase) = ease

        val measurements = mutableMapOf<String, String>()
        measurements["어깨너비"] = ((shoulder + shoulderEase) * weightFactor).toInt().toString()
        measurements["가슴단면"] = ((chest / 2.0 + chestEase) * weightFactor).toInt().toString()
        measurements["허리단면"] = ((waist / 2.0 + waistEase) * weightFactor).toInt().toString()
        measurements["엉덩이단면"] = ((hip / 2.0 + hipEase) * weightFactor).toInt().toString()

        val totalLength = (height * 0.55 + lengthEase) * weightFactor
        measurements["총장"] = totalLength.toInt().toString()

        return SizeDetail(measurements)
    }

    val typeToSizeMap = types.associateWith { calculate(it) }

    return RecommendedSizeResult.Success(
        category = SizeCategory.ONE_PIECE,
        typeToSizeMap = typeToSizeMap
    )
}


fun buildShoeSizeReference(bodySize: BodySize): RecommendedSizeResult {
    val footLength = bodySize.footLength?.toDouble()
    val footWidth = bodySize.footWidth?.toDouble()

    if (footLength == null) {
        return RecommendedSizeResult.Failure("발 길이 정보가 필요합니다.")
    }

    val types = listOf("운동화", "구두", "부츠", "슬리퍼", "샌들", "로퍼", "플랫슈즈")

    fun calculate(type: String): SizeDetail {
        val ease = when (type) {
            "운동화" -> listOf(5.0, 4.0)
            "구두" -> listOf(4.0, 3.5)
            "부츠" -> listOf(6.0, 4.5)
            "슬리퍼" -> listOf(3.0, 2.0)
            "샌들" -> listOf(3.5, 2.5)
            "로퍼" -> listOf(4.0, 3.0)
            "플랫슈즈" -> listOf(3.5, 2.5)
            else -> listOf(4.0, 3.0)
        }

        val (lengthEase, widthEase) = ease

        val measurements = mutableMapOf<String, String>()
        measurements["발길이"] = (footLength + lengthEase).toInt().toString()

        footWidth?.let {
            measurements["발볼너비"] = (it + widthEase).toInt().toString()
        }

        return SizeDetail(measurements)
    }

    val typeToSizeMap = types.associateWith { calculate(it) }

    return RecommendedSizeResult.Success(
        category = SizeCategory.SHOE,
        typeToSizeMap = typeToSizeMap
    )
}



fun buildAccessorySizeReference(bodySize: BodySize): RecommendedSizeResult {
    val gender = bodySize.gender
    val weight = bodySize.weight.toDouble()
    val isFemale = gender == Gender.FEMALE

    val neck = bodySize.neck?.toDouble() ?: run {
        if (isFemale) {
            when {
                weight <= 45 -> 30.0
                weight <= 55 -> 32.0
                weight <= 65 -> 34.0
                else -> 36.0
            }
        } else {
            when {
                weight <= 55 -> 34.0
                weight <= 70 -> 36.0
                weight <= 85 -> 38.0
                else -> 40.0
            }
        }
    }

    val chainEase = if (isFemale) 8.0 else 10.0
    val chainLength = neck + chainEase

    val sizeDetail = SizeDetail(
        measurements = mapOf(
            "목걸이 체인길이" to chainLength.toInt().toString()
        )
    )

    return RecommendedSizeResult.Success(
        category = SizeCategory.ACCESSORY,
        typeToSizeMap = mapOf("목걸이" to sizeDetail)
    )
}
