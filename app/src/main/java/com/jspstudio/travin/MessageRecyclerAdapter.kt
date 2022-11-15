package com.jspstudio.travin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.ItemRecyclerMessageBinding
import java.text.SimpleDateFormat
import java.util.*

class MessageRecyclerAdapter constructor(val context: Context, var msgItems:MutableList<MessageRecyclerItem>):RecyclerView.Adapter<MessageRecyclerAdapter.VH>(){

    // (2) 리스너 인터페이스
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener


    inner class VH constructor(itemView:View) : RecyclerView.ViewHolder(itemView){
        val binding:ItemRecyclerMessageBinding = ItemRecyclerMessageBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView: View = layoutInflater.inflate(R.layout.item_recycler_message, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: MessageRecyclerAdapter.VH, position: Int) {
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.binding.msgName.text = msgItems[position].name // 메세지화면 닉네임
        holder.binding.msgContents.text = msgItems[position].contents // 메세지화면 메세지내용
        holder.binding.msgTime.text = msgItems[position].time // 메세지화면 보낸시간

        Glide.with(context).load(msgItems[position].profile).into(holder.binding.msgProfile) // 메세지화면 프로필사진


    }

    override fun getItemCount(): Int = msgItems.size

}