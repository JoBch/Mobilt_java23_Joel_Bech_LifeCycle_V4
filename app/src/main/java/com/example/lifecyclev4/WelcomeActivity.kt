package com.example.lifecyclev4

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)

        val loginButton: Button = findViewById(R.id.loginBtn)
        val registerButton: Button = findViewById(R.id.registerBtn)
        val textView: TextView = findViewById(R.id.welcomeTextView)

        val sharedPreferences = this.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val emailSharedpref: String = sharedPreferences?.getString("user_email", null).toString()

        textView.append("\n" + emailSharedpref)

        loginButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.welcome, LoginFragment())
                .commit()
        }

        registerButton.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.welcome, RegisterFragment())
                .commit()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.welcome)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}