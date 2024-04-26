package com.example.barbershop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.barbershop.adapters.onboardingadapter
import com.example.barbershop.databinding.ActivityOnboardingactivityBinding
import com.example.barbershop.models.onboardingmodel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class onboardingactivity : AppCompatActivity() {
    private lateinit var binding:ActivityOnboardingactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOnboardingactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signin.setOnClickListener {
              val a=Intent(this,MainActivity::class.java)
            startActivity(a)
        }

        var list=ArrayList<onboardingmodel>()
        list.add(onboardingmodel("The platform for","barbers",R.drawable.image1))
        list.add(onboardingmodel("let you book in","seconds",R.drawable.image2))
        list.add(onboardingmodel("Simple and Easy","user interface",R.drawable.image4))
        val wormDotsIndicator = findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)


        binding.onboardingviewpager.adapter=onboardingadapter(this,list)
        wormDotsIndicator.attachTo(binding.onboardingviewpager)



    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}