package com.example.barbershop.adapters


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.barbershop.R
import com.example.barbershop.models.onboardingmodel


import java.net.URI

class onboardingadapter( val context: Context,val productList: ArrayList<onboardingmodel>) : RecyclerView.Adapter<onboardingadapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.onboardingrecylerlayout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val currentItem = productList[position]
        holder.maintext.text=currentItem.maintext
        holder.secondtext.text=currentItem.secondtext
        holder.img.setImageResource(currentItem.img)

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var maintext=itemView.findViewById<TextView>(R.id.textView)
        var secondtext=itemView.findViewById<TextView>(R.id.textView2)
        var img=itemView.findViewById<ImageView>(R.id.imageView2)


    }
}