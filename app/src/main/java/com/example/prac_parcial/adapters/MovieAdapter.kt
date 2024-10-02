package com.example.prac_parcial.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.prac_parcial.R
import com.example.prac_parcial.models.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(
    private val movies: List<Movie>,
    private val clickListener: OnItemClickListener
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPopularity: TextView = itemView.findViewById(R.id.tvPopularity)
        private val tvSinopsis: TextView = itemView.findViewById(R.id.tvSinopsis)
        private val btLike: ImageButton = itemView.findViewById(R.id.btLike)
        private  val ivImage: ImageView = itemView.findViewById(R.id.ivMovie)

        fun bind(movie: Movie) {
            tvName.text = movie.title
            tvPopularity.text = movie.popularity.toString()
            tvSinopsis.text = movie.overview

            // Verifica que el posterPath no sea null o vacío y usa Picasso para cargar la imagen
            if (!movie.posterPath.isNullOrEmpty()) {
                val fullUrl = "https://image.tmdb.org/t/p/w500" + movie.posterPath // Ajusta esta URL según sea necesario
                Picasso.get().load(fullUrl).into(ivImage)
            }

            btLike.setOnClickListener {
                clickListener.onItemClick(movie)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prototype_movie, parent, false)
        return MovieViewHolder(view)

    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }
    interface OnItemClickListener{
        fun onItemClick(movie: Movie)
    }

}