package com.example.clothier

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clothier.Adapter.BestSellerAdapter
import com.example.clothier.Adapter.ClothingAdapter
import com.example.clothier.Adapter.OfferAdapter
import kotlin.properties.Delegates



class Home : Fragment() {

    private lateinit var bestSellerRecyclerView: RecyclerView
    private lateinit var clothingRecyclerView: RecyclerView
    private lateinit var offerRecyclerView: RecyclerView
    private lateinit var button : ImageButton
    private val imageArray = arrayOf(
        R.drawable.banner18,
        R.drawable.banner8,
        R.drawable.banner13,
        R.drawable.banner7
    )
    private var currentIndex = 0
    private val handler = Handler()
    private var userid by Delegates.notNull<Int>()
    lateinit var usernm :String
    lateinit var category:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        userid = arguments?.getInt("userid", -1) ?: -1
        usernm = arguments?.getString("usernm").toString()
        category="Casual"
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var UserName:TextView= view.findViewById(R.id.top_name)
        UserName.text="HELLO "+usernm.toUpperCase()+" !"
        //To be added in Grid
        button = view.findViewById(R.id.filterButton)
        button.setOnClickListener {
//            val bottomSheetFragment = BottomSheetFragment()
//            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }



        //Best Seller
        bestSellerRecyclerView = view.findViewById(R.id.bestCategoryRecylerView)

        bestSellerRecyclerView.setHasFixedSize(true)
        bestSellerRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val bestSeller: MutableList<Pair<Int, String>> = mutableListOf()
        bestSeller.add(Pair(R.drawable.graphic_tee, "Casual"))
        bestSeller.add(Pair(R.drawable.churidar, "Ethnic"))
        bestSeller.add(Pair(R.drawable.asymmetric_hem_dress, "Party"))
        bestSeller.add(Pair(R.drawable.fff_the_shoulder_top, "Western"))
        bestSeller.add(Pair(R.drawable.pant_suit_with_blazer, "Formal"))

        val bestSellerAdapter = BestSellerAdapter(bestSeller)
        bestSellerRecyclerView.adapter = bestSellerAdapter

        bestSellerAdapter.setOnItemClickListener(object : BestSellerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedItem = bestSeller[position].second
                navigateToNewFragment(Product_Grid.newInstance(userid, usernm ,selectedItem))
//                Toast.makeText(requireContext(), "Clicked on $selectedItem", Toast.LENGTH_SHORT).show()
            }
        })


        //Clothing
        clothingRecyclerView = view.findViewById(R.id.offerRecylerView)
        clothingRecyclerView.setHasFixedSize(true)
        clothingRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val clothingList: MutableList<Pair<Int, String>> = mutableListOf()
        clothingList.add(Pair(R.drawable.banner3, "Up to 20% off"))
        clothingList.add(Pair(R.drawable.women_cloths, "Up to 20% off"))
        clothingList.add(Pair(R.drawable.banner4, "Up to 20% off"))
        val clothingAdapter = ClothingAdapter(clothingList)
        clothingRecyclerView.adapter = clothingAdapter

        //Offer
        offerRecyclerView = view.findViewById(R.id.favoriteRecylerView)
        offerRecyclerView.setHasFixedSize(true)
        offerRecyclerView.layoutManager =LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val imageList: MutableList<Int> = mutableListOf()
        imageList.add(R.drawable.banner4)
        imageList.add(R.drawable.banner5)
        imageList.add(R.drawable.banner6)
        val offerAdapter = OfferAdapter(imageList)
        offerRecyclerView.adapter = offerAdapter
        view.findViewById<ImageView>(R.id.bannerscroll).setImageResource(imageArray[currentIndex])

        // Start automatic image scroll
        startAutoImageScroll()
    }

    private fun startAutoImageScroll() {
        handler.postDelayed({
            currentIndex = (currentIndex + 1) % imageArray.size
            val currentImage = imageArray[currentIndex]
            view?.findViewById<ImageView>(R.id.bannerscroll)?.setImageResource(currentImage)

            // Start the next iteration after a delay
            handler.postDelayed({
                startAutoImageScroll()
            }, 2000)
        }, 2000) // Start after 2 seconds
    }

    fun navigateToNewFragment(newFragment: Fragment) {
        // Create a FragmentTransaction
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, newFragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }

    companion object {
        fun newInstance(userid: Int?, usernm: String?): Home {
            val fragment = Home()
            val args = Bundle()
            args.putInt("userid", userid ?: -1)
            args.putString("usernm", usernm)
            fragment.arguments = args
            return fragment
        }
    }

}
