package com.example.myapplication.adapter

import IlanBilgileri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class IlanlarAdapter(private val ilanlarList: List<IlanBilgileri>) : RecyclerView.Adapter<IlanlarAdapter.IlanlarViewHolder>() {

    class IlanlarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ilanBasligi: TextView = itemView.findViewById(R.id.ilanBasligiTEXT)
        val ilanFiyat: TextView = itemView.findViewById(R.id.ilanAciklamasiTEXT)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IlanlarViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_ilan_cell, parent, false)
        return IlanlarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IlanlarViewHolder, position: Int) {
        val currentItem = ilanlarList[position]

        holder.ilanBasligi.text = currentItem.ilanBasligi
        holder.ilanFiyat.text = currentItem.ilanFiyat
        // Diğer özellikleri de buraya ekleyebilirsiniz.
    }

    override fun getItemCount(): Int {
        return ilanlarList.size
    }
}