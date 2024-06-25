package com.example.clothier.Model

class BestSeller(private val image: Int, private val offer: String) {

    fun getImage(): Int {
        return image
    }

    fun getOffer(): String {
        return offer
    }
}