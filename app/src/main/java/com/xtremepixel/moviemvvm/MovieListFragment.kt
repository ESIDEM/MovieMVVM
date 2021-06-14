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
import com.xtremepixel.moviemvvm.adapter.MovieAdapter
import com.xtremepixel.moviemvvm.data.Resource
import com.xtremepixel.moviemvvm.databinding.FragmentMovieListBinding
import com.xtremepixel.moviemvvm.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel by viewModels<MovieViewModel>()
    lateinit var movieRecyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var errorTextView: TextView
    private val movieListAdapter = MovieAdapter(arrayListOf(),object : MovieAdapter.MovieClickedListener{
        override fun onMovieClicked(position: Int) {
            Toast.makeText(context, "${position}",Toast.LENGTH_LONG).show()
        }

    })
    lateinit var binding: FragmentMovieListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieListBinding.inflate(layoutInflater,container,false)


        movieRecyclerView = binding.moviesList
        progressBar = binding.loadingView
        errorTextView = binding.listError
        movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieListAdapter
        }
            observeViewModel()
        return binding.root
    }

    private fun observeViewModel() {
        viewModel.movieData.observe(viewLifecycleOwner, Observer { result ->

            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
                        movieListAdapter.updateCountries(list)
                    }
                    progressBar.visibility = View.GONE
                }

                Resource.Status.ERROR -> {
                    result.message?.let {
                        errorTextView.visibility = View.VISIBLE
                    }
                    progressBar.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                }
            }

        })
    }

}