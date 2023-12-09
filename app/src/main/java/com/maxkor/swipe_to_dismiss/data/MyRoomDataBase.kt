package com.maxkor.swipe_to_dismiss.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MyEntity::class],
    version = 1
)
abstract class MyRoomDataBase : RoomDatabase() {
    abstract val dao: MyDao
}
