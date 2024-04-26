package com.example.barbershop

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.barbershop.databinding.FragmentMoreBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage


class More : Fragment() {
    private var PICK_IMAGE_REQUEST=1
    private lateinit var imageUri: Uri
    private lateinit var downloadUrl:String
    private lateinit var binding:FragmentMoreBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMoreBinding.inflate(layoutInflater)
        val db=Firebase.firestore
        if(!Firebase.auth.currentUser!!.uid.isEmpty()) {


            db.collection("users").whereEqualTo("uid", Firebase.auth.currentUser!!.uid).get()
                .addOnSuccessListener {
                    binding.moreusername.text = it.documents[0].getString("username")
                    binding.moreuseremail.text = it.documents[0].getString("email")
                    var downurl = it.documents[0].getString("img")
                    if (downurl != null && downurl.isNotEmpty()) {
                        Glide.with(requireContext()).load(downurl).into(binding.profileImage)
                    } else {
                        // Handle the case where "img" is null or empty
                        // For example, you can load a default image or display a placeholder
                        // Glide.with(requireContext()).load(R.drawable.default_image).into(binding.profileImage)
                    }

                }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        binding.morelogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            findNavController().navigate(R.id.action_more2_to_onboardingactivity)
        }
        binding.moresettings.setOnClickListener {
            Toast.makeText(requireContext(), "Will be Added Soon", Toast.LENGTH_SHORT).show()
        }
        binding.updateuserprofileimage.setOnClickListener {
            opengallery()
        }
        return binding.root
    }
    private fun opengallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
            val storageRef = Firebase.storage.reference

            val imageRef = storageRef.child("images/${System.currentTimeMillis()}.jpg")

            val uploadTask = imageRef.putFile(imageUri)

            uploadTask.addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    downloadUrl = uri.toString()
                    val db=Firebase.firestore

                    db.collection("users").document(Firebase.auth.currentUser!!.uid).update("img",downloadUrl).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                      Glide.with(requireContext()).load(downloadUrl).into(binding.profileImage)
                    }.addOnFailureListener {
                        Toast.makeText(requireContext(), "Failed to Update", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to upload", Toast.LENGTH_SHORT).show()
            }


        }
    }

}