package com.example.myapplication.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityEvArkadasiFourBinding


class evArkadasiRecyclerAdapter(
    private val imageList: ArrayList<Uri>,
    private val onDeleteClickListener: (Int) -> Unit
) : RecyclerView.Adapter<evArkadasiRecyclerAdapter.ImageHolder>() {

    class ImageHolder(val binding: ActivityEvArkadasiFourBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        val binding = ActivityEvArkadasiFourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageHolder(binding)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: evArkadasiRecyclerAdapter.ImageHolder, position: Int) {

        // ImageView'ın URI'sini, bind edilmiş olan ImageView'a yükleyin.
        holder.binding.evArkadasiFoto.setImageURI(imageList[position])


        // İsteğe bağlı olarak, bu kısmı kullanarak ImageView'a tıklandığında bir işlem gerçekleştirebilirsiniz.
        holder.binding.evArkadasiFoto.setOnClickListener {
            onDeleteClickListener.invoke(position)
        }
    }

}
