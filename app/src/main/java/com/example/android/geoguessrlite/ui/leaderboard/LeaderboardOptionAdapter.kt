package com.example.android.geoguessrlite.ui.leaderboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.geoguessrlite.database.score.GameScore
import com.example.android.geoguessrlite.databinding.LeaderboardItemViewBinding

class LeaderboardOptionAdapter : ListAdapter<GameScore, ViewHolder>(LeaderboardDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}

class ViewHolder private constructor(val binding: LeaderboardItemViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: GameScore) {
        binding.position = (this.adapterPosition + 1).toString()
        binding.score = item.finalScore.toString()
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LeaderboardItemViewBinding.inflate(layoutInflater, parent, false)

            return ViewHolder(binding)
        }
    }
}

class LeaderboardDiffCallback :
    DiffUtil.ItemCallback<GameScore>() {
    override fun areItemsTheSame(oldItem: GameScore, newItem: GameScore): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GameScore, newItem: GameScore): Boolean {
        return oldItem == newItem
    }
}
