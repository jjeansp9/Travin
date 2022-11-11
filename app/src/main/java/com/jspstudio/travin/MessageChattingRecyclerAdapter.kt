package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemRecyclerMessageMychatBinding
import com.jspstudio.travin.databinding.ItemRecyclerMessageOtherchatBinding

class MessageChattingRecyclerAdapter constructor(val context: Context, var msgItems:MutableList<MessageChattingRecyclerItem>):
    RecyclerView.Adapter<MessageChattingRecyclerAdapter.VH>() {

    val TYPE_MY = 0
    val TYPE_OTHER = 1

    override fun getItemViewType(position: Int): Int {
        return if (msgItems[position].name == UserDatas.nickname) TYPE_MY else TYPE_OTHER
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val myBinding: ItemRecyclerMessageMychatBinding = ItemRecyclerMessageMychatBinding.bind(itemView)
        val otherBinding: ItemRecyclerMessageOtherchatBinding = ItemRecyclerMessageOtherchatBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val layoutInflater:LayoutInflater = LayoutInflater.from(context)

        var itemView: View? = null
        itemView =
            if (viewType == TYPE_MY) layoutInflater.inflate(R.layout.item_recycler_message_mychat, parent, false)
            else layoutInflater.inflate(R.layout.item_recycler_message_otherchat, parent, false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        //Glide.with(context).load(R.drawable.ic_profile).into(holder.myBinding.msgChatMyProfile)
        holder.myBinding.msgChatMyName.text = msgItems[position].name
        holder.myBinding.msgChatMyBox.text = msgItems[position].message
        holder.myBinding.msgChatMyTime.text = msgItems[position].time

        holder.otherBinding.msgChatOtherUser.text = msgItems[position].name
        holder.otherBinding.msgChatOtherTime.text = msgItems[position].time
        holder.otherBinding.msgChatOtherBox.text = msgItems[position].message
        //Glide.with(context).load(R.drawable.ic_profile).into(holder.otherBinding.msgChatOtherProfile)
    }

    override fun getItemCount(): Int = msgItems.size

}

























