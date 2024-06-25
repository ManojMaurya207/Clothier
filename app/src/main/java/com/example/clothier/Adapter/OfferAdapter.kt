package com.example.clothier.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothier.R


class OfferAdapter(private val imageList: List<Int>) :
    RecyclerView.Adapter<OfferAdapter.OfferViewHOlder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHOlder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.offer, parent, false)
        return OfferViewHOlder(view)
    }

    override fun onBindViewHolder(holder: OfferViewHOlder, position: Int) {
        holder.mImageView.setImageResource(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class OfferViewHOlder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageView: ImageView

        init {
            mImageView = itemView.findViewById<ImageView>(R.id.offer_iv)
        }
    }
}