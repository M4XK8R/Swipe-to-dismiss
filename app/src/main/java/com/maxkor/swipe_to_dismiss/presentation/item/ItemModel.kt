package com.maxkor.swipe_to_dismiss.presentation.item

import android.os.Parcelable
import com.maxkor.swipe_to_dismiss.R
import kotlinx.parcelize.Parcelize
import kotlin.random.Random

@Parcelize
data class ItemModel(
    val id: Int = 0,
    val imageResId: Int = R.drawable.av2,
    val text: String = "Lorem ipsum... $id",
    var isChecked: Boolean = Random.nextBoolean()
) : Parcelable