package com.example.retailapp.core.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.retailapp.R

fun AppCompatImageView.drawPhoto(
    photoUrl: String?,
    cornerRadiusPx: Int = 0,
    context: Context?,
    @DrawableRes placeholderRes: Int = R.drawable.ic_photo_placeholder
) {
    var requestOptions = RequestOptions()
    requestOptions = if (cornerRadiusPx != 0) {
        requestOptions.transform(RoundedCorners(cornerRadiusPx))
    } else {
        requestOptions.transform()
    }

    if (context == null) return
    val thumbnail: RequestBuilder<Drawable> =
        Glide.with(context)
            .load(placeholderRes)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(requestOptions)

    Glide.with(this)
        .load(photoUrl)
        .apply(requestOptions)
        .thumbnail(thumbnail)
        .into(this)
}