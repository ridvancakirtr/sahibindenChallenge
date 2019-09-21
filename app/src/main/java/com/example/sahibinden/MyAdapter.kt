package com.example.sahibinden

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class MyAdapter(private val myDataset: MutableList<itemDataModel>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.day.text= myDataset[position].day
        holder.month.text= myDataset[position].month
        holder.market.text= myDataset[position].market
        holder.item.text= myDataset[position].item
        holder.price.text= myDataset[position].price
        holder.status.text= myDataset[position].status
    }
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        //İnflate edildikden sonra İçindeki elemanlara erişim
        var oneLineItem=view
        var day=oneLineItem.day
        var month=oneLineItem.month
        var market=oneLineItem.market
        var item=oneLineItem.item
        var price=oneLineItem.price
        var status=oneLineItem.status

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}