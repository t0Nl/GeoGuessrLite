package com.example.android.geoguessrlite.ui.leaderboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.geoguessrlite.databinding.FragmentLeaderboardBinding

class LeaderboardFragment : Fragment() {
    private val viewModel: LeaderboardViewModel by lazy {
        ViewModelProvider(this)[LeaderboardViewModel::class.java]
    }

    private lateinit var binding: FragmentLeaderboardBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = LeaderboardOptionAdapter()

        binding = FragmentLeaderboardBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.optionList.adapter = adapter

        viewModel.gameScores.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}
