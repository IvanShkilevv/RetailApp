package com.example.retailapp.core.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(protected val view: View) : RecyclerView.ViewHolder(view) {

    protected open var data: T? = null

    open fun bind(data: T) {
        this.data = data
    }

    val context: Context
        get() = itemView.context

    fun getString(@StringRes id: Int): String {
        return context.getString(id)
    }

    fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return context.getString(id, *formatArgs)
    }

    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context, id)
    }

    protected fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context, id)
    }

    protected fun getDimen(@DimenRes dimenRes: Int): Int {
        return context.resources.getDimensionPixelOffset(dimenRes)
    }

    companion object {
        fun createView(parent: ViewGroup, @LayoutRes resource: Int): View {
            return LayoutInflater.from(parent.context).inflate(resource, parent, false)
        }
    }
}