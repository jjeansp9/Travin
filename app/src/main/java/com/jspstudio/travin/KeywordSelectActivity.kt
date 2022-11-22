package com.jspstudio.travin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ActivityKeywordSelectBinding

// 키워드 선택화면 //

class KeywordSelectActivity : AppCompatActivity() {

    val binding:ActivityKeywordSelectBinding by lazy { ActivityKeywordSelectBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼 ( 로그인&회원가입 시작화면으로 이동 )
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "여행스타일 키워드"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // fab 버튼을 클릭하면 메인 홈화면으로 이동
        binding.fab.setOnClickListener{ nextActivity() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 다음액티비티로 넘어가는 메소드
    fun nextActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }



}