package com.xtremepixel.moviemvvm

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.xtremepixel.moviemvvm.adapter.MovieAdapter
import com.xtremepixel.moviemvvm.data.Resource
import com.xtremepixel.moviemvvm.databinding.FragmentMovieListBinding
import com.xtremepixel.moviemvvm.viewmodel.MovieViewModel
import com.xtremepixel.moviemvvm.views.PaginationScrollListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel by viewModels<MovieViewModel>()
    private var movieListAdapter :MovieAdapter? = null
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecyclerView()
        setupSwipeRefresh()
        observeViewModel()
        viewModel.getMovies()
    }

    private fun setupRecyclerView() {
        movieListAdapter = MovieAdapter(context, arrayListOf(), object : MovieAdapter.MovieClickedListener{
            override fun onMovieClicked(position: Int) {
                TODO("Not yet implemented")
            }

        })
        binding.moviesList.adapter = movieListAdapter
        binding.moviesList.addOnScrollListener(object : PaginationScrollListener(binding.moviesList.linearLayoutManager) {
            override fun isLoading(): Boolean {
                val isLoading = binding.srlFragmentMovieList.isRefreshing

                if (isLoading) {
                    binding.loadingView.visible()
                } else {
                    binding.loadingView.gone()
                }

                return isLoading

            }

            override fun isLastPage(): Boolean {
                return viewModel.isLastPage()
            }

            override fun loadMoreItems() {
                viewModel.fetchNextPopularMovies()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }

    private fun observeViewModel() {
        viewModel.movieData.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        if (viewModel.isFirstPage()){
                            movieListAdapter?.clear()

                        }
                        movieListAdapter?.fillList(list)
                    }
                    binding.srlFragmentMovieList.isRefreshing = false
                }

                Resource.Status.ERROR -> {
                    result.message?.let {

                        binding.srlFragmentMovieList.isRefreshing = false
                        binding.loadingView.gone()
                        Snackbar.make(binding.srlFragmentMovieList, getString(R.string.error_message_pattern, it), Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                Resource.Status.LOADING -> {
                    binding.srlFragmentMovieList.isRefreshing = true
                }
            }

        })
    }

    private fun setupSwipeRefresh() {
        binding.srlFragmentMovieList.setOnRefreshListener {
            viewModel.refreshPopularMovies()
        }
    }
}