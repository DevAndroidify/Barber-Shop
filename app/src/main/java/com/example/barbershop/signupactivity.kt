package com.example.barbershop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.barbershop.databinding.ActivitySignupactivityBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class signupactivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var username:String
    private lateinit var binding:ActivitySignupactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }

    private fun updateUI(user: FirebaseUser?) {
       val db=Firebase.firestore;
        val data= hashMapOf(
            "username" to username,
            "email" to user!!.email,
            "uid"  to user!!.uid
        )
        db.collection("users").add(data).addOnSuccessListener {
            Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Sign up Failed", Toast.LENGTH_SHORT).show()
        }

    }
}