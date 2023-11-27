package com.example.myapplication.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityYurtBilgiThreeBinding

class ImageRecyclerAdapter2(
    private val imageList: ArrayList<Uri>,
    private val onDeleteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ImageRecyclerAdapter2.ImageHolder>() {

    class ImageHolder(val binding: ActivityYurtBilgiThreeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = ActivityYurtBilgiThreeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {
        holder.binding.fotoYukle2.setImageURI(imageList[position])
    }
}
