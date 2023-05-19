package com.example.android.geoguessrlite.ui.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.geoguessrlite.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {
    private val viewModel: TitleViewModel by lazy {
        ViewModelProvider(this)[TitleViewModel::class.java]
    }

    private lateinit var binding: FragmentTitleBinding

    private fun setGameSettings() {
        val args = TitleFragmentArgs.fromBundle(requireArguments())
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

        setGameSettings()
    }
}
