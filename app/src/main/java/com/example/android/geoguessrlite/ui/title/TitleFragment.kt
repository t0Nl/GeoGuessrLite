package com.example.android.geoguessrlite.ui.title

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.android.geoguessrlite.databinding.FragmentTitleBinding
import com.example.android.geoguessrlite.ui.selection.SelectionType

class TitleFragment : Fragment() {
    private val viewModel: TitleViewModel by lazy {
        ViewModelProvider(this)[TitleViewModel::class.java]
    }

    private lateinit var binding: FragmentTitleBinding

    private fun setGameSettings() {
        val args = TitleFragmentArgs.fromBundle(requireArguments())
        Log.e("TONI", "LESS GO")
        args.gameType?.let {
            viewModel.setGameType(it)
        }
        args.gameDuration?.let {
            viewModel.setGameDuration(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTitleBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.selectGameType.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToSelectionFragment(SelectionType.GAME_TYPE.label))
        }
        binding.selectGameDuration.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToSelectionFragment(SelectionType.GAME_DURATION.label))
        }

        setGameSettings()
    }
}
