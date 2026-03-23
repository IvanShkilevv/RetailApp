package com.example.retailapp.core.utils

private const val CURRENCY_SIGN = "€"

fun Float.addCurrencySign(): String = "$this $CURRENCY_SIGN"