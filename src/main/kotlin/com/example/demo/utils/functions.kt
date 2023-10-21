package com.example.demo.utils

import java.util.*

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    val re = Regex(emailRegex)

    return re.matches(email)
}

fun generateRandomID(): String {
    val rand = Random()
    val idLength = 8

    val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    val id = (0 until idLength)
            .map { chars[rand.nextInt(chars.length)] }
            .joinToString("")

    return id
}