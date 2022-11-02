package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemTab3RecyclerUsefulInfoBinding

class Tab3UsefulInfoRecyclerAdapter constructor(val context:Context, var tab3Items:MutableList<Tab3UsefulInfoItem>): RecyclerView.Adapter<Tab3UsefulInfoRecyclerAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding:ItemTab3RecyclerUsefulInfoBinding = ItemTab3RecyclerUsefulInfoBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView : View = layoutInflater.inflate(R.layout.item_tab3_recycler_useful_info, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:Tab3UsefulInfoItem = tab3Items[position]

        holder.binding.tvTab3Contents.text = item.usefulInfoContents // 여행질문 내용
        holder.binding.tvTab3Name.text = item.usefulInfoName // 여행질문자 닉네임
        holder.binding.tvTab3TimeBefore.text = item.usefulInfoTimeBefore // 질문한지 흐른 시간 (n시간 전)

        Glide.with(context).load(item.usefulInfoPicture).into(holder.binding.ivTab3Picture) // 여행질문글 사진


    }

    override fun getItemCount(): Int = tab3Items.size

}
