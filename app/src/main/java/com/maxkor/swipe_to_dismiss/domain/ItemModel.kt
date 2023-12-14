package com.maxkor.swipe_to_dismiss.domain

import com.maxkor.swipe_to_dismiss.R
import kotlin.random.Random

data class ItemModel(
    val id: Int = 0,
    val imageResId: Int = R.drawable.av,
    val text: String = "Lorem ipsum...",
    var isChecked: Boolean = Random.nextBoolean()
)