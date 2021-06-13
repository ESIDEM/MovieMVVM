package com.xtremepixel.moviemvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.xtremepixel.moviemvvm.Config
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.databinding.ItemMovieBinding
import com.xtremepixel.moviemvvm.models.Movie

class MovieAdapter(private var movies:ArrayList<Movie>):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    fun updateCountries(newMovies: List<Movie>) {
        movies.clear()
        movies.addAll(newMovies)
        notifyDataSetChanged()
    }

//    companion object {
//         private const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
//     }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount() = movies.size

    class ViewHolder(binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {
        val movieTitle = binding.textTitle
        val moviePoster = binding.imagePoster
        val movieRating = binding.ratingBar
        val movieOverView = binding.overview
        fun bind(movie: Movie) {
            val moviePosterURL = Config.IMAGE_URL + movie.posterPath
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
