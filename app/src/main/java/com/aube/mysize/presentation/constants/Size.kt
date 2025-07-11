package com.aube.mysize.presentation.constants

import com.aube.mysize.domain.model.clothes.MemoVisibility
import com.aube.mysize.domain.model.size.AccessorySize
import com.aube.mysize.domain.model.size.BottomSize
import com.aube.mysize.domain.model.size.OnePieceSize
import com.aube.mysize.domain.model.size.OuterSize
import com.aube.mysize.domain.model.size.ShoeSize
import com.aube.mysize.domain.model.size.Size
import com.aube.mysize.domain.model.size.SizeCategory
import com.aube.mysize.domain.model.size.TopSize

val genderTypes = listOf("남성", "여성")

val topTypes = listOf(
    "맨투맨", "니트", "후드티", "셔츠/블라우스", "베스트/뷔스티에",
    "긴소매 티", "반소매 티", "카라 티", "민소매 티", "기타 상의"
)
val bottomTypes = listOf(
    "청바지", "슬랙스", "면바지", "반바지", "트레이닝팬츠", "조거팬츠", "레깅스",
    "미니스커트", "미디스커트", "롱스커트", "기타 하의"
)
val outerTypes = listOf("환절기 코트", "겨울 코트", "롱 패딩", "숏 패딩", "패딩 베스트",
    "카디건", "폴리스", "후드 집업", "블루종", "무스탕", "퍼 재킷", "아노락 재킷",
    "트레이닝 재킷", "사파리 재킷", "스타디움 재킷", "레더 재킷", "트러커 재킷", "블레이저 재킷", "기타 아우터")
val onePieceTypes = listOf("원피스", "점프수트", "멜빵바지", "기타")
val shoeTypes = listOf(
    "운동화", "구두", "부츠", "슬리퍼", "샌들", "로퍼", "플랫슈즈", "기타"
)
val accessoryTypes = listOf("반지", "팔찌", "목걸이", "모자", "벨트", "시계", "가방", "기타")


val topFits = listOf("슬림핏", "레귤러핏", "오버핏")
val bottomFits = listOf("슬림핏", "레귤러핏", "루즈핏", "테이퍼드핏")
val outerFits = listOf("슬림핏", "레귤러핏", "오버핏")
val onePieceFits = listOf("슬림핏", "레귤러핏", "오버핏")
val shoeFits = listOf("작음", "딱 맞음", "큼")
val accessoryFits = listOf("작음", "딱 맞음", "큼")

val topKeys = listOf(
    "어깨", "가슴", "소매길이", "총", // 한글
    "SHOULDER", "CHEST", "SLEEVE", "LENGTH"  // 영어
)
val bottomKeys = listOf(
    "허리", "밑위", "엉덩이", "허벅지", "힙", "밑단", "총", // 한글
    "WAIST", "RISE", "HIP", "THIGH", "HEM", "LENGTH"  // 영어
)
val outerKeys = listOf(
    "어깨", "가슴", "소매길이", "총", // 한글
    "SHOULDER", "CHEST", "SLEEVE", "LENGTH"  // 영어
)
val onePieceKeys = listOf(
    "어깨", "가슴", "허리", "엉덩이", "소매길이", "밑위", "허벅지", "밑단", "총", // 한글
    "SHOULDER", "CHEST", "WAIST", "HIP", "SLEEVE", "RISE", "THIGH", "HEM", "LENGTH"  // 영어
)
val shoeKeys = listOf(
    "길이", "너비", "발볼", // 한글
    "LENGTH", "WIDTH" // 영어
)

fun normalizeTopKey(original: String): String {
    val upper = original.uppercase()

    return when {
        "SHOULDER" in upper || "어깨" in original -> "SHOULDER"
        "CHEST" in upper || "BUST" in upper || "가슴" in original -> "CHEST"
        ("SLEEVE" in upper || "소매 길이" in original || "소매길이" in original) && ("HEM" !in upper) -> "SLEEVE"
        "LENGTH" in upper || "총" in original -> "LENGTH"
        else -> upper
    }
}

fun normalizeBottomKey(original: String): String {
    val upper = original.uppercase()
    return when {
        "WAIST" in upper || "허리" in original -> "WAIST"
        "RISE" in upper || "밑위" in original -> "RISE"
        "HIP" in upper || "엉덩이" in original || "힙" in original -> "HIP"
        "THIGH" in upper || "허벅지" in original -> "THIGH"
        "HEM" in upper || "밑단" in original -> "HEM"
        "LENGTH" in upper || "총" in original -> "LENGTH"
        else -> upper
    }
}

fun normalizeOuterKey(original: String): String {
    val upper = original.uppercase()

    return when {
        "SHOULDER" in upper || "어깨" in original -> "SHOULDER"
        "CHEST" in upper || "BUST" in upper || "가슴" in original -> "CHEST"
        "SLEEVE" in upper || "소매 길이" in original || "소매길이" in original -> "SLEEVE"
        "LENGTH" in upper || "총" in original -> "LENGTH"
        else -> upper
    }
}

fun normalizeOnePieceKey(original: String): String {
    val upper = original.uppercase()

    return when {
        "SHOULDER" in upper || "어깨" in original -> "SHOULDER"
        "CHEST" in upper || "BUST" in original || "가슴" in original -> "CHEST"
        "WAIST" in upper || "허리" in original -> "WAIST"
        "HIP" in upper || "엉덩이" in original -> "HIP"
        "SLEEVE" in upper || "소매길이" in original -> "SLEEVE"
        "RISE" in upper || "밑위" in original -> "RISE"
        "THIGH" in upper || "허벅지" in original -> "THIGH"
        "HEM" in upper || "밑단" in original -> "HEM"
        "LENGTH" in upper || "총" in original -> "LENGTH"
        else -> upper
    }
}

fun normalizeShoeKey(original: String): String {
    val upper = original.uppercase()

    return when {
        "WIDTH" in upper || "너비" in original || "발볼" in original -> "FOOT WIDTH"
        "LENGTH" in upper || "길이" in original -> "FOOT LENGTH"
        else -> upper
    }
}

fun formatSizeByCategory(
    category: SizeCategory,
    sizeId: String,
    allSizes: Map<SizeCategory, List<Size>>
): String? {
    val sizeList = allSizes[category].orEmpty()
    return when (category) {
        SizeCategory.TOP -> sizeList.find { it.id == sizeId }?.let { formatTopSize(it as TopSize) }
        SizeCategory.BOTTOM -> sizeList.find { it.id == sizeId }?.let { formatBottomSize(it as BottomSize) }
        SizeCategory.OUTER -> sizeList.find { it.id == sizeId }?.let { formatOuterSize(it as OuterSize) }
        SizeCategory.ONE_PIECE -> sizeList.find { it.id == sizeId }?.let { formatOnePieceSize(it as OnePieceSize) }
        SizeCategory.SHOE -> sizeList.find { it.id == sizeId }?.let { formatShoeSize(it as ShoeSize) }
        SizeCategory.ACCESSORY -> sizeList.find { it.id == sizeId }?.let { formatAccessorySize(it as AccessorySize) }
        else -> null
    }
}

fun formatTopSize(size: TopSize, memoVisibility: MemoVisibility? = null): String = buildString {
    appendLine("👕")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.shoulder?.let { appendLine("어깨너비: ${it}cm") }
    size.chest?.let { appendLine("가슴단면: ${it}cm") }
    size.sleeve?.let { appendLine("소매길이: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: $it") }
    if (memoVisibility != null && memoVisibility == MemoVisibility.PUBLIC) size.note?.let { append("메모: $it") }
}

fun formatBottomSize(size: BottomSize, memoVisibility: MemoVisibility? = null): String = buildString {
    appendLine("👖")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.waist?.let { appendLine("허리단면: ${it}cm") }
    size.rise?.let { appendLine("밑위: ${it}cm") }
    size.hip?.let { appendLine("엉덩이단면: ${it}cm") }
    size.thigh?.let { appendLine("허벅지단면: ${it}cm") }
    size.hem?.let { appendLine("밑단단면: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: $it") }
    if (memoVisibility != null && memoVisibility == MemoVisibility.PUBLIC) size.note?.let { append("메모: $it") }
}

fun formatOuterSize(size: OuterSize, memoVisibility: MemoVisibility? = null): String = buildString {
    appendLine("🧥")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.shoulder?.let { appendLine("어깨너비: ${it}cm") }
    size.chest?.let { appendLine("가슴단면: ${it}cm") }
    size.sleeve?.let { appendLine("소매길이: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: $it") }
    if (memoVisibility != null && memoVisibility == MemoVisibility.PUBLIC) size.note?.let { append("메모: $it") }
}

fun formatOnePieceSize(size: OnePieceSize, memoVisibility: MemoVisibility? = null): String = buildString {
    appendLine("👗")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.shoulder?.let { appendLine("어깨너비: ${it}cm") }
    size.chest?.let { appendLine("가슴단면: ${it}cm") }
    size.waist?.let { appendLine("허리단면: ${it}cm") }
    size.hip?.let { appendLine("엉덩이단면: ${it}cm") }
    size.sleeve?.let { appendLine("소매길이: ${it}cm") }
    size.rise?.let { appendLine("밑위: ${it}cm") }
    size.thigh?.let { appendLine("허벅지단면: ${it}cm") }
    size.hem?.let { appendLine("밑단단면: ${it}cm") }
    size.length?.let { appendLine("총장: ${it}cm") }
    size.fit?.let { appendLine("핏: ${it}") }
    if (memoVisibility != null && memoVisibility == MemoVisibility.PUBLIC) size.note?.let { append("메모: $it") }
}

fun formatShoeSize(size: ShoeSize, memoVisibility: MemoVisibility? = null): String = buildString {
    appendLine("👟")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.footLength?.let { appendLine("발길이: ${it}cm") }
    size.footWidth?.let { appendLine("발볼너비: ${it}cm") }
    size.fit?.let { appendLine("핏: ${it}") }
    if (memoVisibility != null && memoVisibility == MemoVisibility.PUBLIC) size.note?.let { append("메모: $it") }
}

fun formatAccessorySize(size: AccessorySize, memoVisibility: MemoVisibility? = null): String = buildString {
    appendLine("💍")
    appendLine("${size.type} ${size.sizeLabel} - ${size.brand}")
    size.bodyPart?.let { appendLine("착용 부위: ${it}") }
    size.fit?.let { appendLine("핏: ${it}") }
    if (memoVisibility != null && memoVisibility == MemoVisibility.PUBLIC) size.note?.let { append("메모: $it") }
}
