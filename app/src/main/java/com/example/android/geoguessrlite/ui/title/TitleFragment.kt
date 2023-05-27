package com.example.android.geoguessrlite.ui.title

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.geoguessrlite.databinding.FragmentTitleBinding
import com.example.android.geoguessrlite.ui.selection.SelectionType

const val GAME_TYPE_SHARED_PREFERENCES_KEY = "game-type"
const val GAME_DURATION_SHARED_PREFERENCES_KEY = "game-duration"

class TitleFragment : Fragment() {
    private val viewModel: TitleViewModel by lazy {
        ViewModelProvider(this)[TitleViewModel::class.java]
    }

    private var sharedPreferences: SharedPreferences? = null

    private lateinit var binding: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        sharedPreferences = activity?.getPreferences(Context.MODE_PRIVATE)

        binding = FragmentTitleBinding.inflate(inflater)
        binding.viewModel = viewModel

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)

        if (sharedPref != null) {
            val gameType =
                sharedPref.getString(GAME_TYPE_SHARED_PREFERENCES_KEY, DEFAULT_GAME_TYPE.label)
            val gameDuration = sharedPref.getString(
                GAME_DURATION_SHARED_PREFERENCES_KEY,
                DEFAULT_GAME_DURATION.label
            )
            viewModel.setGameType(gameType)
            viewModel.setGameDuration(gameDuration)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectGameType.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                TitleFragmentDirections.actionTitleFragmentToSelectionFragment(SelectionType.GAME_TYPE.label)
            )
        }

        binding.selectGameDuration.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                TitleFragmentDirections.actionTitleFragmentToSelectionFragment(SelectionType.GAME_DURATION.label)
            )
        }

        binding.startGameButton.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                TitleFragmentDirections.actionTitleFragmentToGameFragment()
            )
        }
    }
}
