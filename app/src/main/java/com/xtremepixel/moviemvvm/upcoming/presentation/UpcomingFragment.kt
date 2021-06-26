package com.xtremepixel.moviemvvm.upcoming.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.xtremepixel.moviemvvm.databinding.FragmentUpcomingBinding
import com.xtremepixel.moviemvvm.popular.presentation.viewmodel.PopularMovieViewModel
import com.xtremepixel.moviemvvm.upcoming.presentation.viewmodel.UpcomingMovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingFragment : Fragment() {

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

}