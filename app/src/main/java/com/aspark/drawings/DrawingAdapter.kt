package com.aspark.drawings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspark.drawings.databinding.ItemDrawingBinding

class DrawingAdapter: RecyclerView.Adapter<DrawingAdapter.ViewHolder>() {

    private lateinit var binding: ItemDrawingBinding

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        binding = ItemDrawingBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)

        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}