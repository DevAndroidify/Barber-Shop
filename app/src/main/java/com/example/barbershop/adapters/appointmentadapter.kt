package com.example.barbershop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barbershop.models.booked

class appointmentadapter(val context: Context, val productList: ArrayList<booked>) : RecyclerView.Adapter<appointmentadapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coverimg = itemView.findViewById<ImageView>(R.id.barbershopimage)
        val name = itemView.findViewById<TextView>(R.id.barbershopname)
        val date=itemView.findViewById<TextView>(R.id.selecteddate)
        val time=itemView.findViewById<TextView>(R.id.selectedtime)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.appointmentlayout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentitem = productList[position]
        holder.name.text = currentitem.barbername
        holder.time.text=currentitem.selectedtime
        holder.date.text=currentitem.selectedDate

        Glide.with(context).load(currentitem.barbercoverimg).into(holder.coverimg)    }





    override fun getItemCount(): Int {
        return productList.size
    }




}
