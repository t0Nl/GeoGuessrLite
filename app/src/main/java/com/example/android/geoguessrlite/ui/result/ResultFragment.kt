package com.example.android.geoguessrlite.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.geoguessrlite.databinding.FragmentGameBinding
import com.example.android.geoguessrlite.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private val viewModel: ResultViewModel by lazy {
        ViewModelProvider(this)[ResultViewModel::class.java]
    }

    private lateinit var binding: FragmentResultBinding

    private fun setResults() {
        val args = ResultFragmentArgs.fromBundle(requireArguments())
        binding.categoryValue.text = args.gameCategory
        binding.durationValue.text = args.gameDuration.toString()
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
    }
}
