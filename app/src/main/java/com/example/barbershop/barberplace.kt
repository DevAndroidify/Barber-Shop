package com.example.barbershop

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.barbershop.databinding.FragmentBarberplaceBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.util.Calendar

class barberplace : Fragment() {
    private lateinit var selectedtime:String
   private lateinit var latitude:String
   private lateinit var selectedDate:String
   private lateinit var barbername:String
   private lateinit var barberuid:String
   private lateinit var longitude:String
   private lateinit var barberimage:String
   private lateinit var binding:FragmentBarberplaceBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentBarberplaceBinding.inflate(layoutInflater)
        binding.popbackicon.setOnClickListener {
            findNavController().popBackStack()
        }
        var uid = arguments?.getString("uid")
        val db = Firebase.firestore
        db.collection("barbershop").document(uid!!).get().addOnSuccessListener {
            barbername=it.getString("name")!!
            binding.barbername.text = it.getString("name")
            binding.barberservices.text = it.getString("services")
            binding.queue.text = "0/" + it.getString("capacity")
            latitude=it.getString("latitude")!!
            longitude=it.getString("longitude")!!
            barberimage=it.getString("coverimg")!!
            barberuid=it.getString("uid")!!

            Glide.with(requireContext()).load(it.getString("coverimg")).into(binding.barberimage)
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "Failed to retrieve data", Toast.LENGTH_SHORT).show()
        }
        binding.location.setOnClickListener {
            val url = "https://www.google.com/maps?q=$latitude,$longitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
        var timelist=ArrayList<String>()

        binding.selectadate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                    db.collection("booked")
                        .whereEqualTo("date", selectedDate)
                        .whereEqualTo("barberuid", barberuid)
                        .get()
                        .addOnSuccessListener { querySnapshot ->
                            if (!querySnapshot.isEmpty) {
                                for (document in querySnapshot.documents) {
                                    val timedata = document.getString("time")
                                    timelist.add(timedata!!)
                                    Toast.makeText(requireContext(), timedata, Toast.LENGTH_SHORT).show()
                                }

                                for (time in timelist) {
                                    when (time) {
                                        "10:AM" -> binding.tenam.visibility = View.INVISIBLE
                                        "11:AM" -> binding.elevanam.visibility = View.INVISIBLE
                                        "12:PM" -> binding.twelvepm.visibility = View.INVISIBLE
                                        "01:PM" -> binding.onepm.visibility = View.INVISIBLE
                                        "02:PM" -> binding.twopm.visibility = View.INVISIBLE
                                        "03:PM" -> binding.threepm.visibility = View.INVISIBLE
                                        "04:PM" -> binding.fourpm.visibility = View.INVISIBLE
                                        "05:PM" -> binding.fivepm.visibility = View.INVISIBLE
                                        // Add cases for other RadioButtons if needed
                                    }
                                }
                                timelist.clear()
                            } else {
                                // Only make RadioButtons visible if timelist is empty
                                if (timelist.isEmpty()) {
                                    binding.tenam.visibility = View.VISIBLE
                                    binding.elevanam.visibility = View.VISIBLE
                                    binding.twelvepm.visibility = View.VISIBLE
                                    binding.onepm.visibility = View.VISIBLE
                                    binding.twopm.visibility = View.VISIBLE
                                    binding.threepm.visibility = View.VISIBLE
                                    binding.fourpm.visibility = View.VISIBLE
                                    binding.fivepm.visibility = View.VISIBLE
                                }
                            }
                        }
                        .addOnFailureListener {
                            // If there's a failure, make all RadioButtons visible
                            binding.tenam.visibility = View.VISIBLE
                            binding.elevanam.visibility = View.VISIBLE
                            binding.twelvepm.visibility = View.VISIBLE
                            binding.onepm.visibility = View.VISIBLE
                            binding.twopm.visibility = View.VISIBLE
                            binding.threepm.visibility = View.VISIBLE
                            binding.fourpm.visibility = View.VISIBLE
                            binding.fivepm.visibility = View.VISIBLE
                        }
                },
                year,
                month,
                dayOfMonth
            )

            datePickerDialog.show()


            datePickerDialog.show()



        }
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton? = group.findViewById(checkedId)
            radioButton?.let {

                selectedtime = it.text.toString()
            }



        }


        binding.bookanappointment.setOnClickListener {


            val data = hashMapOf(
                "date" to selectedDate,
                "time" to selectedtime,
                "useruid" to Firebase.auth.currentUser!!.uid,
                "barberuid" to barberuid,
                "barberimage" to barberimage,
                "barbername" to barbername
            )
            db.collection("booked").add(data)
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Booked Successfully", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                Toast.makeText(requireContext(), "Server Error Try again later", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }



    }