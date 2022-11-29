package com.jspstudio.travin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.R
import com.jspstudio.travin.databinding.ItemRecyclerAccountResponseFriendBinding
import com.jspstudio.travin.model.AccountFriendResponseItem

class AccountFriendResponseRecyclerAdapter constructor(val context: Context, var responseItems: MutableList<AccountFriendResponseItem>): RecyclerView.Adapter<AccountFriendResponseRecyclerAdapter.VH>(){
    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemRecyclerAccountResponseFriendBinding = ItemRecyclerAccountResponseFriendBinding.bind(itemView)
    }

    // (2) 리스너 인터페이스 - 수락, 거절, 이름
    interface OnItemClickListener {
        fun onAllowClick(v: View, position: Int)
        fun onDenyClick(v: View, position: Int)
        fun onNameClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var itemView: View = inflater.inflate(R.layout.item_recycler_account_response_friend, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        // 아이템마다 클릭 반응 - 수락, 거절, 이름
        holder.binding.responseFriendName.setOnClickListener{ itemClickListener.onNameClick(it,position) }
        holder.binding.btnAllow.setOnClickListener{ itemClickListener.onAllowClick(it,position) }
        holder.binding.btnDeny.setOnClickListener{ itemClickListener.onDenyClick(it,position) }

        holder.binding.responseFriendName.text = responseItems[position].responseName
        Glide.with(context).load(responseItems[position].responseProfile).into(holder.binding.responseFriendProfile)
        Glide.with(context).load(responseItems[position].allow).into(holder.binding.btnAllow)
        Glide.with(context).load(responseItems[position].deny).into(holder.binding.btnDeny)
    }

    override fun getItemCount(): Int = responseItems.size
}