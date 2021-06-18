package com.xtremepixel.moviemvvm.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.xtremepixel.moviemvvm.Config
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.databinding.ItemMovieBinding
import com.xtremepixel.moviemvvm.dp
import com.xtremepixel.moviemvvm.load
import com.xtremepixel.moviemvvm.models.Movie
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class MovieAdapter(private val context: Context?,private var movies:ArrayList<Movie>,private val movieClickedListiner:MovieClickedListener):RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.tvMovieTitle.text = movie.title
        holder.tvMovieDescription.text = context?.getString(R.string.movie_row_desc_pattern,
            if (movie.releaseDate.isNotEmpty())
                LocalDate.parse(movie.releaseDate).format(DateTimeFormatter.ofLocalizedDate(
                    FormatStyle.MEDIUM))
            else context.getString(R.string.no_release_date),
            getRating(movie)
        )

        holder.ivMoviePoster.setImageResource(R.drawable.ic_launcher_background)
       // holder.ivMoviePoster.transitionName = movie.id.toString()
        holder.llMovieTextContainer.setBackgroundColor(Color.DKGRAY)

        holder.ivMoviePoster.load(url = Config.IMAGE_URL + movie.posterPath,
            crossFade = true, width = 160.dp, height = 160.dp) { color ->

            holder.llMovieTextContainer.setBackgroundColor(color)
        }

        holder.itemView.setOnClickListener {
            movieClickedListiner.onMovieClicked(position)
        }
    }

    override fun getItemCount() = movies.size

    fun fillList(items: List<Movie>) {
        this.movies += items
        notifyDataSetChanged()
    }

    fun clear() {
        this.movies = arrayListOf()
    }

    interface MovieClickedListener {

        fun onMovieClicked(position: Int)
    }

    fun getRating(movie: Movie): String {
        return if (movie.voteCount == 0 && context != null) {
            context.getString(R.string.no_ratings)
        } else {
            val starIcon = 9733.toChar()
            "${movie.voteAverage} $starIcon"
        }
    }
}
    class ViewHolder(binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {

        val cvMovieContainer: CardView = binding.cvMovieContainer
        val llMovieTextContainer: LinearLayout = binding.textContainer
        val tvMovieTitle: TextView = binding.movieTitle
        val tvMovieDescription: TextView = binding.tvMovieDescription
        val ivMoviePoster: ImageView = binding.moviePoster

    }

