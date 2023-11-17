package com.example.myapplication.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityAdInformationFourBinding

class ImageRecyclerAdapter(
    private val imageList: ArrayList<Uri>,
    private val onDeleteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder>() {

    class ImageHolder(val binding: ActivityAdInformationFourBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = ActivityAdInformationFourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.binding.fotoYukle.setImageURI(imageList[position])

    }
}
