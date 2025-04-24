package com.aube.mysize.utils

import android.content.Context
import java.util.Locale

fun setAppLocale(context: Context, language: String) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources = context.resources
    val config = resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
}