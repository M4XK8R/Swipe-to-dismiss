package com.maxkor.swipe_to_dismiss.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDao {

    @Query("SELECT * FROM my_table")
    fun getAllItems(): Flow<List<MyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem (myEntity: MyEntity)

    @Delete
    fun deleteItem (myEntity: MyEntity)
}