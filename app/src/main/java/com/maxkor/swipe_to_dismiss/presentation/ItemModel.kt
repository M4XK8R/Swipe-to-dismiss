package com.maxkor.swipe_to_dismiss.presentation

import android.graphics.drawable.Drawable
import android.icu.text.CaseMap.Title
import android.media.Image
import com.maxkor.swipe_to_dismiss.R
import kotlin.random.Random

data class ItemModel(
    var orderId: Int? = null,
    var id: Int = 0,
    val imageResId: Int = R.drawable.av2,
    val text: String = "Lorem ipsum",
    val isChecked: Boolean = Random.nextBoolean()
)