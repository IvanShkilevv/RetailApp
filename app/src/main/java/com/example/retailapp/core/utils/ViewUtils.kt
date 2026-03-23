package com.example.retailapp.core.utils

import android.view.View
import android.view.View.*

fun View.makeGone() {
    this.visibility = GONE
}

fun View.makeVisible() {
    this.visibility = VISIBLE
}

fun View.visibleIf(condition: Boolean) {
    this.visibility = if (condition) VISIBLE else GONE
}

fun View.goneIf(condition: Boolean) {
    this.visibility = if (condition) GONE else VISIBLE
}