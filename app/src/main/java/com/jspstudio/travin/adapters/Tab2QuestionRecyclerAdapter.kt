package com.jspstudio.travin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.R
import com.jspstudio.travin.databinding.ItemRecyclerMessageBinding
import com.jspstudio.travin.databinding.ItemTab2RecyclerQuestionBinding
import com.jspstudio.travin.model.Tab2QuestionItem

class Tab2QuestionRecyclerAdapter constructor(val context:Context, var tab2Items:MutableList<Tab2QuestionItem>): RecyclerView.Adapter<Tab2QuestionRecyclerAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemTab2RecyclerQuestionBinding = ItemTab2RecyclerQuestionBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val itemView : View = layoutInflater.inflate(R.layout.item_tab2_recycler_question, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item:Tab2QuestionItem = tab2Items[position]

        holder.binding.tvTab2Contents.text = item.questionContents // 여행질문 내용
        holder.binding.tvTab2Name.text = item.questionName // 여행질문자 닉네임
        holder.binding.tvTab2TimeBefore.text = item.questionTimeBefore // 질문한지 흐른 시간 (n시간 전)

        Glide.with(context).load(item.questionPicture).into(holder.binding.ivTab2Picture) // 여행질문글 사진
    }

    override fun getItemCount(): Int = tab2Items.size

}
