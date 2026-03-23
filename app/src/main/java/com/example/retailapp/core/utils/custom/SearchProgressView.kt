package com.example.retailapp.core.utils.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import com.example.retailapp.R

class SearchProgressView: FrameLayout {

    private lateinit var message: TextView
    private lateinit var secondMessage: TextView
    private lateinit var progress: ProgressBar
    private lateinit var emptyStateBlock: Group

    constructor(context: Context) : super(context) {
        inflate(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        inflate(context, attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        inflate(context, attrs)
    }

    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        inflate(context, attrs)
    }

    private fun inflate(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.view_search_progress, this)
        visibility = GONE
        message = findViewById(R.id.main_message)
        secondMessage = findViewById(R.id.second_message)
        progress = findViewById(R.id.progress)
        emptyStateBlock = findViewById(R.id.empty_block)
    }

    fun showMessage(
        message: String? = null,
        secondMessage: String? = null,
        @DrawableRes image: Int? = null
    ) {
        visibility = VISIBLE
        this.message.apply {
            this.isVisible = message != null
            text = message
        }
        this.secondMessage.apply {
            this.isVisible = secondMessage != null
            text = secondMessage
        }

        progress.visibility = GONE
    }

    fun showProgress(show: Boolean) {
        if (show) {
            progress.visibility = VISIBLE
            visibility = VISIBLE
            emptyStateBlock.isVisible = false
        } else {
            visibility = GONE
        }
    }
}