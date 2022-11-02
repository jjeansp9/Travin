package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jspstudio.travin.databinding.ActivitySignUpBinding

// 회원가입 화면 //

class SignUpActivity : AppCompatActivity() {

    private var mBinding : ActivitySignUpBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //  가입하기버튼을 누르면 키워드선택 화면으로 이동
        binding.btnJoin.setOnClickListener {
            val intent: Intent = Intent(this, KeywordSelectActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}