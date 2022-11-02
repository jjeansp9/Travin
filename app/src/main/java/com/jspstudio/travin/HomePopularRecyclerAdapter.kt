package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemRecyclerHomePopularBinding

class HomePopularRecyclerAdapter constructor(val context: Context, var homePopularItems:MutableList<HomePopularItem>):RecyclerView.Adapter<HomePopularRecyclerAdapter.VH>(){

    inner class VH constructor(itemView:View) : RecyclerView.ViewHolder(itemView){
        val binding:ItemRecyclerHomePopularBinding = ItemRecyclerHomePopularBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView: View = layoutInflater.inflate(R.layout.item_recycler_home_popular, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Glide.with(context).load(homePopularItems[position].picture).into(holder.binding.ivHomePopularPicture)
    }

    override fun getItemCount(): Int = homePopularItems.size

}