package com.jspstudio.travin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.R
import com.jspstudio.travin.databinding.ItemRecyclerCommentBinding
import com.jspstudio.travin.model.CommentRecyclerItem

class CommentRecyclerAdapter constructor(val context: Context, var commentItems: MutableList<CommentRecyclerItem>): RecyclerView.Adapter<CommentRecyclerAdapter.VH>(){
    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemRecyclerCommentBinding = ItemRecyclerCommentBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var itemView: View = layoutInflater.inflate(R.layout.item_recycler_comment, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.msgChatTime.text = commentItems[position].time // 숙소이름
        holder.binding.msgChatName.text = commentItems[position].name // 숙소내용
        holder.binding.msgChatBox.text = commentItems[position].comment // 숙소가격

        Glide.with(context).load(commentItems[position].profile).into(holder.binding.msgChatProfile) // 숙소 이미지
    }

    override fun getItemCount(): Int = commentItems.size
}