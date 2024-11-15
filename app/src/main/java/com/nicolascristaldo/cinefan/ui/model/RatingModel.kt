package com.nicolascristaldo.cinefan.ui.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

data class RatingModel(
    @DrawableRes val image: Int,
    @ColorRes val color: Int
)
