package com.aube.mysize.presentation.model

data class UserPreference(
    val styles: List<Style>,
    val ageGroup: AgeGroup,
    val priceRange: PriceRange
)

enum class Style(val displayName: String) {
    ALL("전체"),
    MINIMAL("미니멀"),
    CASUAL("캐주얼"),
    STREET("스트릿"),
    ROMANTIC("로맨틱"),
    VINTAGE("빈티지"),
    CLASSIC("클래식"),
    SPORTS("스포츠"),
}


enum class AgeGroup(val displayName: String) {
    TEENS("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대 이상")
}

enum class PriceRange(val displayName: String) {
    LOW("저가"),
    MEDIUM("중가"),
    HIGH("고가"),
    PREMIUM("프리미엄")
}

enum class Gender {
    MALE, FEMALE, UNISEX
}

enum class BodyType(
    val displayName: String,
    val heightRange: IntRange,
    val weightRange: IntRange
) {
    ALL("모든 체형", 140..210, 40..130),

    SLIM_SHORT("마른 체형 · 키 작음", 140..160, 40..50),
    SLIM_AVERAGE("마른 체형 · 평균 키", 161..175, 45..55),
    SLIM_TALL("마른 체형 · 키 큼", 176..190, 50..60),

    AVERAGE_SHORT("보통 체형 · 키 작음", 140..160, 50..65),
    AVERAGE_AVERAGE("보통 체형 · 평균 키", 161..175, 60..75),
    AVERAGE_TALL("보통 체형 · 키 큼", 176..190, 65..80),

    PLUS_SHORT("통통한 체형 · 키 작음", 140..160, 65..85),
    PLUS_AVERAGE("통통한 체형 · 평균 키", 161..175, 75..95),
    PLUS_TALL("통통한 체형 · 키 큼", 176..190, 85..110)
}

