package com.mateus.agenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LoginEmail: Fragment() {
    val ref= FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_email, container, false)
        val email=view.findViewById<TextInputEditText>(R.id.login_email)
        val password=view.findViewById<TextInputEditText>(R.id.login_password)
        val registerBtn=view.findViewById<Button>(R.id.register_button)
        val register = view.findViewById<TextView>(R.id.navigate_to_register)

        registerBtn.setOnClickListener {
            ref.signInWithEmailAndPassword(
                email.text.toString().trim(),
                password.text.toString().trim()
            ).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    findNavController().navigate(R.id.action_loginEmail_to_listFragment)
                }else{
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }

            }
        }
        register.setOnClickListener{
            findNavController().navigate(R.id.action_loginEmail_to_createUser)
        }
        return view
    }

}