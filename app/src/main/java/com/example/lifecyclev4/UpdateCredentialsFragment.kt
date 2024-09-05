package com.example.lifecyclev4

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class UpdateCredentialsFragment : Fragment() {

    val db = Firebase.firestore

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_update_credentials, container, false)
        val phoneEditText: EditText = view.findViewById(R.id.updatePhoneNumber)
        val passwordEditText: EditText = view.findViewById(R.id.updatePassword)
        val firstNameEditText: EditText = view.findViewById(R.id.updateFirstName)
        val lastnameEdittext: EditText = view.findViewById(R.id.updateLastName)
        val sexRadioGroup: RadioGroup = view.findViewById(R.id.sexRadioBtn)
        val submitButton: Button = view.findViewById(R.id.submitCredentialsBtn)
        val textView: TextView = view.findViewById(R.id.updateCredentialsTextView)
        val menuBtn: Button = view.findViewById(R.id.credentialsMenuBtn)

        val sharedPreferences = activity?.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val emailSharedpref: String = sharedPreferences?.getString("user_email", null).toString()

        textView.append("\n" + emailSharedpref)

        menuBtn.setOnClickListener(){
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.menu_container, MenuFragment())
                .commit()
        }

        submitButton.setOnClickListener {
            val selectedId: Int = sexRadioGroup.checkedRadioButtonId

            if (selectedId != -1) {  // Check if a RadioButton is selected
                val selectedRadioButton: RadioButton = view.findViewById(selectedId)

                //Updates the values in my Firestore associated with the email used to log in
                val user = hashMapOf(
                    "first" to firstNameEditText.text.toString(),
                    "last" to lastnameEdittext.text.toString(),
                    "phone" to phoneEditText.text.toString(),
                    "password" to passwordEditText.text.toString(),
                    "sex" to selectedRadioButton.text.toString()
                )

                //Updates userInfo
                db.collection("users").document(emailSharedpref)
                    .set(user)
                    .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                    .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }

                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }

        return view
    }
}
