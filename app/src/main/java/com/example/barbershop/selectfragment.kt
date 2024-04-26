package com.example.barbershop

import android.content.Intent
import android.os.Bundle
import android.text.Layout.Directions
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.barbershop.databinding.FragmentSelectfragmentBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class selectfragment : Fragment() {
  private lateinit var binding:FragmentSelectfragmentBinding
  private lateinit var selected:String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSelectfragmentBinding.inflate(layoutInflater)
        selected=""
        binding.radioGroup.setOnCheckedChangeListener { group, i ->
            var radioButton:RadioButton=requireView().findViewById(i)
            selected=radioButton.text.toString()
        }
         binding.proceed.setOnClickListener {
             if(selected.isEmpty()){
                 Toast.makeText(requireContext(), "Choose Your Role", Toast.LENGTH_SHORT).show()
             }else {

                 val action = selectfragmentDirections.actionSelectfragmentToSigninfragment(selected)
                 findNavController().navigate(action)
             }
             }
        binding.already.setOnClickListener {
            if(selected.isEmpty()){
                Toast.makeText(requireContext(), "Choose Your Role", Toast.LENGTH_SHORT).show()
            }else {


                val action = selectfragmentDirections.actionSelectfragmentToLoginfragment(selected)
                findNavController().navigate(action)
            }
            }
        // Inflate the layout for this fragment
        return binding.root
    }
    public override fun onStart() {
        super.onStart()
        val currentUser = Firebase.auth.currentUser
        if (currentUser != null) {
           findNavController().navigate(R.id.action_selectfragment_to_homeFragment2)
        }
    }


}