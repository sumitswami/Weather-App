package com.example.weatherapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.model.CardDescription
import com.example.weatherapp.R

class Adapter(val descriptionList :List<CardDescription>) : RecyclerView.Adapter<Adapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //creates viewholder for us which stores views
        val inflater:LayoutInflater = LayoutInflater.from(parent.context)
        //layout inflater converts xml file into java object
        val view:View = inflater.inflate(R.layout.item_view,parent,false )
        //inflater in inflated and layout is passed which return us a view
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemDescription.text = descriptionList[position].title
        holder.itemData.text = descriptionList[position].data

    }

    override fun getItemCount(): Int {
        return descriptionList.size
    }

    //nested class because it cannot work without adapter
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemDescription = itemView.findViewById<TextView>(R.id.descriptionText)
        var itemData = itemView.findViewById<TextView>(R.id.itemData)
        //storing references of our text so that we dont have to do findviewbyid again and again

    }

}

