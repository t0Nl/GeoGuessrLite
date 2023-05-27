package com.example.android.geoguessrlite.ui.selection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.geoguessrlite.databinding.FragmentSelectionBinding
import com.example.android.geoguessrlite.ui.title.GAME_DURATION_SHARED_PREFERENCES_KEY
import com.example.android.geoguessrlite.ui.title.GAME_TYPE_SHARED_PREFERENCES_KEY

class SelectionFragment : Fragment() {
    private val viewModel: SelectionViewModel by lazy {
        ViewModelProvider(this)[SelectionViewModel::class.java]
    }

    private lateinit var binding: FragmentSelectionBinding

    private fun setFragmentType() {
        val args = SelectionFragmentArgs.fromBundle(requireArguments())
        viewModel.setSelectionOptions(args.selectionType)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val adapter = SelectionOptionAdapter(
            OptionClickListenerListener { label ->
                viewModel.onOptionClicked(label)
            }
        )

        binding = FragmentSelectionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.optionList.adapter = adapter

        viewModel.fragmentSelectionOptions.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.finishSelection.observe(viewLifecycleOwner, Observer { label ->
            label?.let {
                when (viewModel.fragmentSelectionType.value) {
                    SelectionType.GAME_TYPE -> {
                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                        if (sharedPref != null) {
                            with(sharedPref.edit()) {
                                putString(GAME_TYPE_SHARED_PREFERENCES_KEY, label)
                                apply()
                            }
                        }

                        this.findNavController().navigate(
                            SelectionFragmentDirections.actionSelectionFragmentToTitleFragment()
                        )
                    }

                    else -> {
                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                        if (sharedPref != null) {
                            with(sharedPref.edit()) {
                                putString(GAME_DURATION_SHARED_PREFERENCES_KEY, label)
                                apply()
                            }
                        }

                        this.findNavController().navigate(
                            SelectionFragmentDirections.actionSelectionFragmentToTitleFragment()
                        )
                    }
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentType()
    }
}