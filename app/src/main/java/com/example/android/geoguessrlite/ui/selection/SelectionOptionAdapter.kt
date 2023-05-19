package com.example.android.geoguessrlite.ui.selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.android.geoguessrlite.R

class SelectionOptionAdapter : RecyclerView.Adapter<ViewHolder>() {
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.selectionButton.text = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.selection_item_view, parent, false)
        return ViewHolder(view)
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val selectionButton: Button = itemView.findViewById(R.id.selectionButton)
}
