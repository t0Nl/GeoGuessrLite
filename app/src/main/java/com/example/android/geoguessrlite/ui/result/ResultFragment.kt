package com.example.android.geoguessrlite.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.android.geoguessrlite.R
import com.example.android.geoguessrlite.databinding.FragmentResultBinding

private const val CUP_IMAGE_URL =
    "https://icons-for-free.com/iconfiles/png/512/cup+podium+prize+sport+win+winner+icon-1320165901243634768.png"
private const val CUP_IMAGE_SIZE = 500

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

        context?.let {
            Glide.with(it)
                .load(CUP_IMAGE_URL)
                .placeholder(R.drawable.leaderboard_icon)
                .error(R.drawable.ic_error)
                .override(CUP_IMAGE_SIZE, CUP_IMAGE_SIZE)
                .centerCrop()
                .into(binding.resultScreenImage)
        }
    }
}
