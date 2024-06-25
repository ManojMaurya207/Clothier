package com.example.clothier

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Product_Grid.newInstance] factory method to
 * create an instance of this fragment.
 */
class Product_Grid : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var userid: Int=1
    lateinit var category:String
    lateinit var db1:SQLiteDatabase
    lateinit var db:SQLiteDatabase
    lateinit var grid:GridView
    var modelList=ArrayList<grid_item_object>()
    var casual= intArrayOf(R.drawable.boyfriend_shirt,R.drawable.button_front_midi_dress,
        R.drawable.cargo_pants,R.drawable.casual_jumsuit,
        R.drawable.floral_sundress,R.drawable.graphic_tee,R.drawable.high_low_hem_tunic,
        R.drawable.hoodie_dress,R.drawable.ribbed_knit_dress,R.drawable.striped_shrit_dress)

    var ethnic= intArrayOf(R.drawable.bandhani,R.drawable.churidar,
        R.drawable.jaipuri_lehanga,R.drawable.kalamkari_anarkali,
        R.drawable.kerala_kasavu_saree,R.drawable.lehanga,R.drawable.mysore_silk_saree,
        R.drawable.navari,R.drawable.salwaar_kameez,R.drawable.saree)

    var party= intArrayOf(R.drawable.asymmetric_hem_dress,R.drawable.crystal_embellished_gown,
        R.drawable.embellished_cocktail_dress,R.drawable.feather_trimmed_dress,
        R.drawable.lace_bodycon_dress,R.drawable.mesh_panel_dress,R.drawable.metallic_sheath_dress,
        R.drawable.satin_slip_dress,R.drawable.tiered_ruffle_dress,R.drawable.velvet_gown)

    var western= intArrayOf(R.drawable.boho_maxi_skirt,R.drawable.bomber_jacket,
        R.drawable.crop_top,R.drawable.denim_overalls,
        R.drawable.fff_the_shoulder_top,R.drawable.plaid_shirt_dress,R.drawable.pleated_skirt,
        R.drawable.prairie_dress,R.drawable.sack_dress,R.drawable.skinny_jeans)

    var formal= intArrayOf(R.drawable.a_line_dress,R.drawable.belted_maxi_dress,
        R.drawable.empire_waist_gown,R.drawable.flared_sleeve_blouse,
        R.drawable.pant_suit_with_blazer,R.drawable.pinafore_dress,R.drawable.pleated_palazzo_pants,
        R.drawable.structured_sheath_dress,R.drawable.tuxedo_dress,R.drawable.wide_leg_pants)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            userid = arguments?.getInt("userid", 1) ?: 1
            category = arguments?.getString("category").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product_grid, container, false)
        // Initialize your views and adapters here
        grid = view.findViewById(R.id.category_grid)
        var imgs = ethnic
        when(category){
            "Casual"-> imgs=casual
            "Ethnic"-> imgs=ethnic
            "Party"-> imgs=party
            "Western"-> imgs=western
            "Formal"-> imgs=formal
        }

        db1 = requireActivity().openOrCreateDatabase("Favourite", Context.MODE_PRIVATE, null)
        val fav_product = mutableListOf<Int>()
        val cursor1 = db1.rawQuery("Select p_id from Favourite where u_id=$userid", null)
        if (cursor1 != null && cursor1.moveToFirst()) {
            do {
                val uid = cursor1.getInt(0)
                fav_product.add(uid)
            } while (cursor1.moveToNext())
        }
        cursor1?.close()

        db = requireActivity().openOrCreateDatabase("Product", Context.MODE_PRIVATE, null)
        val idfromDB = mutableListOf<Int>()
        val namesFromDB = mutableListOf<String>()
        val pricesFromDB = mutableListOf<Int>()
        val cursor = db.rawQuery("SELECT pid, pname, price FROM Product where category='$category'", null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val price = cursor.getInt(2)

                // Add name and image data to respective lists
                idfromDB.add(id)
                namesFromDB.add(name)
                pricesFromDB.add(price)

            } while (cursor.moveToNext())
        }
        cursor?.close()
        val ids = idfromDB.toIntArray()
        val names = namesFromDB.toTypedArray()
        val price = pricesFromDB.toIntArray()
        for (i in names.indices) {
            modelList.add(grid_item_object(ids[i], names[i], imgs[i], price[i]))
        }

        val cust_adap = customAdapter(modelList, requireContext(), fav_product, userid, db1)
        grid.adapter = cust_adap

        grid.setOnItemClickListener { adapterView, view, i, l ->

            //Remove Comment to Run as Fragment
            navigateToNewFragment(Product_page.newInstance(userid,ids[i], names[i], imgs[i], price[i]))

//            Toast.makeText(requireContext(),"Reaching to product page  user "+names[1],Toast.LENGTH_LONG).show()
            //Remove Comment to Run as bottomSheet
//            val bottomSheetFragment = Product_page.newInstance(userid,ids[i], names[i], imgs[i], price[i])
//            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
        return view
    }




    class customAdapter(
        var g_items: ArrayList<grid_item_object>,
        var context: Context,
        var fav_products:MutableList<Int>,
        var userid: Int,
        var db:SQLiteDatabase
    ): BaseAdapter(){

        var layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        override fun getCount(): Int {
            return g_items.size
        }

        override fun getItem(position: Int): Any {
            return g_items[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, view: View?, viewgroup: ViewGroup?): View {
            var view=view
            if (view==null){
                view=layoutInflater.inflate(R.layout.grid_item,viewgroup,false)
            }
            var imgname=view?.findViewById<TextView>(R.id.img_name)
            val img = view?.findViewById<ImageView>(R.id.grid_img)
            val favoriteIcon = view?.findViewById<ImageView>(R.id.fav_img)
            var price=view?.findViewById<TextView>(R.id.img_price)
            val currentItem=g_items[position]

            if (fav_products.contains(currentItem.id)) {
                favoriteIcon?.setImageResource(R.drawable.ic_action_favourite_red) // Use filled heart icon
            } else {
                favoriteIcon?.setImageResource(R.drawable.ic_action_favourite_fore) // Use empty heart icon
            }

            favoriteIcon?.setOnClickListener {
                // Toggle favorite status when the favorite icon is clicked
                if (fav_products.contains(currentItem.id)) {
                    // Remove product from favorites
                    fav_products.remove(currentItem.id)
                    // Change heart icon to empty
                    favoriteIcon.setImageResource(R.drawable.ic_action_favourite_fore)
                    // TODO: Remove product from user's favorites in the database
                    db.execSQL("DELETE FROM Favourite WHERE p_id ="+currentItem.id)

                } else {
                    // Add product to favorites
                    fav_products.add(currentItem.id!!)
                    // Change heart icon to filled
                    favoriteIcon.setImageResource(R.drawable.ic_action_favourite_red)
                    // TODO: Add product to user's favorites in the database
                    db.execSQL(("INSERT INTO Favourite (u_id,p_id) VALUES("+userid+","+currentItem.id+")"))
                }
            }

            imgname?.text=currentItem.name
            img?.setImageResource(currentItem.image1!!)
            price?.text= currentItem.price.toString()+"/-"
            return view!!
        }

    }



    fun navigateToNewFragment(newFragment: Fragment) {
        // Create a FragmentTransaction
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, newFragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }

    companion object {
        fun newInstance(userid: Int, usernm: String, category: String?): Product_Grid {
            val fragment = Product_Grid()
            val args = Bundle()
            args.putInt("userid", userid)
            args.putString("usernm", usernm)
            args.putString("category", category)
            fragment.arguments = args
            return fragment
        }
    }
}