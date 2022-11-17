package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class MessageChattingRecyclerAdapter constructor(val context: Context, var msgItems:MutableList<MessageChattingRecyclerItem>):
    RecyclerView.Adapter<MessageChattingRecyclerAdapter.VH>() {

    val TYPE_MY = 0
    val TYPE_OTHER = 1

    override fun getItemViewType(position: Int): Int {
        val pref = context.getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
        val nickname = pref?.getString("nickname", null)
        return if (msgItems[position].nickname.toString() == nickname) TYPE_MY else TYPE_OTHER
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name:TextView by lazy { itemView.findViewById(R.id.msg_chat_name) }
        val time:TextView by lazy { itemView.findViewById(R.id.msg_chat_time) }
        val msg: TextView by lazy { itemView.findViewById(R.id.msg_chat_box) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        val layoutInflater:LayoutInflater = LayoutInflater.from(context)

        val itemView =
            if (viewType == TYPE_MY) layoutInflater.inflate(R.layout.item_recycler_message_mychat, parent, false)
            else layoutInflater.inflate(R.layout.item_recycler_message_otherchat, parent, false)

        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item: MessageChattingRecyclerItem = msgItems[position]
        holder.name.text = item.nickname
        holder.time.text = item.time
        holder.msg.text = item.message
        //Glide.with(context).load(R.drawable.ic_profile).into(holder.myBinding.msgChatMyProfile)


    }

    override fun getItemCount(): Int = msgItems.size

}

























