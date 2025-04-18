package com.aube.mysize.utils

fun colorToHexString(color: Int): String {
    return String.format("#%06X", color and 0xFFFFFF)
}
