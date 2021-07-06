package com.xtremepixel.moviemvvm.common.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.xtremepixel.moviemvvm.Config
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.common.presentation.model.MovieUI
import com.xtremepixel.moviemvvm.databinding.ItemMovieBinding
import com.xtremepixel.moviemvvm.dp
import com.xtremepixel.moviemvvm.load
import com.xtremepixel.moviemvvm.common.remote.model.Movie
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class MovieAdapter(private val listener:MovieClickedListener):ListAdapter<MovieUI,MovieAdapter.ViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item:MovieUI = getItem(position)
        holder.bind(item)
    }

    interface MovieClickedListener {

        fun onMovieClicked(movieID: Int?)
    }

    fun getRating(context: Context,movie: MovieUI): String {
        return if (movie.voteCount == 0 && context != null) {
            context.getString(R.string.no_ratings)
        } else {
            val starIcon = 9733.toChar()
            "${movie.voteAverage} $starIcon"
        }
    }

 inner   class ViewHolder(private val binding: ItemMovieBinding):RecyclerView.ViewHolder(binding.root) {

     fun bind(movieUI: MovieUI){
            binding.movieTitle.text = movieUI.title
         binding.tvMovieDescription.text = binding.root.context?.getString(R.string.movie_row_desc_pattern,
             if (movieUI.releaseDate!!.isNotEmpty())
                 LocalDate.parse(movieUI.releaseDate).format(DateTimeFormatter.ofLocalizedDate(
                     FormatStyle.MEDIUM))
             else binding.root.context.getString(R.string.no_release_date),
             getRating(binding.root.context,movieUI)
         )
         binding.moviePoster.setImageResource(R.drawable.ic_launcher_background)
         binding.textContainer.setBackgroundColor(Color.DKGRAY)
         binding.moviePoster.load(url = Config.IMAGE_URL + movieUI.posterPath,
             crossFade = true, width = 160.dp, height = 160.dp) { color ->

             binding.textContainer.setBackgroundColor(color)
         }
         binding.root.setOnClickListener {
             listener.onMovieClicked(movieUI.id)
         }
     }

    }
}
private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<MovieUI>() {
    override fun areItemsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieUI, newItem: MovieUI): Boolean {
        return oldItem == newItem
    }
}

