package com.example.clothier

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainNavigation : AppCompatActivity(){

    private lateinit var bottomNavigationView: BottomNavigationView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_navigation)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        val bundle: Bundle? = intent.extras
        val userid= bundle?.getInt("userid")
        val usernm= bundle?.getString("usernm")
        val email= bundle?.getString("email")


        replaceFragment(Home.newInstance(userid, usernm))



        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> replaceFragment(Home.newInstance(userid, usernm))
                R.id.favourite -> replaceFragment(Favorite())
                R.id.mycart -> replaceFragment(MyCart())
                R.id.profile -> replaceFragment(Profile())
                else -> {
                }
            }
            true
        }
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.kart, menu)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}
