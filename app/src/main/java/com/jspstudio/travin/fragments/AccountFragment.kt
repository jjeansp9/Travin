package com.jspstudio.travin.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.FragmentAccountBinding
import java.util.*

class AccountFragment : Fragment() {

    lateinit var binding:FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pref = context?.getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
        val nickname = pref?.getString("nickname", null)

        binding.accountName.text = "$nickname"
        binding.accountKeyword.text = "${nickname}님의 여행스타일"


        binding.hotelLayout.setOnClickListener { hotelList() } // 호텔 리스트
        binding.friendLayout.setOnClickListener { friendList() } // 친구목록 리스트
        binding.myUploadLayout.setOnClickListener { myUploadList() } // 내가쓴 글 리스트
        binding.btnUpload.setOnClickListener { startActivity(Intent(context,UploadProfileActivity::class.java)) } // 프로필사진 업로드 버튼 ( 프로필사진 수정화면으로 이동 )
        binding.accountProfile.setOnClickListener{startActivity(Intent(context,UploadProfileActivity::class.java))} // 프로필사진을 클릭하면 프로필사진 수정화면으로 이동

    }

    override fun onResume() {
        super.onResume()
        //completeUploadProfile()
        loadProfile()
    }

    fun loadProfile(){
        val pref: SharedPreferences = view?.context!!.getSharedPreferences("account", Context.MODE_PRIVATE)
        UserDatas.profileUrl = pref.getString("profileUrl", null)

        // 유저데이터가 비어있지 않은 경우 등록한 프로필사진으로 이미지 설정
        if (UserDatas.profileUrl != null){ Glide.with(view?.context!!).load(UserDatas.profileUrl).into(binding.accountProfile) }

    }


    // 호텔 리스트 메소드
    private fun hotelList(){
        startActivity(Intent(context, AccountHotelListActivity::class.java))
    }
    // 친구목록 리스트 메소드
    private fun friendList(){
        startActivity(Intent(context, AccountFriendListActivity::class.java))
    }
    // 내가쓴 글 리스트 메소드
    private fun myUploadList(){

    }


}