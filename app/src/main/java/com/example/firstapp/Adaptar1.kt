package com.example.firstapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adaptar1(private val itemlist : ArrayList<RetrivingLostData>) : RecyclerView.Adapter<Adaptar1.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = itemlist[position]

        //While adding image change here
        holder.item.text = currentitem.item
        holder.name.text = currentitem.name
        holder.phone.text = currentitem.phone
    }

    override fun getItemCount(): Int {

        return itemlist.size
//        val a:Int
//        if(itemlist != null && !itemlist.isEmpty() ){
//            a = itemlist.size
//        }
//        else{
//            a = 0
//        }
//        return a
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        //While adding image change here
        val item : TextView = itemView.findViewById(R.id.item)
        val name : TextView = itemView.findViewById(R.id.impnamename)
        val phone : TextView = itemView.findViewById(R.id.phonenumnum)
    }
}