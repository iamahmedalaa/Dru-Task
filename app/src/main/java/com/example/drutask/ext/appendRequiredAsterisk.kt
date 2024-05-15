package com.example.drutask.ext

fun String.appendRequiredAsterisk(isRequired: Boolean) = if (isRequired) "$this*" else this
