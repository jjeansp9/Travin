package com.jspstudio.travin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ItemRecyclerHomeBinding

class HomeRecyclerAdapter constructor(val context: Context, var homeItems:MutableList<HomeItem>):RecyclerView.Adapter<HomeRecyclerAdapter.VH>(){

    inner class VH constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding:ItemRecyclerHomeBinding = ItemRecyclerHomeBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater = LayoutInflater.from(context)
        var itemView: View = layoutInflater.inflate(R.layout.item_recycler_home, parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {

        holder.binding.tvHomeName.text = homeItems[position].homeName // 글 작성자 이름
        holder.binding.tvHomeLocation.text = homeItems[position].homeLocation // 글 작성자 위치
        holder.binding.tvHomeTimeBefore.text = homeItems[position].homeTimeBefore // 글 작성시간
        holder.binding.tvHomeContents.text = homeItems[position].homeContents // 작성자의 글 내용

        Glide.with(context).load(homeItems[position].homeProfile).into(holder.binding.ivHomeProfile) // 글 작성자 프로필
        Glide.with(context).load(homeItems[position].homePicture).into(holder.binding.ivHomePicture) // 글 작성자가 업로드한 사진
        Glide.with(context).load(homeItems[position].homeFavorite).into(holder.binding.icHomeFavorite) // 글 좋아요 버튼
        Glide.with(context).load(homeItems[position].homeComment).into(holder.binding.icHomeComment) // 글 좋아요 버튼
    }

    override fun getItemCount(): Int = homeItems.size

}