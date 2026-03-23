package com.example.retailapp.core.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.View.*
import androidx.recyclerview.widget.RecyclerView
import com.example.retailapp.core.utils.custom.VertRecyclerOffsetsDecorator

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

/**
 * Use for vertical recyclerView decoration
 */
fun RecyclerView.setVerticalOffsets(
    bottomMarginInPx: Int,
    middleMarginInPx: Int,
    topMarginInPx: Int
) {
    this.addItemDecoration(
        VertRecyclerOffsetsDecorator(
            this.context,
            bottomMarginInPx.toFloat(),
            middleMarginInPx.toFloat(),
            topMarginInPx.toFloat()
        )
    )
}

fun drawStatusBarTopPadding(root: View) {
    root.apply {
        setPadding(
            paddingLeft,
            getStatusBarHeight(root.context),
            paddingLeft,
            paddingBottom
        )
    }
}

@SuppressLint("InternalInsetResource")
fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId =
        context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}