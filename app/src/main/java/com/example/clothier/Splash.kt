package com.example.clothier

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView

class Splash : AppCompatActivity() {
    lateinit var logo :ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()
//        Thread.sleep(5000)
        setContentView(R.layout.activity_splash)
        logo=findViewById(R.id.Splash_logo)
        val animation=android.view.animation.AnimationUtils.loadAnimation(applicationContext,R.anim.splash_animation)
        logo.startAnimation(animation)
        Handler(Looper.getMainLooper()).postDelayed({

            startActivity(Intent(this,OnBoarding::class.java))
            finish()
        },1300)

    }
}