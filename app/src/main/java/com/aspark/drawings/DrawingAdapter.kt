package com.aspark.drawings

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.aspark.drawings.databinding.ItemDrawingBinding
import com.aspark.drawings.model.Drawing
import com.aspark.drawings.utility.Utility
import com.bumptech.glide.Glide


class DrawingAdapter(private val drawingList:  List<Drawing>):
    RecyclerView.Adapter<DrawingAdapter.ViewHolder>() {

   inner class ViewHolder(binding: ItemDrawingBinding) : RecyclerView.ViewHolder(binding.root) {

        val tvName = binding.tvName
        val markers = binding.tvMarkers
        val timeAdded = binding.tvTimeAdded
        val image = binding.ivDrawingImg

        init {
            binding.root.setOnClickListener {

                val intent = Intent(it.context,DrawingProfileActivity::class.java)
                intent.putExtra("drawingId",drawingList[adapterPosition].id)
                it.context.startActivity(intent)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val binding = ItemDrawingBinding.inflate(LayoutInflater.from(parent.context),
            parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvName.text = drawingList[position].name
        holder.markers.text = drawingList[position].markers.toString()
        val timeAdded = drawingList[position].timeAdded
        holder.image.clipToOutline = true

        holder.timeAdded.text = Utility().formatTime(timeAdded)
        val uri = drawingList[position].imagePath.toUri()

        Glide
            .with(holder.itemView.context)
            .load(uri)
            .centerCrop()
            .into(holder.image)

    }

    override fun getItemCount(): Int {

        return drawingList.size
    }

    }