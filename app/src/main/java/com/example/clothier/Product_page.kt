package com.example.clothier

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.openOrCreateDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Product_page.newInstance] factory method to
 * create an instance of this fragment.
 */
class Product_page : Fragment() {
    private lateinit var db1: SQLiteDatabase
    private lateinit var db: SQLiteDatabase
    private lateinit var pname: TextView
    private lateinit var pprice: TextView
    private lateinit var f_img: ImageView
    private lateinit var pimg: ImageView
    private lateinit var pdesc: TextView
    private lateinit var stock_mess: TextView
    private lateinit var buy_btn: Button
    private lateinit var kart_btn: Button
    private lateinit var newprice: String

    private var userid: Int = 0
    private var pro_id: Int = 0
    private lateinit var pro_name: String
    private var pro_img: Int = 0
    private var pro_price: Int = 0

    fun removeCurrency(price: String): String {
        return price.replace("/-", "").trim()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_product_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userid = arguments?.getInt("userid") ?: 0
        pro_id = arguments?.getInt("id") ?: 0
        pro_name = arguments?.getString("name") ?: ""
        pro_img = arguments?.getInt("img") ?: 0
        pro_price = arguments?.getInt("price") ?: 0

        var desc: String = ""
        var stock: Int = 0

        pname = view.findViewById(R.id.pname)
        f_img = view.findViewById(R.id.f_img)
        pprice = view.findViewById(R.id.pprice)
        pimg = view.findViewById(R.id.pimg)
        pdesc = view.findViewById(R.id.pdesc)
        stock_mess = view.findViewById(R.id.quan_mess)

        pname.text = pro_name
        pimg.setImageResource(pro_img)
        pprice.text = pro_price.toString()
        newprice = removeCurrency(pro_price.toString())






        db = requireActivity().openOrCreateDatabase("Product", Context.MODE_PRIVATE, null)
        val cursor = db.rawQuery("SELECT pid,desc,stock FROM Product where pname='$pro_name'", null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val pid = cursor.getInt(0)
                desc = cursor.getString(1)
                stock = cursor.getInt(2)
            } while (cursor.moveToNext())
        }
        cursor?.close()

        val message: String

        message = if (stock <= 10) {
            "Hurry Up! Only $stock left"
        } else {
            ""
        }
        stock_mess.text = message
        pdesc.text = desc

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

        if (fav_product.contains(userid)) {
            f_img.setImageResource(R.drawable.ic_action_favourite_red) // Use filled heart icon
        } else {
            f_img.setImageResource(R.drawable.ic_action_favourite_fore) // Use empty heart icon
        }

        buy_btn = view.findViewById(R.id.buy)
        kart_btn = view.findViewById(R.id.cart)

        buy_btn.setOnClickListener {
            navigateToNewFragment(Order_Page_details.newInstance(userid, pro_id, pro_name, newprice.toInt()))

            Toast.makeText(requireContext(), "Buy Button", Toast.LENGTH_SHORT).show()
        }
        kart_btn.setOnClickListener {
            db = requireActivity().openOrCreateDatabase("Cart", Context.MODE_PRIVATE, null)
            db.execSQL("CREATE TABLE IF NOT EXISTS Cart(\n" +
                    "    cart_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    uid INTEGER,\n" +
                    "    pro_id INTEGER,\n" +
                    "    FOREIGN KEY (uid) REFERENCES User(userid),\n" +
                    "    FOREIGN KEY (pro_id) REFERENCES Product(pid)\n" +
                    ");")
            db.execSQL("Insert into Cart(uid,pro_id) values($userid,$pro_id)")
            Toast.makeText(requireContext(), "Added to Cart", Toast.LENGTH_SHORT).show()
        }


        addFavoriteClickListener(userid, fav_product)
    }

    private fun addFavoriteClickListener(uid: Int, favProduct: MutableList<Int>) {
        f_img.setOnClickListener {
            if (favProduct.contains(uid)) {
                favProduct.remove(uid)
                f_img.setImageResource(R.drawable.ic_action_favourite_fore)
                db1.execSQL("DELETE FROM Favourite WHERE p_id =$uid")

            } else {
                favProduct.add(uid)
                f_img.setImageResource(R.drawable.ic_action_favourite_red)
                db1.execSQL(("INSERT INTO Favourite (u_id,p_id) VALUES($uid,$uid)"))
            }
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
        fun newInstance(userid: Int, id: Int, name: String, img: Int, price: Int): Product_page {
            val fragment = Product_page()
            val args = Bundle()

            args.putInt("userid", userid)
            args.putInt("id", id)
            args.putString("name", name)
            args.putInt("img", img)
            args.putInt("price", price)
            fragment.arguments = args
            return fragment
        }
    }
}
