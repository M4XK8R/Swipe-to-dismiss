package com.maxkor.swipe_to_dismiss.domain

import com.maxkor.swipe_to_dismiss.R
import kotlin.random.Random

data class ItemModel(
    val id: Int = 0,
    val imageResId: Int = R.drawable.default_avatar,
    val text: String = "Lorem ipsum...",
    var isChecked: Boolean = Random.nextBoolean()
)