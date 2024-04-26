package com.example.barbershop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.barbershop.databinding.FragmentHome2Binding
import com.example.barbershop.models.barbershopdetails
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore


class home : Fragment(), barbershopadapter.OnItemClickListener  {

         private lateinit var binding:FragmentHome2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHome2Binding.inflate(layoutInflater)
        binding.homesearchview.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {

                findNavController().navigate(R.id.action_home2_to_search)
            }
            true // Consume the touch event
        }

        binding.categories.setOnClickListener {
            Toast.makeText(requireContext(), "Will Be Added Soon", Toast.LENGTH_SHORT).show()
        }
        val db=Firebase.firestore
        val list=ArrayList<barbershopdetails>()
        db.collection("barbershop").get().addOnSuccessListener {   querysnapshot->
            for (document in querysnapshot.documents){
                var name=document.getString("name")
                var services=document.getString("services")
                var coverimg=document.getString("coverimg")
                var uid=document.getString("uid")
                list.add(barbershopdetails(uid!!,name!!,services!!,"dd",coverimg!!,"dd","dd"))



            }
            binding.barbershoprecylerview.adapter=barbershopadapter(requireContext(),list,this)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show()
        }
        if(!Firebase.auth.currentUser!!.uid.isEmpty()){
            db.collection("users").document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var downurl = it.getString("img")
                if (downurl != null && downurl.isNotEmpty()) {
                    Glide.with(requireContext()).load(downurl).into(binding.homeuserprofile)
                } else {
                    // Handle the case where "img" is null or empty
                    // For example, you can load a default image or display a placeholder
                    // Glide.with(requireContext()).load(R.drawable.default_image).into(binding.profileImage)
                }
                var username=it.getString("username")
                binding.user.text=username
            }.addOnFailureListener {

            }
        }
        return binding.root
    }

    override fun onItemClick(uid: String) {
        // Handle item click here
        // Navigate to another fragment with the UID as a parameter
        val action =homeDirections.actionHome2ToBarberplace2(uid)
        findNavController().navigate(action)
    }


}