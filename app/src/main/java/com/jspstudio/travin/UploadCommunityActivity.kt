package com.jspstudio.travin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jspstudio.travin.databinding.ActivityUploadCommunityBinding

class UploadCommunityActivity : AppCompatActivity() {

    val binding: ActivityUploadCommunityBinding by lazy { ActivityUploadCommunityBinding.inflate(layoutInflater) }

    var tabNum:Int? = null
    val tabMenu = arrayOf("여행 질문", "여행 꿀팁", "여행 동행", "여행 후기 ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = intent
        tabNum = intent.getIntExtra("tabNumber", 0)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 글쓰기 탭 선택한 것에 따라 업로드 탭 표시
        supportActionBar?.title = tabMenu[tabNum!!]
    }

    // 제목줄의 뒤로가기버튼(업버튼)을 클릭했을 때 자동실행되는 콜백메소드
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


}