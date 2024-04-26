package com.example.barbershop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.barbershop.databinding.FragmentSearchBinding
import com.example.barbershop.models.barbershopdetails
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.util.Locale

class search : Fragment(),searchadapter.OnItemClickListener {
    private var list:ArrayList<barbershopdetails> = ArrayList()

    private lateinit var binding:FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater)

        binding.searchbackbutton.setOnClickListener {
            findNavController().popBackStack()
        }
        val db= Firebase.firestore
        db.collection("barbershop").get().addOnSuccessListener {   querysnapshot->
            for (document in querysnapshot.documents){
                var name=document.getString("name")
                var services=document.getString("services")
                var coverimg=document.getString("coverimg")
                var uid=document.getString("uid")
                list.add(barbershopdetails(uid!!,name!!,services!!,"dd",coverimg!!,"dd","dd"))



            }
            binding.searchrecylerview.adapter=searchadapter(requireContext(),list,this)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show()
        }
        binding.searchproduct.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
        return binding.root
    }
    override fun onItemClick(uid: String) {
        // Handle item click here
        // Navigate to another fragment with the UID as a parameter
        val action =homeDirections.actionHome2ToBarberplace2(uid)
        findNavController().navigate(action)
    }
    private fun filterList(query: String?) {

        if (query != null) {
            val filteredList = ArrayList<barbershopdetails>()
            for (i in list) {
                if (i.name!!.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                binding.searchrecylerview.adapter=searchadapter(requireContext(), filteredList,this)


            }
        }
    }

}