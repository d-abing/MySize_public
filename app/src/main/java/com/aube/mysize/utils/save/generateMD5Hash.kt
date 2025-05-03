package com.aube.mysize.utils.save

import java.security.MessageDigest

fun generateMD5Hash(input: ByteArray): String {
    val md = MessageDigest.getInstance("MD5")
    return md.digest(input).joinToString("") { "%02x".format(it) }
}