package com.example.prac_parcial.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie (
    @PrimaryKey
    val id: Int,  // La API proporciona un ID único para cada película
    @ColumnInfo(name = "title")
    val title: String?,  // El título de la película
    @ColumnInfo(name = "popularity")
    val popularity: Double?,  // La popularidad de la película
    @ColumnInfo(name = "overview")
    val overview: String?,  // La sinopsis de la película
    @ColumnInfo(name = "poster_path")
    val posterPath: String?  // El camino al poster de la película
)