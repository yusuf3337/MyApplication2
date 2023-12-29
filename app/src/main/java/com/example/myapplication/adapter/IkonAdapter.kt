package com.example.myapplication.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class IkonAdapter(private val ikonListesi: List<Drawable>) : RecyclerView.Adapter<IkonAdapter.IkonViewHolder>() {

    inner class IkonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iconImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IkonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ikoncell, parent, false)
        return IkonViewHolder(view)
    }

    override fun onBindViewHolder(holder: IkonViewHolder, position: Int) {
        // İlgili konumda bulunan ikonu göster
        holder.imageView.setImageDrawable(ikonListesi[position])
    }

    override fun getItemCount(): Int {
        // İkon listesinin boyutunu döndür
        return ikonListesi.size
    }
}