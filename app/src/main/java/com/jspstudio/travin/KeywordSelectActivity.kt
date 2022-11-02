package com.jspstudio.travin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jspstudio.travin.databinding.ActivityKeywordSelectBinding

// 키워드 선택화면 //

class KeywordSelectActivity : AppCompatActivity() {

    private var mBinding : ActivityKeywordSelectBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityKeywordSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼 ( 로그인&회원가입 시작화면으로 이동 )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // fab 버튼을 클릭하면 메인 홈화면으로 이동
        binding.fab.setOnClickListener{
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}