package com.example.prac_parcial.apiService

import com.example.prac_parcial.communication.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMovieService {
    @GET("movie/popular")
    fun getMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ApiResponse>
}