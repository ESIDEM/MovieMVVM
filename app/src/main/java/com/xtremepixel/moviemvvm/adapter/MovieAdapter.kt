package com.xtremepixel.moviemvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.models.Movie

class MovieAdapter(private var movies:ArrayList<Movie>):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun updateCountries(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

    companion object {
         private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
     }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val movieTitle = itemView.findViewById<TextView>(R.id.textTitle)
        val moviePoster = itemView.findViewById<ImageView>(R.id.imagePoster)
        val movieRating = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val movieOverView = itemView.findViewById<TextView>(R.id.overview)
        fun bind(movie: Movie) {
            val moviePosterURL = POSTER_BASE_URL + movie.posterPath
            movieTitle.text = movie.title
            movieOverView.text = movie.overview
            movieRating.rating = movie.voteAverage.toFloat()
            movieRating.numStars=5
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .error(R.drawable.ic_launcher_background)
                .centerInside()
                .into(moviePoster)
        }

    }

}
