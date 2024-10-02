package com.example.prac_parcial.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.prac_parcial.models.Movie

@Database(entities=[Movie::class], version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): MovieDao
    companion object{
        private var INSTANCE: AppDatabase?= null

        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE==null){
                INSTANCE = Room
                    .databaseBuilder(context, AppDatabase::class.java, "movies.db")
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE!!
        }


    }

}