package com.example.myapplication.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityAdInformationFourBinding

class ImageRecyclerAdapter(private val imageList: ArrayList<Image>) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder>() {

    class ImageHolder(val binding: ActivityAdInformationFourBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = ActivityAdInformationFourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }


    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.binding.imageRecyclerView.adapter //= imageList[position].email
        holder.binding.imageRecyclerView.adapter//= imageList[position].comment
    }
}
