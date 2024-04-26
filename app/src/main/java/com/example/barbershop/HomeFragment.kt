package com.example.barbershop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.barbershop.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var selecteduser:String
   private lateinit var binding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentHomeBinding.inflate(layoutInflater)
        val navController = childFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()
        navController?.let { nav ->
            binding.mainbottomview.setupWithNavController(nav)
        }

        return binding.root
    }


}