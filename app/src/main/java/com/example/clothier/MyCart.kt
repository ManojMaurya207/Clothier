package com.example.clothier

import CartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.clothier.databinding.MyCartBinding

class MyCart : Fragment() {
    private lateinit var binding: MyCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyCartBinding.inflate(inflater, container, false)

        val cartClothName = listOf("Jeans", "Top", "shirt", "plajo", "gagra")
        val cartItemPrice = listOf("5", "13", "24", "45", "65")
        val cartImage = listOf(
            R.drawable.banner7,
            R.drawable.banner18,
            R.drawable.banner8,
            R.drawable.banner13,
            R.drawable.women_cloths
        )

        val adapter = CartAdapter(cartClothName, cartItemPrice, cartImage)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter

        return binding.root
    }

}
