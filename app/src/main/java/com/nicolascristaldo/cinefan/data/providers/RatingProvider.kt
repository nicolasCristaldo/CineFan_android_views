package com.nicolascristaldo.cinefan.data.providers

import com.nicolascristaldo.cinefan.R
import com.nicolascristaldo.cinefan.ui.model.RatingModel
import javax.inject.Inject

class RatingProvider @Inject constructor() {
    fun getRating(rating: Float): RatingModel {
        return when(rating) {
            in 0f..3.0f -> RatingModel(
                image = R.drawable.ic_rating_poor,
                color = R.color.rating_poor
            )
            in 3.0..5.4 -> RatingModel(
                image = R.drawable.ic_rating_fair,
                color = R.color.rating_fair
            )
            in 5.4..7.4 -> RatingModel(
                image = R.drawable.ic_rating_good,
                color = R.color.rating_good
            )
            in 7.4..9.0 -> RatingModel(
                image = R.drawable.ic_rating_great,
                color = R.color.rating_great
            )
            else -> RatingModel(
                image = R.drawable.ic_rating_excellent,
                color = R.color.rating_excellent
            )
        }
    }
}