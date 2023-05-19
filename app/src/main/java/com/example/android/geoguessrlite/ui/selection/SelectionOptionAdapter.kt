package com.example.android.geoguessrlite.ui.selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.geoguessrlite.R
import com.example.android.geoguessrlite.databinding.SelectionItemViewBinding

class SelectionOptionAdapter : ListAdapter<String, ViewHolder>(SleepNightDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder private constructor(val binding: SelectionItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        binding.selectionButton.text = item
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = SelectionItemViewBinding.inflate(layoutInflater, parent, false)

            return ViewHolder(binding)

//            val view = layoutInflater
//                .inflate(R.layout.selection_item_view, parent, false)
//
//            return ViewHolder(view)
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
