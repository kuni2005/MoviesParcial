package com.example.prac_parcial.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prac_parcial.R
import com.example.prac_parcial.adapters.MovieAdapter
import com.example.prac_parcial.apiService.ApiMovieService
import com.example.prac_parcial.communication.ApiResponse
import com.example.prac_parcial.communication.MovieResponse
import com.example.prac_parcial.db.AppDatabase
import com.example.prac_parcial.models.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieActivity: AppCompatActivity(), MovieAdapter.OnItemClickListener {


    private lateinit var rvMovie: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        rvMovie = findViewById(R.id.rvMovie)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        rvMovie.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        loadMovies { movies ->
            rvMovie.adapter = MovieAdapter(movies, this)
        }
    }

    private fun loadMovies(onComplete: (List<Movie>) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiMovieService = retrofit.create(ApiMovieService::class.java)
        val request = apiService.getMovie(
            apiKey = "3cae426b920b29ed2fb1c0749f258325",
            language = "en-US",
            page = 1
        )

        request.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val movieApiResponse: ApiResponse? = response.body()

                    // Log para imprimir el cuerpo de la respuesta cruda en JSON
                    Log.d("MovieActivity", "Raw JSON response: ${response.body().toString()}")

                    // Verificación nula para evitar NullPointerException
                    if (movieApiResponse?.movies != null) {
                        val movieList = movieApiResponse.movies.mapNotNull { it } // Verifica que no haya nulos en la lista

                        // Agrega un Log para imprimir la lista de películas
                        Log.d("MovieActivity", "Movies loaded: $movieList")

                        onComplete(movieList.map(MovieResponse::toMovie))
                    } else {
                        Log.e("MovieActivity", "Movies list is null")
                        onComplete(emptyList())
                    }
                } else {
                    Log.e("MovieActivity", "Error: ${response.code()} - ${response.message()}")
                    onComplete(emptyList())
                }
            }



            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("MovieActivity", "Error loading movies: ${t.message}", t)
                onComplete(emptyList())  // Manejar el error devolviendo una lista vacía
            }
        })
    }

    override fun onItemClick(movie: Movie) {
        val dao = AppDatabase.getInstance(this).getDao()
        dao.insertOne(movie)
        Toast.makeText(this, "Movie ${movie.title} added to favorites", Toast.LENGTH_SHORT).show()
    }
}