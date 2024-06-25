package com.example.clothier

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class login : AppCompatActivity() {
    lateinit var db: SQLiteDatabase
    lateinit var loginmail: TextInputEditText
    lateinit var loginpasswd: TextInputEditText
    lateinit var email_Lay_login: TextInputLayout
    lateinit var pass_Lay_login: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)

        db = openOrCreateDatabase("User", MODE_PRIVATE, null)
        db.execSQL(("Create table if not exists User(userid integer primary key autoincrement ,uname varchar,email varchar, password varchar, address varchar, phone varchar);"))

        loginmail = findViewById(R.id.login_email)
        loginpasswd = findViewById(R.id.login_pass)
        email_Lay_login = findViewById(R.id.login_layout)
        pass_Lay_login = findViewById(R.id.passwd_layout)
    }

    fun forget_passwd(view: View) {
        Toast.makeText(this, "Forget Password", Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        email_Lay_login.error = null

        if (email.isEmpty()) {
            email_Lay_login.error = "Email is required."
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_Lay_login.error = "Please enter a valid email address."
            return false
        }

        return true
    }

    private fun isValidPassword(password: String): Boolean {
        pass_Lay_login.error = null

        if (password.isEmpty()) {
            pass_Lay_login.error = "Password is required."
            return false
        }

        return true
    }
    fun login(view: View) {

        //Make changes after testing
        val emailText = loginmail.text.toString()
        val passwordText =loginpasswd.text.toString()

        when {
            !isValidEmail(emailText) -> {}
            !isValidPassword(passwordText) -> {}
            else -> {

                val c: Cursor = db.rawQuery("select count(*) from User where email='$emailText' and password='$passwordText'", null)
                c.moveToFirst()
                val count = c.getInt(0)
                if (count > 0) {
                    val c1: Cursor = db.rawQuery("select * from User where email='$emailText' and password='$passwordText'", null)
                    c1.moveToFirst()
                    val userId = c1.getInt(0)
                    val userNm = c1.getString(1)
                    Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainNavigation::class.java)
                    intent.putExtra("userid",userId)
                    intent.putExtra("usernm",userNm)
                    intent.putExtra("email",passwordText)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun facebook_login(view: View) {
        Toast.makeText(this, "Facebook", Toast.LENGTH_SHORT).show()
    }

    fun google_login(view: View) {
        Toast.makeText(this, "Google", Toast.LENGTH_SHORT).show()
    }

    fun apple_login(view: View) {
        Toast.makeText(this, "Apple", Toast.LENGTH_SHORT).show()
    }

    fun turn_signup(view: View) {
        val intent = Intent(this, Sign_up::class.java)
        startActivity(intent)
    }


}
