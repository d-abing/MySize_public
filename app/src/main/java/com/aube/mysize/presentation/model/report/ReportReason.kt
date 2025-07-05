package com.aube.mysize.presentation.model.report

enum class ReportReason(val displayName: String) {
    // CLOTHES
    INAPPROPRIATE_CONTENT("부적절한 콘텐츠"),
    COPYRIGHT_VIOLATION("저작권 침해"),
    SPAM("스팸 또는 광고"),
    OBSCENE_IMAGE("외설적인 이미지"),
    ETC_CLOTHES("기타"),

    // USER
    INSULT("욕설 또는 비방"),
    IMPERSONATION("사칭"),
    INAPPROPRIATE_PROFILE("부적절한 프로필 이미지"),
    HARASSMENT("반복적인 괴롭힘"),
    ETC_USER("기타");

    companion object {
        fun forType(type: ReportType): List<ReportReason> = when (type) {
            ReportType.CLOTHES -> listOf(
                INAPPROPRIATE_CONTENT,
                COPYRIGHT_VIOLATION,
                SPAM,
                OBSCENE_IMAGE,
                ETC_CLOTHES
            )
            ReportType.USER -> listOf(
                INSULT,
                IMPERSONATION,
                INAPPROPRIATE_PROFILE,
                HARASSMENT,
                ETC_USER
            )
        }
    }
}