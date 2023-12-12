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
    fun getAllItems(): Flow<List<MyDbEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(myDbEntity: MyDbEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MyDbEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVarArg(vararg items: MyDbEntity)

    @Delete
    suspend fun deleteItem(myDbEntity: MyDbEntity)

    @Query("DELETE FROM my_table")
    suspend fun deleteAll()
}