package com.example.prac_parcial.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.prac_parcial.models.Movie

@Dao
interface MovieDao {
    @Insert
    fun insertOne(movie: Movie)
    @Query("SELECT * FROM Movie")
    fun getAll(): List<Movie>
    @Delete
    fun delete(movie: Movie)


}