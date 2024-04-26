package com.example.barbershop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.barbershop.databinding.FragmentLoginfragmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class loginfragment : Fragment() {
    private lateinit var auth: FirebaseAuth
   private lateinit var binding:FragmentLoginfragmentBinding
    private lateinit var selectedname:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentLoginfragmentBinding.inflate(layoutInflater)
        auth = Firebase.auth
        selectedname=arguments?.getString("selected")!!
        binding.elevatedButton.setOnClickListener {
            var email = binding.signinemail.editText!!.text.toString()
            var password = binding.signinpassword.editText!!.text.toString()
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter Your Detail", Toast.LENGTH_SHORT).show()
            } else {


                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            if (selectedname == "Customer") {
                                findNavController().navigate(R.id.action_loginfragment_to_homeFragment)

                            } else if (selectedname == "Barber") {
                                findNavController().navigate(R.id.action_loginfragment_to_addshop)
                            } else {
                                Toast.makeText(requireContext(), "Server Issue", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
        return binding.root

    }


}