package com.example.barbershop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.barbershop.models.barbershopdetails

class searchadapter(val context: Context, val productList: ArrayList<barbershopdetails>, val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<searchadapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.barberlayou, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentitem = productList[position]
        holder.name.text = currentitem.name
        holder.services.text = currentitem.services
        Glide.with(context).load(currentitem.coverimg).into(holder.coverimg)

        // Set click listener
        holder.itemView.setOnClickListener {
            // Pass UID to click listener
            itemClickListener.onItemClick(currentitem.uid)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coverimg = itemView.findViewById<ImageView>(R.id.barbershopimage)
        val name = itemView.findViewById<TextView>(R.id.barbershopname)
        val services = itemView.findViewById<TextView>(R.id.selecteddate)
    }

    // Define interface for item click listener
    interface OnItemClickListener {
        fun onItemClick(uid: String)
    }
}
