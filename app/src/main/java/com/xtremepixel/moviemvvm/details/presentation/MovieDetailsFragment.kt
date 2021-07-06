package com.xtremepixel.moviemvvm.details.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.xtremepixel.moviemvvm.Config
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.common.presentation.Event
import com.xtremepixel.moviemvvm.common.presentation.adapter.MovieAdapter
import com.xtremepixel.moviemvvm.common.presentation.model.MovieUI
import com.xtremepixel.moviemvvm.common.presentation.model.displayReleaseDate
import com.xtremepixel.moviemvvm.common.presentation.model.displayVoteCount
import com.xtremepixel.moviemvvm.databinding.FragmentMovieDetailsBinding
import com.xtremepixel.moviemvvm.details.MovieDetailsState
import com.xtremepixel.moviemvvm.details.viewmodel.MovieDetailsViewModel
import com.xtremepixel.moviemvvm.load
import com.xtremepixel.moviemvvm.popular.presentation.PopularMovieState
import com.xtremepixel.moviemvvm.popular.presentation.viewmodel.PopularMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    val arg: MovieDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentMovieDetailsBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonBack.setOnClickListener {
            activity?.onBackPressed()
        }
        observeViewStateUpdates()
        viewModel.loadMovieDetails(arg.id)
    }

    private fun observeViewStateUpdates() {
//        viewModel.state.observe(viewLifecycleOwner) {
//            updateScreenState(it)
//        }

        viewModel.movie.observe(viewLifecycleOwner){
            updateUi(it)
        }
      viewModel.error.observe(viewLifecycleOwner){
          it.let {
              handleFailures(it)
          }
      }
        viewModel.loadingState.observe(viewLifecycleOwner){
            if (it) {binding.loadingView.visibility = View.VISIBLE
            hideViews() } else {
                binding.loadingView.visibility = View.GONE
                showViews()
            }
        }
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return

        val fallbackMessage = getString(R.string.an_error_occurred)

        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        }
        else {
            unhandledFailure.message!! }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(requireView(), snackbarMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    fun  updateUi(movie:MovieUI){
        binding.imagePoster.load(url = Config.IMAGE_URL + movie.posterPath)
        binding.ratingBar.rating = movie.voteAverage!!.div(2).toFloat()
        binding.textTitle.text = movie.title
        binding.textOverview.text = movie.overview
        binding.textVoteCount.text = movie.displayVoteCount()
        binding.textReleaseDate.text = movie.displayReleaseDate()
    }

    fun hideViews(){
        binding.imagePoster.visibility = View.GONE
        binding.ratingBar.visibility = View.GONE
        binding.textTitle.visibility = View.GONE
        binding.textOverview.visibility = View.GONE
        binding.textVoteCount.visibility = View.GONE
        binding.textReleaseDate.visibility = View.GONE
    }
fun showViews(){
    binding.imagePoster.visibility = View.VISIBLE
    binding.ratingBar.visibility = View.VISIBLE
    binding.textTitle.visibility = View.VISIBLE
    binding.textOverview.visibility = View.VISIBLE
    binding.textVoteCount.visibility = View.VISIBLE
    binding.textReleaseDate.visibility = View.VISIBLE
}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }
}