package com.example.retailapp.core.utils.custom

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * Use with layout attributes "app:bottomItemOffset", "app:middleItemOffset", "app:topItemOffset"
 */
class VertRecyclerOffsetsDecorator(
    context: Context?,
    bottomMarginInPx: Float,
    middleMarginInPx: Float,
    topMarginInPx: Float
) : ItemDecoration() {

    private val bottomMargin: Int = bottomMarginInPx.toInt()
    private val middleMargin: Int = middleMarginInPx.toInt()
    private val topMargin: Int = topMarginInPx.toInt()

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = topMargin
            outRect.bottom = middleMargin
        } else if (parent.getChildAdapterPosition(view) == parent.adapter!!.itemCount - 1) {
            outRect.top = middleMargin
            outRect.bottom = bottomMargin
        } else {
            outRect.top = middleMargin
            outRect.bottom = middleMargin
        }

    }

}