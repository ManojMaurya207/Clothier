package com.example.clothier.Model

class Clothing(private val image: Int, private val offer: String) {

    fun getImage(): Int {
        return image
    }

    fun getOffer(): String {
        return offer
    }
}