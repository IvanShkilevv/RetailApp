package com.example.retailapp.core.utils

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
            bottomMarginInPx.toFloat(),
            middleMarginInPx.toFloat(),
            topMarginInPx.toFloat()
        )
    )
}
