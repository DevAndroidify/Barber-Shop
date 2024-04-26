package com.example.barbershop.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barbershop.R
import com.example.barbershop.models.booked

class barberuseradapter(val context: Context, val productList: ArrayList<booked>) : RecyclerView.Adapter<barberuseradapter.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val username=itemView.findViewById<TextView>(R.id.bookusername)
        val date=itemView.findViewById<TextView>(R.id.bookuserdate)
        val time=itemView.findViewById<TextView>(R.id.bookusertime)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.barberuserlayout, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentitem = productList[position]
        holder.username.text=currentitem.barbername
        holder.time.text=currentitem.selectedtime
        holder.date.text=currentitem.selectedDate

            }





    override fun getItemCount(): Int {
        return productList.size
    }




}
