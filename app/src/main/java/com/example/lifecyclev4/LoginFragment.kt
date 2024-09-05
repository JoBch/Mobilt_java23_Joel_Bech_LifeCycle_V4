package com.example.lifecyclev4

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()

    var inputEmail:String = ""
    var inputPassword:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val sharedPreferences = activity?.getSharedPreferences("user_prefs", MODE_PRIVATE)
        val emailSharedpref: String = sharedPreferences?.getString("user_email", null).toString()
        val passwordSharedpref: String = sharedPreferences?.getString("user_password", null).toString()

        val emailEditText: EditText = view.findViewById(R.id.loginEmailAddress)
        val passwordEditText: EditText = view.findViewById(R.id.loginPassword)
        val submitButton: Button = view.findViewById(R.id.submitLoginBtn)
        val menuBtn: Button = view.findViewById(R.id.loginMenuBtn)

        menuBtn.setOnClickListener(){
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.menu_container, MenuFragment())
                .commit()
        }

        //Autofills the login fields from sharedpref if the user doesnt logout and therefore nulls the sharedpref
        emailEditText.setText(emailSharedpref)
        passwordEditText.setText(passwordSharedpref)

        submitButton.setOnClickListener {
            //Retrieve email and password from the EditTexts
            inputEmail = emailEditText.text.toString()
            inputPassword = passwordEditText.text.toString()

            if (inputEmail.isEmpty() || inputPassword.isEmpty())
                showToast("Input required")
            else performLogin(inputEmail, inputPassword)
        }

        return view
    }

    private fun performLogin(email: String, password: String) {
        //Get the user document from Firestore using the email as the document ID
        db.collection("users").document(email).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val storedPassword = document.getString("password")

                    if (storedPassword == password) {
                        //Password matches, login successful, navigate to desired activity or fragment and set email in sharedpref
                        val intent = Intent(activity, HomeActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                        val sharedPreferences = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.putString("user_email", inputEmail)
                        editor?.putString("user_password", inputPassword)
                        editor?.apply()

                    } else showToast("Invalid password")

                } else {
                    showToast("User not found")
                }
            }
            .addOnFailureListener { e ->
                Log.w("LoginFragment", "Error getting document", e)
                showToast("Error occurred. Please try again.")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}
