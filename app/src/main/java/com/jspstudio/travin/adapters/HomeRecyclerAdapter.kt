package com.jspstudio.travin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.jspstudio.travin.R
import com.jspstudio.travin.databinding.ItemRecyclerHomeBinding
import com.jspstudio.travin.model.HomeItem

class HomeRecyclerAdapter constructor(val context: Context, var homeItems:MutableList<HomeItem>):RecyclerView.Adapter<HomeRecyclerAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding:ItemRecyclerHomeBinding = ItemRecyclerHomeBinding.bind(itemView)
    }

    // (2) 리스너 인터페이스 - 수락, 거절, 이름
    interface OnItemClickListener {
        fun nameClick(v: View, position: Int)
        fun locationClick(v: View, position: Int)
        fun contentsClick(v: View, position: Int)
        fun commentClick(v: View, position: Int)
        fun favoriteClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView: View = layoutInflater.inflate(R.layout.item_recycler_home, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        // (1) 리스트 내 항목 클릭 시 onClick() 호출
        holder.binding.tvHomeName.setOnClickListener { itemClickListener.nameClick(it, position) }
        holder.binding.tvHomeLocation.setOnClickListener { itemClickListener.locationClick(it, position) }
        holder.binding.tvHomeContents.setOnClickListener { itemClickListener.contentsClick(it, position) }
        holder.binding.icHomeComment.setOnClickListener { itemClickListener.commentClick(it, position) }
        holder.binding.icHomeFavorite.setOnClickListener { itemClickListener.favoriteClick(it, position) }

        holder.binding.tvHomeName.text = homeItems[position].homeName // 글 작성자 이름
        holder.binding.tvHomeLocation.text = homeItems[position].homeLocation // 글 작성자 위치
        holder.binding.tvHomeTimeBefore.text = homeItems[position].homeTimeBefore // 글 작성시간
        holder.binding.tvHomeContents.text = homeItems[position].homeContents // 작성자의 글 내용

        Glide.with(context).load(homeItems[position].homeProfile).into(holder.binding.ivHomeProfile) // 글 작성자 프로필
        Glide.with(context).load(homeItems[position].homePicture).into(holder.binding.ivHomePicture) // 글 작성자가 업로드한 사진
        Glide.with(context).load(homeItems[position].homeComment).into(holder.binding.icHomeComment) // 댓글달기 버튼

    }

    override fun getItemCount(): Int = homeItems.size


}