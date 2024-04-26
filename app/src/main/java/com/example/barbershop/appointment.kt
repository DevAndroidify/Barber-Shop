package com.example.barbershop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.barbershop.databinding.FragmentAppointmentBinding
import com.example.barbershop.models.booked
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class appointment : Fragment() {

   private lateinit var binding:FragmentAppointmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAppointmentBinding.inflate(layoutInflater)
        val db=Firebase.firestore
        val list=ArrayList<booked>()
        val userid=Firebase.auth.currentUser!!.uid
        db.collection("booked").whereEqualTo("useruid",userid).get().addOnSuccessListener {
              for(document in it.documents) {
                  var selecteddate = document.getString("date")
                  var barbername = document.getString("barbername")
                  var barberimage = document.getString("barberimage")
                  var selectedtime=document.getString("time")
                  list.add(booked(selecteddate!!, selectedtime!!, "aa", barberimage!!, "aa", barbername!!))
              }


                binding.appointmentrecylerview.adapter = appointmentadapter(requireContext(), list)
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "No Appointments", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }


}