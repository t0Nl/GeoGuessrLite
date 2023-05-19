package com.example.android.geoguessrlite.ui.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.geoguessrlite.databinding.SelectionItemViewBinding

class SelectionOptionAdapter(val clickListener: OptionClickListenerListener) : ListAdapter<String, ViewHolder>(SleepNightDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder private constructor(val binding: SelectionItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(clickListener: OptionClickListenerListener, item: String) {
        binding.label = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SelectionItemViewBinding.inflate(layoutInflater, parent, false)

            return ViewHolder(binding)
        }
    }
}

class SleepNightDiffCallback :
    DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class OptionClickListenerListener(val clickListener: (label: String) -> Unit) {
    fun onClick(selectedOption: String) = clickListener(selectedOption)
}
