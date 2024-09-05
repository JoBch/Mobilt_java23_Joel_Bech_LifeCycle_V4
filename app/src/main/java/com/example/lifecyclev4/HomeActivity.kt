package com.example.lifecyclev4

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        val sharedPreferences = this.getSharedPreferences("user_prefs", MODE_PRIVATE)
        val updateCredentialsBtn: Button = findViewById(R.id.updateCredentialsBtn)
        val textView: TextView = findViewById(R.id.homeTextView)
        val menuBtn: Button = findViewById(R.id.homeMenuBtn)

        val emailSharedpref: String = sharedPreferences?.getString("user_email", null).toString()

        textView.append("\n" + emailSharedpref)

        menuBtn.setOnClickListener(){
            supportFragmentManager.beginTransaction()
                .replace(R.id.menu_container, MenuFragment())
                .commit()
        }

        updateCredentialsBtn.setOnClickListener {
            //Navigate to desired activity or fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.home, UpdateCredentialsFragment())
                .commit()
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}