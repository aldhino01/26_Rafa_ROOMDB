package com.example.a26_rafa_roomdb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.locks.Lock

@Database(
    entities = [Movie::class],
    version = 1
)
abstract class Moviedb : RoomDatabase(){
    abstract fun movieDao() : MovieDao

    companion object{
        @Volatile private var instance : Moviedb? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock){
            instance ?: buildDatabase(context).also {
                instance = it 
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            Moviedb::class.java,
            "Movie12345.db"
        ).build()


    }


}