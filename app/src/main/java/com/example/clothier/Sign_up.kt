package com.example.clothier

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class Sign_up : AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    lateinit var name: TextInputEditText
    lateinit var mail: TextInputEditText
    lateinit var passwd: TextInputEditText
    lateinit var name_Lay: TextInputLayout
    lateinit var email_Lay: TextInputLayout
    lateinit var pass_Lay: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        db = openOrCreateDatabase("User", Context.MODE_PRIVATE, null)
        db.execSQL(("Create table if not exists User(userid integer primary key autoincrement ,uname varchar,email varchar, password varchar, address varchar, phone varchar);"))

        name = findViewById(R.id.signup_nm)
        mail = findViewById(R.id.signup_email)
        passwd = findViewById(R.id.signup_pass)
        name_Lay= findViewById(R.id.sign_nm_lay)
        email_Lay= findViewById(R.id.sign_mail_lay)
        pass_Lay= findViewById(R.id.sign_pass_lay)
    }

    fun isValidEmail(email: String): Boolean {
        email_Lay.error = null

        if (email.isEmpty()) {
            email_Lay.error = "Email is required."
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_Lay.error = "Please enter a valid email address."
            return false
        }

        return true
    }

    fun isValidName(name: String): Boolean {
        name_Lay.error = null

        if (name.isEmpty()) {
            name_Lay.error = "Name is required."
            return false
        }

        val specialChars = Regex("[!@#\$%^&*(),.?\":{}|<>]")
        if (specialChars.containsMatchIn(name)) {
            name_Lay.error = "Name should not contain special characters."
            return false
        }

        val containsNumber = Regex("[0-9]")
        if (containsNumber.containsMatchIn(name)) {
            name_Lay.error = "Name should not contain numbers."
            return false
        }

        return true
    }

    fun isValidPassword(password: String): Boolean {
        pass_Lay.error = null

        if (password.isEmpty()) {
            pass_Lay.error = "Password is required."
            return false
        }
        if (password.length < 6) {
            pass_Lay.error = "Password must be at least 6 characters long."
            return false
        }
        val containsAlphabet = Regex("[a-zA-Z]")
        if (!containsAlphabet.containsMatchIn(password)) {
            pass_Lay.error = "Password must contain at least one alphabet."
            return false
        }
        val containsNumber = Regex("[0-9]")
        if (!containsNumber.containsMatchIn(password)) {
            pass_Lay.error = "Password must contain at least one number."
            return false
        }
        return true
    }

    fun turn_login(view: View) {
        val intent = Intent(this, login::class.java)
        startActivity(intent)
    }

    fun signup(view: View) {
        val nameText = name.text.toString()
        val emailText = mail.text.toString()
        val passwordText = passwd.text.toString()

        when {
            !isValidName(nameText) -> {}
            !isValidEmail(emailText) -> {}
            !isValidPassword(passwordText) -> {}
            else -> {
                val c: Cursor = db.rawQuery("Select count(*) from user where email='$emailText'", null)
                c.moveToFirst()
                val record = c.getInt(0)
                if (record > 0) {
                    Toast.makeText(this, "User already present", Toast.LENGTH_SHORT).show()
                } else {
                    db.execSQL(("Insert into User(uname,email,password) values('$nameText','$emailText','$passwordText');"))
                    Toast.makeText(this, "Signed Up Successfully", Toast.LENGTH_SHORT).show()
                    turn_login(view)
                }
            }
        }
    }
}
