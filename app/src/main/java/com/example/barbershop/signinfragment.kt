package com.example.barbershop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.barbershop.databinding.FragmentSigninfragmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class signinfragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var selectedName:String
    private lateinit var username:String
   private lateinit var binding:FragmentSigninfragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding=FragmentSigninfragmentBinding.inflate(layoutInflater)
         selectedName = arguments?.getString("selected")!!
        binding.welcome.text="Welcome "+selectedName;
        auth = Firebase.auth
        binding.elevatedButton.setOnClickListener {

            var email = binding.signinemail.editText!!.text.toString();
            username = binding.signinusername.editText!!.text.toString();
            var password = binding.signinpassword.editText!!.text.toString();
            if (email.isEmpty() && username.isEmpty() && password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Your Details", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {

                            val user = auth.currentUser
                            updateUI(user)
                        } else {

                            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }



        // Inflate the layout for this fragment
        return binding.root
    }
    private fun updateUI(user: FirebaseUser?) {
        val db=Firebase.firestore;
        val data= hashMapOf(
            "typeuser" to selectedName,
            "username" to username,
            "email" to user!!.email,
            "uid"  to user!!.uid
        )
        db.collection("users").document(auth.currentUser!!.uid).set(data).addOnSuccessListener {
            if(selectedName=="Barber"){
                findNavController().navigate(R.id.action_signinfragment_to_addshop)

            }else if(selectedName=="Customer"){
                findNavController().navigate(R.id.action_signinfragment_to_homeFragment)
            }else{
                Toast.makeText(requireContext(), "Server Issue", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Sign up Failed", Toast.LENGTH_SHORT).show()
        }

    }


}