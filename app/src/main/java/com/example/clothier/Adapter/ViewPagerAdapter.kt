package com.example.clothier.Adapter

import android.view.View
import androidx.viewpager.widget.PagerAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.clothier.R

class ViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private val images = arrayOf(
        R.drawable.onboard1,
        R.drawable.onboard2,
        R.drawable.onboard3,
    )

    private val headings = arrayOf(
        R.string.heading_one,
        R.string.heading_two,
        R.string.heading_three,
    )

    private val descriptions = arrayOf(
        R.string.desc_one,
        R.string.desc_two,
        R.string.desc_three,
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slider_layout, container, false)

        val slideTitleImage: ImageView = view.findViewById(R.id.titleImg)
        val slideHeading: TextView = view.findViewById(R.id.titlelb)
        val slideDescription: TextView = view.findViewById(R.id.desclb)

        slideTitleImage.setImageResource(images[position])
        slideHeading.setText(headings[position])
        slideDescription.setText(descriptions[position])

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
