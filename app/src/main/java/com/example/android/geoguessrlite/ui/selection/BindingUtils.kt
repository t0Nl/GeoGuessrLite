package com.example.android.geoguessrlite.ui.selection

import android.widget.Button
import androidx.databinding.BindingAdapter

@BindingAdapter("label")
fun Button.setLabel(item: String?) {
    text = item
}
