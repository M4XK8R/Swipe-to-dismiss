package com.maxkor.swipe_to_dismiss.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MyDbEntity::class],
    version = 1
)
abstract class MyRoomDataBase : RoomDatabase() {
    abstract val dao: MyDao

    companion object Uwu {

        private const val DATABASE_NAME = "my_database"

        @Volatile
        private var INSTANCE: MyRoomDataBase? = null

        fun getInstance(context: Context): MyRoomDataBase {
            synchronized(this) {
                INSTANCE?.let { return it }

                val db = Room.databaseBuilder(
                    context,
                    MyRoomDataBase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = db
                return db
            }
        }
    }
}

