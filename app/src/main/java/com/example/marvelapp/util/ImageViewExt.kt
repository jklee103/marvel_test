package com.example.marvelapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.marvelapp.R

@BindingAdapter("imgUrl")
fun ImageView.bindImageUrl(url: String?) {
    url?.let {
        Glide.with(this)
            .load(it)
            .error(R.drawable.ic_launcher_foreground)
            .into(this)

    }
}