package com.example.prac_parcial.communication

import com.example.prac_parcial.models.Movie
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results")
    val movies: List<MovieResponse>
)

data class MovieResponse(
    val id: Int,
    val title: String,
    val popularity: Double,
    @SerializedName("overview")  // Este campo en la respuesta JSON es "overview"
    val sinopsis: String,
    @SerializedName("poster_path")  // Este campo en la respuesta JSON es "poster_path"
    val image: String
) {
    fun toMovie(): Movie {
        // Aquí convertimos MovieResponse a Movie, que es el modelo usado en la app
        return Movie(id, title, popularity, sinopsis, image)
    }
}