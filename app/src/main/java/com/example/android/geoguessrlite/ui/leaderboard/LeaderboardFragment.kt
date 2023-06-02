package com.example.android.geoguessrlite.ui.leaderboard

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.geoguessrlite.databinding.FragmentLeaderboardBinding
import com.example.android.geoguessrlite.ui.selection.SelectionSource
import com.example.android.geoguessrlite.ui.selection.SelectionType
import com.example.android.geoguessrlite.ui.title.DEFAULT_GAME_DURATION
import com.example.android.geoguessrlite.ui.title.DEFAULT_GAME_TYPE
import com.example.android.geoguessrlite.ui.title.GAME_DURATION_SHARED_PREFERENCES_KEY
import com.example.android.geoguessrlite.ui.title.GAME_TYPE_SHARED_PREFERENCES_KEY
import com.example.android.geoguessrlite.ui.title.TitleFragmentDirections

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

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        if (sharedPref != null) {
            val gameType =
                sharedPref.getString(GAME_TYPE_SHARED_PREFERENCES_KEY, DEFAULT_GAME_TYPE.label)
            val gameDuration = sharedPref.getString(
                GAME_DURATION_SHARED_PREFERENCES_KEY,
                DEFAULT_GAME_DURATION.label
            )
            viewModel.setFilterParams(gameDuration, gameType)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectGameType.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                LeaderboardFragmentDirections.actionLeaderboardFragmentToSelectionFragment(SelectionType.GAME_TYPE.label, SelectionSource.LEADERBOARD_FRAGMENT.label)
            )
        }

        binding.selectGameDuration.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                LeaderboardFragmentDirections.actionLeaderboardFragmentToSelectionFragment(SelectionType.GAME_DURATION.label, SelectionSource.LEADERBOARD_FRAGMENT.label)
            )
        }
    }
}
