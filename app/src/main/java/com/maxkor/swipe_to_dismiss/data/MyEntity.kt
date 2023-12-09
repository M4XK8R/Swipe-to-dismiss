package com.maxkor.swipe_to_dismiss.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.maxkor.swipe_to_dismiss.R
import kotlin.random.Random

private const val TABLE_NAME = "my_table"

@Entity(tableName = TABLE_NAME)
data class MyEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "image")
    val imageResId: Int = R.drawable.av2,

    @ColumnInfo(name = "title")
    val text: String = "Lorem ipsum... $id",

    @ColumnInfo(name = "checked_state")
    val isChecked: Boolean = Random.nextBoolean()
)
