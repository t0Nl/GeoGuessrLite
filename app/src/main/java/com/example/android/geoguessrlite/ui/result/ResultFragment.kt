package com.example.android.geoguessrlite.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.geoguessrlite.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding

    private fun setResults() {
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        binding.categoryValue.text = args.gameType
        binding.durationValue.text = args.gameDuration
        binding.finalScoreValue.text = args.finalScore.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setResults()

        binding.playAgainButton.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                ResultFragmentDirections.actionResultFragmentToGameFragment()
            )
        }

        binding.seeLeaderboardButton.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                ResultFragmentDirections.actionResultFragmentToLeaderboardFragment()
            )
        }

        binding.returnToTitleButton.setOnClickListener { buttonView: View ->
            buttonView.findNavController().navigate(
                ResultFragmentDirections.actionResultFragmentToTitleFragment()
            )
        }
    }
}
