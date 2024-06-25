package com.example.clothier

import android.content.Context.MODE_PRIVATE
import android.database.sqlite.SQLiteDatabase
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast




class Order_Page_details : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var db: SQLiteDatabase
    lateinit var db1: SQLiteDatabase
    lateinit var mode: Spinner
    lateinit var subTotal: TextView
    lateinit var allTotal: TextView
    var total = 0


    private var userid: Int = 0
    private var pro_id: Int = 0
    private lateinit var pro_name: String
    private var pro_price: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            this.userid = arguments?.getInt("userid") ?: 0
            this.pro_id = arguments?.getInt("pro_id") ?: 0
            pro_name = arguments?.getString("pro_name") ?: ""
            pro_price = arguments?.getInt("pro_price") ?: 0

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_page_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        subTotal = view.findViewById(R.id.total_text)
        allTotal = view.findViewById(R.id.alltotal)

        db = requireActivity().openOrCreateDatabase("Order", MODE_PRIVATE, null)
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS Orders(\n" +
                    "orderid INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "uid INTEGER,\n" +
                    "pro_id INTEGER,\n" +
                    "amount INTEGER,\n" +
                    "status VARCHAR,\n" +
                    "FOREIGN KEY (uid) REFERENCES User(userid),\n" +
                    "FOREIGN KEY (pro_id) REFERENCES Product(pid)\n" +
                    ");"
        )

        subTotal.text = pro_price.toString() + "/-"
        total = pro_price + 50
        allTotal.text = total.toString() + "/-"

        val tableLayout: TableLayout = view.findViewById(R.id.itemlist)

        val tableRow = TableRow(requireContext())
        val layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )
        tableRow.layoutParams = layoutParams

        val productNameTextView = TextView(requireContext())
        productNameTextView.text = pro_name
        productNameTextView.gravity = Gravity.CENTER
        productNameTextView.textSize = 15f
        productNameTextView.setTypeface(null, Typeface.BOLD)
        tableRow.addView(productNameTextView)

        val quantityTextView = TextView(requireContext())
        quantityTextView.text = "1" // Set default quantity to 1
        quantityTextView.gravity = Gravity.CENTER
        quantityTextView.textSize = 15f
        quantityTextView.setTypeface(null, Typeface.BOLD)
        tableRow.addView(quantityTextView)

        val totalPriceTextView = TextView(requireContext())
        totalPriceTextView.text = pro_price.toString()
        totalPriceTextView.gravity = Gravity.CENTER
        totalPriceTextView.textSize = 15f
        totalPriceTextView.setTypeface(null, Typeface.BOLD)
        tableRow.addView(totalPriceTextView)

        tableLayout.addView(tableRow)

        var place_order_btn:Button=view.findViewById(R.id.place_or)
        place_order_btn.setOnClickListener(){
            db.execSQL("Insert into Orders(uid,pro_id,amount,status) values(${this.userid},${this.pro_id},$total,'Ordered')")
            db1 = requireActivity().openOrCreateDatabase("Product", MODE_PRIVATE, null)
            db1.execSQL("UPDATE Product SET stock = stock - 1 WHERE pid = ${this.pro_id}")

//            //To play animation
//            val animPaymentFragment = Anim_payment()
//
            navigateToNewFragment(Anim_payment())
            Toast.makeText(requireContext(), "Order Placed", Toast.LENGTH_SHORT).show()

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
        fun newInstance(uid: Int, pid: Int, pname: String, pprice: Int): Order_Page_details {
            val fragment = Order_Page_details()
            val args = Bundle()
            args.putInt("userid", uid)
            args.putInt("pro_id", pid)
            args.putString("pro_name", pname)
            args.putInt("pro_price", pprice)
            fragment.arguments = args
            return fragment
        }
    }


}