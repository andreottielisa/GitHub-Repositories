package com.andreotti.github.commons

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.andreotti.github.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy.AUTOMATIC
import com.bumptech.glide.request.RequestOptions

internal fun ImageView.loadUrl(
    url: String?,
    @DrawableRes resourceError: Int = R.drawable.ic_baseline_broken_image
) {
    val options = RequestOptions()
        .circleCrop()
        .error(resourceError)

    Glide
        .with(context)
        .load(url)
        .diskCacheStrategy(AUTOMATIC)
        .apply(options)
        .into(this)
}