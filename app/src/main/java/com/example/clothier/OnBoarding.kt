package com.example.clothier
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.clothier.Adapter.ViewPagerAdapter

class OnBoarding : AppCompatActivity() {

    private lateinit var mSlideViewPager: ViewPager
    private lateinit var mDotLayout: LinearLayout
    private lateinit var backBtn: Button
    private lateinit var backImg: ImageView
    private lateinit var nextBtn: Button
    private lateinit var skipBtn: Button

    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.boarding_on)

        backBtn = findViewById(R.id.prevbtn)
        nextBtn = findViewById(R.id.nextbtn)
        skipBtn = findViewById(R.id.skipbtn)
        backImg = findViewById(R.id.pervimg)

        backBtn.setOnClickListener {
            if (getItem(0) > 0) {
                mSlideViewPager.setCurrentItem(getItem(-1), true)
            }
        }
        nextBtn.setOnClickListener {
            if (getItem(0) < 2) {
                mSlideViewPager.setCurrentItem(getItem(1), true)
            } else {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
                finish()
            }
        }

        skipBtn.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            finish()

        }

        mSlideViewPager = findViewById(R.id.pageSlider)
        mDotLayout = findViewById(R.id.indicator_lay)

        viewPagerAdapter = ViewPagerAdapter(this)
        mSlideViewPager.adapter = viewPagerAdapter

        setUpIndicator(0)
        mSlideViewPager.addOnPageChangeListener(viewListener)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun setUpIndicator(position: Int) {
        dots = Array(3) { TextView(this) }
        mDotLayout.removeAllViews()

        for (i in dots.indices) {
            dots[i] = TextView(this)
            dots[i].text =  Html.fromHtml("&#8226")
            dots[i].textSize = 45f
            dots[i].setTextColor(resources.getColor(R.color.inactive, applicationContext.theme))
            mDotLayout.addView(dots[i])
        }

        dots[position].setTextColor(resources.getColor(R.color.active, applicationContext.theme))
    }

    val viewListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onPageSelected(position: Int) {
            setUpIndicator(position)
            backBtn.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE
            backImg.visibility = if (position > 0) View.VISIBLE else View.INVISIBLE

        }

        override fun onPageScrollStateChanged(state: Int) {
        }
    }

    private fun getItem(i: Int): Int {
        return mSlideViewPager.currentItem + i
    }
}
