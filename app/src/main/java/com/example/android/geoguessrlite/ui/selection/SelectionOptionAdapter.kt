package com.example.android.geoguessrlite.ui.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.android.geoguessrlite.R

class SelectionOptionAdapter : RecyclerView.Adapter<ButtonItemViewHolder>() {
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ButtonItemViewHolder, position: Int) {
        val item = data[position]
        holder.buttonView.text = item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.selection_item_view, parent, false) as Button
        return ButtonItemViewHolder(view)
    }
}

class ButtonItemViewHolder(val buttonView: Button) : RecyclerView.ViewHolder(buttonView)
