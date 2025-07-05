package com.aube.mysize.domain.model.clothes

enum class BodyField(val displayName: String) {
    GENDER("성별"),
    HEIGHT("키"),
    WEIGHT("몸무게"),
    NECK("목둘레"),
    CHEST("가슴둘레"),
    SHOULDER("어깨너비"),
    ARM("팔 길이"),
    WAIST("허리둘레"),
    FOOT_LENGTH("발 길이"),
    HIP("엉덩이둘레"),
    FOOT_WIDTH("발 너비"),
    LEG("다리 안쪽 길이");

    companion object {
        fun fromDisplayName(name: String): BodyField? =
            entries.find { it.displayName == name }
    }
}