package com.jogayed.tempo.journal.app_core.ext

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImageUrl(url: String, placeHolderResId: Int? = null) {
    val requestOptions = Glide
        .with(this.context)
        .load(url)
        .centerCrop()
    placeHolderResId?.let { requestOptions.placeholder(it) }
    requestOptions.into(this)
}