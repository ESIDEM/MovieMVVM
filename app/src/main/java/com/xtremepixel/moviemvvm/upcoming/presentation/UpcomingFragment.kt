package com.xtremepixel.moviemvvm.upcoming.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.xtremepixel.moviemvvm.R
import com.xtremepixel.moviemvvm.common.presentation.Event
import com.xtremepixel.moviemvvm.common.presentation.adapter.InfiniteScrollListener
import com.xtremepixel.moviemvvm.common.presentation.adapter.MovieAdapter
import com.xtremepixel.moviemvvm.databinding.FragmentUpcomingBinding
import com.xtremepixel.moviemvvm.popular.presentation.MovieListFragment
import com.xtremepixel.moviemvvm.popular.presentation.PopularMovieState
import com.xtremepixel.moviemvvm.popular.presentation.PopularMoviesEvent
import com.xtremepixel.moviemvvm.popular.presentation.viewmodel.PopularMovieViewModel
import com.xtremepixel.moviemvvm.upcoming.presentation.viewmodel.UpcomingMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

    companion object {
        private const val ITEMS_PER_ROW = 2
    }

    private val viewModel by viewModels<UpcomingMovieViewModel>()
    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpcomingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialMoviesList()
    }

    private fun setupUI() {
        val adapter = createAdapter()
        setupRecyclerView(adapter)
        observeViewStateUpdates(adapter)
    }

    private fun createAdapter(): MovieAdapter {
        return MovieAdapter(object : MovieAdapter.MovieClickedListener{
            override fun onMovieClicked(movieID: Int) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun setupRecyclerView(movieAdapter: MovieAdapter) {

        binding.upcomingMoviesList.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), ITEMS_PER_ROW)
            setHasFixedSize(true)
            addOnScrollListener(createInfiniteScrollListener(layoutManager as GridLayoutManager))
        }

    }
    private fun createInfiniteScrollListener(
        layoutManager: GridLayoutManager
    ): RecyclerView.OnScrollListener {
        return object : InfiniteScrollListener(
            layoutManager,
            PopularMovieViewModel.UI_PAGE_SIZE
        ) {
            override fun loadMoreItems() { requestMoreMoviess() }
            override fun isLoading(): Boolean = viewModel.isLoadingMoreMovie
            override fun isLastPage(): Boolean = viewModel.isLastPage
        }
    }

    private fun observeViewStateUpdates(adapter: MovieAdapter) {
        viewModel.state.observe(viewLifecycleOwner) {
            updateScreenState(it, adapter)
        }
    }

    private fun updateScreenState(state: UpcomingMovieState, adapter: MovieAdapter) {
        binding.loadingView.isVisible = state.loading
        adapter.submitList(state.movies)
        handleNoMoreMovies(state.noMoreMovies)
        handleFailures(state.failure)
    }

    private fun handleNoMoreMovies(noMoreAnimalsNearby: Boolean) {
        // Show a warning message and a prompt for the user to try a different
        // distance or postcode
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

    private fun requestInitialMoviesList() {
        viewModel.onEvent(UpcomingMoviesEvent.RequestInitialMoviesList)
    }


    private fun requestMoreMoviess() {
        viewModel.onEvent(UpcomingMoviesEvent.RequestMoreMoviess)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }

}