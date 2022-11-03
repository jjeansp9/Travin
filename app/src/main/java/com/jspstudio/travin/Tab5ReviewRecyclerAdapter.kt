package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemTab5RecyclerReviewBinding

class Tab5ReviewRecyclerAdapter constructor(val context:Context, var tab5Items:MutableList<Tab5ReviewItem>): RecyclerView.Adapter<Tab5ReviewRecyclerAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemTab5RecyclerReviewBinding = ItemTab5RecyclerReviewBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView : View = layoutInflater.inflate(R.layout.item_tab5_recycler_review, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:Tab5ReviewItem = tab5Items[position]

        holder.binding.tvTab5Contents.text = item.reviewContents // 여행질문 내용
        holder.binding.tvTab5Name.text = item.reviewName // 여행질문자 닉네임
        holder.binding.tvTab5TimeBefore.text = item.reviewTimeBefore // 질문한지 흐른 시간 (n시간 전)

        Glide.with(context).load(item.reviewPicture).into(holder.binding.ivTab5Picture) // 여행질문글 사진
    }

    override fun getItemCount(): Int = tab5Items.size

}
