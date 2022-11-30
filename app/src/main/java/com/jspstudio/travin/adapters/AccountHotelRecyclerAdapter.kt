package com.jspstudio.travin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.R
import com.jspstudio.travin.databinding.ItemRecyclerAccountHotelBinding
import com.jspstudio.travin.model.AccountHotelItem

class AccountHotelRecyclerAdapter constructor(val context: Context, var hotelItems: MutableList<AccountHotelItem>):
    RecyclerView.Adapter<AccountHotelRecyclerAdapter.VH>() {
    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: ItemRecyclerAccountHotelBinding = ItemRecyclerAccountHotelBinding.bind(itemView)
    }
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView: View = layoutInflater.inflate(R.layout.item_recycler_account_hotel, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.binding.tvHotelTitle.text = hotelItems[position].hotelName // 숙소이름
        holder.binding.tvPrice.text = hotelItems[position].hotelPrice // 숙소가격

        Glide.with(context).load(hotelItems[position].hotelImage).into(holder.binding.iv) // 숙소 이미지

    }

    override fun getItemCount(): Int = hotelItems.size
}