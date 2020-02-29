package com.example.poalimtest.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.poalimtest.R
import com.example.poalimtest.ui.movieList.model.ImageUiModel


fun ImageUiModel.loadImage(imageView: ImageView) {
    Glide.with(imageView).load(url).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .placeholder(R.drawable.ic_no_pic).into(imageView)
}