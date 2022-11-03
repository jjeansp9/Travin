package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemTab1RecyclerCommunityBinding
import com.jspstudio.travin.databinding.ItemTab2RecyclerQuestionBinding

class Tab1CommunityRecyclerAdapter constructor(val context:Context, var tab1Items:MutableList<Tab1CommunityItem>): RecyclerView.Adapter<Tab1CommunityRecyclerAdapter.VH>() {

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemTab1RecyclerCommunityBinding =
            ItemTab1RecyclerCommunityBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView: View =
            layoutInflater.inflate(R.layout.item_tab1_recycler_community, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: Tab1CommunityItem = tab1Items[position]

        Glide.with(context).load(item.communityProfile)
            .into(holder.binding.tab1CommunityProfile)
    }

    override fun getItemCount(): Int = tab1Items.size
}


















