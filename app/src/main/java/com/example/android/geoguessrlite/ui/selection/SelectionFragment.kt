package com.example.android.geoguessrlite.ui.selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.geoguessrlite.databinding.FragmentSelectionBinding

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
        val adapter = SelectionOptionAdapter()

        binding = FragmentSelectionBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.optionList.adapter = adapter

        viewModel.fragmentSelectionOptions.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentType()
    }
}