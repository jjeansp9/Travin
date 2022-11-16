package com.jspstudio.travin

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    lateinit var binding:FragmentAccountBinding
    lateinit var imgUri:Uri

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

        binding.accountProfile.setOnClickListener{uploadProfile()} // 프로필사진 업로드
        binding.tvHotelList.setOnClickListener { hotelList() } // 호텔 리스트
        binding.tvFriendList.setOnClickListener { friendList() } // 친구목록 리스트
        binding.tvMyUploadList.setOnClickListener { myUploadList() } // 내가쓴 글 리스트

    }

    // 프로필이미지를 업로드하는 메소드
    private fun uploadProfile(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)

    }

    // 사진 업로드 관련 코드
    var resultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != AppCompatActivity.RESULT_CANCELED) {
            imgUri = result.data!!.data!!
            Glide.with(this@AccountFragment).load(imgUri).into(binding.accountProfile)
        }
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