package com.jspstudio.travin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemRecyclerAccountFriendListBinding
import com.jspstudio.travin.model.AccountFriendListItem

class AccountFriendListRcyclerAdapter constructor(val context: Context, var friendListItem: MutableList<AccountFriendListItem>): RecyclerView.Adapter<AccountFriendListRcyclerAdapter.VH>(){
    inner class VH constructor(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding: ItemRecyclerAccountFriendListBinding = ItemRecyclerAccountFriendListBinding.bind(itemView)
    }

    // (2) 리스너 인터페이스 - 수락, 거절, 이름
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var itemView: View = inflater.inflate(R.layout.item_recycler_account_friend_list, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener { itemClickListener.onClick(it, position) }

        holder.binding.friendListName.text = friendListItem[position].name
        Glide.with(context).load(friendListItem[position].profile).into(holder.binding.friendListProfile)
    }

    override fun getItemCount(): Int = friendListItem.size
}