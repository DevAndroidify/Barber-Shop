package com.example.barbershop

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.barbershop.adapters.barberuseradapter
import com.example.barbershop.databinding.FragmentAddshopBinding
import com.example.barbershop.models.booked
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage


class addshop : Fragment() {
    private var PICK_IMAGE_REQUEST=1
    private lateinit var imageUri:Uri
    private lateinit var latitude:String
    private lateinit var longitude:String
     var list=ArrayList<booked>()

    private lateinit var downloadUrl:String
    private val REQUEST_LOCATION_PERMISSION = 1001
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var binding:FragmentAddshopBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddshopBinding.inflate(layoutInflater)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        binding.getimage.setOnClickListener {
            opengallery();
        }
        binding.getlocation.setOnClickListener {
            getLastLocation();

        }
        val userlist=ArrayList<String>()
        val userdate=ArrayList<String>()
        val usertime=ArrayList<String>()
        val db=Firebase.firestore
        db.collection("booked").whereEqualTo("barberuid",Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
            for(document in it.documents){
                var uid=document.getString("useruid")
                var date=document.getString("date")
                var time=document.getString("time")
                userlist.add(uid!!)
                userdate.add(date!!)
                usertime.add(time!!)

            }
            var index=0;
            for(i in userlist){
                db.collection("users").whereEqualTo("uid",i).get().addOnSuccessListener {
                    var username=it.documents[0].getString("username")
                    list.add(booked(userdate[index],usertime[index],"aa","aa","aa",username!!))
                    index++
                    binding.barberrecylerview.adapter=barberuseradapter(requireContext(),list)

                }.addOnFailureListener {

                }
            }

        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Unable to retrieve", Toast.LENGTH_SHORT).show()
        }



        binding.barbersubmit.setOnClickListener {
            val db=Firebase.firestore
            val data= hashMapOf(
                "name" to binding.editTextText.text.toString(),
                "services" to binding.editTextText2.text.toString(),
                "capacity" to binding.editTextText3.text.toString(),
                "latitude" to latitude,
                "longitude" to longitude,
                "coverimg" to downloadUrl,
                "uid" to Firebase.auth.currentUser!!.uid
            )
            db.collection("barbershop").document(Firebase.auth.currentUser!!.uid).set(data).addOnSuccessListener {
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
            }
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
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to upload", Toast.LENGTH_SHORT).show()
             }

        }
    }

    private fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // Use the location here as needed
                     latitude = location.latitude.toString()
                     longitude = location.longitude.toString()

                } else {
                    Toast.makeText(requireContext(), "Location not available", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(requireContext(), "Failed to get location: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}