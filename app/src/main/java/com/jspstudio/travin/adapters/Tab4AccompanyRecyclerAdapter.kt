package com.jspstudio.travin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.R
import com.jspstudio.travin.databinding.ItemTab4RecyclerAccompanyBinding
import com.jspstudio.travin.model.Tab4AccompanyItem

class Tab4AccompanyRecyclerAdapter constructor(val context:Context, var tab4Items:MutableList<Tab4AccompanyItem>): RecyclerView.Adapter<Tab4AccompanyRecyclerAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemTab4RecyclerAccompanyBinding = ItemTab4RecyclerAccompanyBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView : View = layoutInflater.inflate(R.layout.item_tab4_recycler_accompany, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:Tab4AccompanyItem = tab4Items[position]

        holder.binding.tvTab4Contents.text = item.accompanyContents // 여행질문 내용
        holder.binding.tvTab4Name.text = item.accompanyName // 여행질문자 닉네임
        holder.binding.tvTab4TimeBefore.text = item.accompanyTimeBefore // 질문한지 흐른 시간 (n시간 전)

        Glide.with(context).load(item.accompanyPicture).into(holder.binding.ivTab4Picture) // 여행질문글 사진
    }

    override fun getItemCount(): Int = tab4Items.size

}
