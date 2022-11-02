package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemRecyclerMessageBinding

class MessageRecyclerAdapter constructor(val context: Context, var msgItems:MutableList<MessageRecyclerItem>):RecyclerView.Adapter<MessageRecyclerAdapter.VH>(){

    inner class VH constructor(itemView:View) : RecyclerView.ViewHolder(itemView){
        val binding:ItemRecyclerMessageBinding = ItemRecyclerMessageBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView: View = layoutInflater.inflate(R.layout.item_recycler_message, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.msgName.text = msgItems[position].name // 메세지화면 닉네임
        holder.binding.msgContents.text = msgItems[position].contents // 메세지화면 메세지내용
        holder.binding.msgTime.text = msgItems[position].time // 메세지화면 보낸시간

        Glide.with(context).load(msgItems[position].profile).into(holder.binding.msgProfile) // 메세지화면 프로필사진
    }

    override fun getItemCount(): Int = msgItems.size

}