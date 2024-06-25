package com.example.clothier

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class Favorite : Fragment() {
    private lateinit var grid: GridView
    private var modelList = ArrayList<FavItem>()
    private val favourite = intArrayOf(
        R.drawable.boyfriend_shirt, R.drawable.mysore_silk_saree,
        R.drawable.embellished_cocktail_dress, R.drawable.denim_overalls,
        R.drawable.pleated_skirt, R.drawable.pleated_palazzo_pants
    )
    private val names = mutableListOf<String>(
        "Boyfriend Shirt", "Mysore silk saree", "Embellished Cocktail dress", "Denim Overalls",
        "Pleated Skirt", "Pleated Palazzo Pants"
    )
    private val prices = mutableListOf<Int>(199, 499, 1899, 699, 2999, 399)

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favourite, container, false)
        grid = rootView.findViewById(R.id.favourite_grid)

        // Add the logic for retrieving uid and deciding category here

        for (i in names.indices) {
            modelList.add(FavItem(names[i], favourite[i], prices[i]))
        }

        val custAdapter = CustomAdapter(modelList, requireContext())
        grid.adapter = custAdapter

        return rootView
    }








    private class CustomAdapter(
        private val items: ArrayList<FavItem>,
        private val context: Context
    ) : BaseAdapter() {
        private val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int = items.size

        override fun getItem(position: Int): Any = items[position]

        override fun getItemId(position: Int): Long = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var itemView = convertView
            val holder: ViewHolder

            if (itemView == null) {
                itemView = layoutInflater.inflate(R.layout.grid_item, parent, false)
                holder = ViewHolder()
                holder.imgNameTextView = itemView.findViewById(R.id.img_name)
                holder.imgImageView = itemView.findViewById(R.id.grid_img)
                holder.favoriteIconImageView = itemView.findViewById(R.id.fav_img)
                holder.priceTextView = itemView.findViewById(R.id.img_price)
                itemView.tag = holder
            } else {
                holder = itemView.tag as ViewHolder
            }

            val currentItem = items[position]

            holder.imgNameTextView?.text = currentItem.name
            holder.favoriteIconImageView?.setImageResource(R.drawable.ic_action_favourite_red)
            holder.imgImageView?.setImageResource(currentItem.image1!!)
            holder.priceTextView?.text = "${currentItem.price}/-"

            return itemView!!
        }

        private class ViewHolder {
            var imgNameTextView: TextView? = null
            var imgImageView: ImageView? = null
            var favoriteIconImageView: ImageView? = null
            var priceTextView: TextView? = null
        }
    }

    data class FavItem(val name: String, val image1: Int, val price: Int)

}