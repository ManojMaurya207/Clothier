package com.example.clothier.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothier.R

@SuppressLint("MissingInflatedId")
class ClothingAdapter(private val clothingList: MutableList<Pair<Int, String>>) :
    RecyclerView.Adapter<ClothingAdapter.ClothingViewHOlder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClothingViewHOlder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.clothing, parent, false)
        return ClothingViewHOlder(view)
    }

    override fun onBindViewHolder(holder: ClothingViewHOlder, position: Int) {
        holder.offer.text = clothingList[position].second
        holder.mImageView.setImageResource(clothingList[position].first)
    }

    override fun getItemCount(): Int {
        return clothingList.size
    }

    inner class ClothingViewHOlder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val offer: TextView
        val mImageView: ImageView

        init {
            offer = itemView.findViewById<TextView>(R.id.clothing_offer_tv)
            mImageView = itemView.findViewById<ImageView>(R.id.clothing_image)
        }
    }
}