package com.moviedemo.extensions

import android.widget.ImageView
import com.moviedemo.R

fun ImageView.setPosterUrl(imageName: String) {
    try {
        val resource = context.resources.getIdentifier(
            imageName.replace(".jpg", ""),
            "drawable",
            context.packageName
        )
        this.setImageResource(
            if (resource == 0) R.drawable.placeholder_for_missing_posters else resource
        )
    } catch (E: Exception) {
        this.setImageResource(
            R.drawable.placeholder_for_missing_posters
        )
    }
}



