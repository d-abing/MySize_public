package com.aube.mysize.utils

import com.aube.mysize.domain.model.size.BodySize
import com.aube.mysize.presentation.model.RecommendedSizeResult
import com.aube.mysize.presentation.model.SizeCategory
import com.aube.mysize.presentation.model.SizeDetail

fun recommendTopSizes(bodySize: BodySize): RecommendedSizeResult {
    val chest = bodySize.chest ?: return RecommendedSizeResult.Failure("가슴둘레 정보가 필요합니다.")
    val shoulder = bodySize.shoulder ?: return RecommendedSizeResult.Failure("어깨너비 정보가 필요합니다.")

    val typeToSizeMap = mapOf(
        "맨투맨" to SizeDetail(mapOf("가슴단면" to chest, "어깨너비" to shoulder)),
        "셔츠/블라우스" to SizeDetail(mapOf("어깨너비" to shoulder)),
        "후드티" to SizeDetail(mapOf("가슴단면" to chest + 2f)),
        "긴소매 티" to SizeDetail(mapOf("가슴단면" to chest)),
        "반소매 티" to SizeDetail(mapOf("가슴단면" to chest)),
        "카라 티" to SizeDetail(mapOf("어깨너비" to shoulder)),
        "민소매 티" to SizeDetail(mapOf("가슴단면" to chest)),
        "기타 상의" to SizeDetail(mapOf("가슴단면" to chest))
    )

    val mostSelectedLabel = mapOf(
        "맨투맨" to "L",
        "셔츠/블라우스" to "100",
        "후드티" to "L",
        "긴소매 티" to "M",
        "반소매 티" to "M",
        "카라 티" to "L",
        "민소매 티" to "S",
        "기타 상의" to "M"
    )

    return RecommendedSizeResult.Success(SizeCategory.TOP, typeToSizeMap, mostSelectedLabel)
}

fun recommendBottomSizes(bodySize: BodySize): RecommendedSizeResult {
    val waist = bodySize.waist ?: return RecommendedSizeResult.Failure("허리둘레 정보가 필요합니다.")
    val hip = bodySize.hip ?: return RecommendedSizeResult.Failure("엉덩이둘레 정보가 필요합니다.")

    val typeToSizeMap = mapOf(
        "청바지" to SizeDetail(mapOf("허리단면" to waist)),
        "슬랙스" to SizeDetail(mapOf("허리단면" to waist - 1f)),
        "반바지" to SizeDetail(mapOf("허리단면" to waist)),
        "트레이닝팬츠" to SizeDetail(mapOf("허리단면" to waist + 1f)),
        "조거팬츠" to SizeDetail(mapOf("허리단면" to waist)),
        "레깅스" to SizeDetail(mapOf("엉덩이단면" to hip)),
        "미니스커트" to SizeDetail(mapOf("허리단면" to waist)),
        "롱스커트" to SizeDetail(mapOf("엉덩이단면" to hip)),
        "기타 하의" to SizeDetail(mapOf("허리단면" to waist))
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
        "기타 하의" to "M"
    )

    return RecommendedSizeResult.Success(SizeCategory.BOTTOM, typeToSizeMap, mostSelectedLabel)
}

fun recommendOuterSizes(bodySize: BodySize): RecommendedSizeResult {
    val chest = bodySize.chest ?: return RecommendedSizeResult.Failure("가슴둘레 정보가 필요합니다.")
    val shoulder = bodySize.shoulder ?: return RecommendedSizeResult.Failure("어깨너비 정보가 필요합니다.")

    val types = listOf(
        "환절기 코트", "겨울 코트", "롱 패딩", "숏 패딩", "블루종", "트러커 재킷", "블레이저 재킷"
    )

    val typeToSizeMap = types.associateWith {
        SizeDetail(mapOf("가슴단면" to chest, "어깨너비" to shoulder))
    }

    val mostSelectedLabel = types.associateWith { "L" } // 예시로 모두 L 처리

    return RecommendedSizeResult.Success(SizeCategory.OUTER, typeToSizeMap, mostSelectedLabel)
}

fun recommendOnePieceSizes(bodySize: BodySize): RecommendedSizeResult {
    val chest = bodySize.chest ?: return RecommendedSizeResult.Failure("가슴둘레 정보가 필요합니다.")
    val waist = bodySize.waist ?: return RecommendedSizeResult.Failure("허리둘레 정보가 필요합니다.")
    val hip = bodySize.hip ?: return RecommendedSizeResult.Failure("엉덩이둘레 정보가 필요합니다.")

    val typeToSizeMap = mapOf(
        "원피스" to SizeDetail(mapOf("가슴단면" to chest, "허리단면" to waist, "엉덩이단면" to hip)),
        "점프수트" to SizeDetail(mapOf("가슴단면" to chest, "허리단면" to waist, "엉덩이단면" to hip)),
        "기타 일체형" to SizeDetail(mapOf("가슴단면" to chest, "허리단면" to waist, "엉덩이단면" to hip))
    )

    val mostSelectedLabel = mapOf(
        "원피스" to "FREE",
        "점프수트" to "M",
        "기타 일체형" to "M"
    )

    return RecommendedSizeResult.Success(SizeCategory.ONE_PIECE, typeToSizeMap, mostSelectedLabel)
}

fun recommendShoeSizes(bodySize: BodySize): RecommendedSizeResult {
    val footLength = bodySize.footLength ?: return RecommendedSizeResult.Failure("발길이 정보가 필요합니다.")

    val typeToSizeMap = mapOf(
        "운동화" to SizeDetail(mapOf("발길이" to footLength)),
        "구두" to SizeDetail(mapOf("발길이" to footLength - 0.5f)),
        "샌들" to SizeDetail(mapOf("발길이" to footLength)),
        "기타 신발" to SizeDetail(mapOf("발길이" to footLength))
    )

    val mostSelectedLabel = mapOf(
        "운동화" to "260",
        "구두" to "255",
        "샌들" to "260",
        "기타 신발" to "260"
    )

    return RecommendedSizeResult.Success(SizeCategory.SHOE, typeToSizeMap, mostSelectedLabel)
}

fun recommendAccessorySizes(bodySize: BodySize): RecommendedSizeResult {
    val neck = bodySize.neck ?: return RecommendedSizeResult.Failure("목둘레 정보가 필요합니다.")

    val typeToSizeMap = mapOf(
        "목걸이" to SizeDetail(mapOf("목둘레" to neck)),
        "기타 악세사리" to SizeDetail(mapOf("목둘레" to neck))
    )

    val mostSelectedLabel = mapOf(
        "목걸이" to "FREE",
        "기타 악세사리" to "FREE"
    )

    return RecommendedSizeResult.Success(SizeCategory.ACCESSORY, typeToSizeMap, mostSelectedLabel)
}
