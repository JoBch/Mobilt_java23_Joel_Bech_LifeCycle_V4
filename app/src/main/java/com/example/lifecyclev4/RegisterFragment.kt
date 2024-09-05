package com.example.lifecyclev4

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance() // Ensure you're using FirebaseFirestore instance

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val emailEditText: EditText = view.findViewById(R.id.registerEmailAddress)
        val passwordEditText: EditText = view.findViewById(R.id.registerPassword)
        val submitButton: Button = view.findViewById(R.id.submitRegisterBtn)
        val menuBtn: Button = view.findViewById(R.id.registerMenuBtn)

        menuBtn.setOnClickListener(){
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.menu_container, MenuFragment())
                .commit()
        }

        submitButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (!isValidEmail(email)) {
                Toast.makeText(activity, "Invalid email", Toast.LENGTH_LONG).show()
            } else {
                val user = hashMapOf(
                    "password" to password
                )

                db.collection("users").document(email)
                    .set(user)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                // Navigate to desired activity or fragment
                val intent = Intent(activity, WelcomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        return view
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        return email.matches(emailPattern.toRegex())
    }
}
