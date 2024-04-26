package com.example.barbershop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.airbnb.lottie.LottieAnimationView

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val animationView: LottieAnimationView = findViewById(R.id.animationView)
        animationView.playAnimation()

        // Delay transition to the next screen after animation duration
        Handler().postDelayed({
            // Proceed to the next screen or perform any other action
            // For example, start your main activity
            startActivity(Intent(this, onboardingactivity::class.java))
            finish()
        }, 6000)
    }
}