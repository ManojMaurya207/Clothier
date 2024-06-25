package com.example.clothier.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clothier.R

class BestSellerAdapter(private val bestSellerList: List<Pair<Int, String>>) :
    RecyclerView.Adapter<BestSellerAdapter.BestSellerViewHolder>() {


    // Define a listener interface
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    // Set the listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.best_seller, parent, false)
        return BestSellerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.bind(bestSellerList[position])

        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return bestSellerList.size
    }

    inner class BestSellerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mImageView: ImageView = itemView.findViewById(R.id.bestCategoryImage)
        private val mTextView: TextView = itemView.findViewById(R.id.categry)

        fun bind(item: Pair<Int, String>) {
            mTextView.text = item.second
            mImageView.setImageResource(item.first)
        }
    }
}