package com.example.clothier

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlin.properties.Delegates


class Profile : Fragment() {


    private var userid by Delegates.notNull<Int>()
    var usernm :String ="Manoj Maurya"
    var email :String ="manoj123@gmail.com"
    lateinit var userText:TextView
    lateinit var emailText:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        userid = arguments?.getInt("userid", 0) ?: 0
//        usernm = arguments?.getString("usernm").toString()
//        email = arguments?.getString("email").toString()
//        Toast.makeText(requireContext(),"UserId is "+usernm,Toast.LENGTH_SHORT).show()



        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        userText=view.findViewById(R.id.usernm)
        userText.text=usernm
        emailText=view.findViewById(R.id.email)
        emailText.text=email


        view.findViewById<View>(R.id.button4)?.setOnClickListener {
            showToast("Edit Profile")
        }

        view.findViewById<View>(R.id.button5)?.setOnClickListener {
            showToast("My Orders")
        }

        view.findViewById<View>(R.id.button6)?.setOnClickListener {
            showToast("Saved Address")
        }

        view.findViewById<View>(R.id.button7)?.setOnClickListener {
            showToast("Rate Us")
        }

        view.findViewById<View>(R.id.button8)?.setOnClickListener {
            showToast("FAQs")
        }

        view.findViewById<View>(R.id.button9)?.setOnClickListener {
            showToast("Wallets")
        }

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }



    companion object {
        fun newInstance(userid: Int?, usernm: String?,email:String?): Home {
            val fragment = Home()
            val args = Bundle()
            args.putInt("userid", userid ?: -1)
            args.putString("usernm", usernm)
            args.putString("email", email)
            fragment.arguments = args
            return fragment
        }
    }
}