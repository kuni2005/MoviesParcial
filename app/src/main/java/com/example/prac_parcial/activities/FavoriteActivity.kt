package com.example.prac_parcial.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prac_parcial.R
import com.example.prac_parcial.adapters.MovieAdapter
import com.example.prac_parcial.db.AppDatabase
import com.example.prac_parcial.models.Movie

class FavoriteActivity: AppCompatActivity(), MovieAdapter.OnItemClickListener {
    private lateinit var rvFavorite: RecyclerView
    @SuppressLint("MissingInflateId")
    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        rvFavorite = findViewById(R.id.rvFavorite)

    }

    override fun onResume(){
        super.onResume()
        loadMovies{ movies ->
            rvFavorite.adapter = MovieAdapter(movies, this)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }

    }

    private fun loadMovies(onComplete: (List<Movie>)-> Unit) {
        val dao = AppDatabase.getInstance(this).getDao()
        val movies = dao.getAll()
        if (movies.isNotEmpty()) {
            onComplete(movies)
        } else {
            Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClick(movie: Movie) {
        val dao = AppDatabase.getInstance(this).getDao()
        dao.delete(movie)
        Toast.makeText(this, "Movie " + movie.title + " deleted", Toast.LENGTH_SHORT).show()
        loadMovies{ movies ->
            rvFavorite.adapter = MovieAdapter(movies, this)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
        }
    }

}