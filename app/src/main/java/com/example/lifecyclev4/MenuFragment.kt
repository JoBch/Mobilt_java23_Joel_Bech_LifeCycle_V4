package com.example.lifecyclev4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class MenuFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        val homeButton: Button = view.findViewById(R.id.menuHomeBtn)
        val welcomeButton: Button = view.findViewById(R.id.menuWelcomeButton)
        val credentialsButton: Button = view.findViewById(R.id.menuCredentialsButton)
        val logoutButton: Button = view.findViewById(R.id.menuLogoutBtn)
        val loginButton: Button = view.findViewById(R.id.menuLoginBtn)

        homeButton.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        welcomeButton.setOnClickListener {
            val intent = Intent(activity, WelcomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        credentialsButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, UpdateCredentialsFragment())
                .commit()
        }

        loginButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }

        logoutButton.setOnClickListener {
            val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }

            val intent = Intent(activity, WelcomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return view
    }
}
