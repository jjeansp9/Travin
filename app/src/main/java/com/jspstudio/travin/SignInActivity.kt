package com.jspstudio.travin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jspstudio.travin.databinding.ActivitySignInBinding
import com.jspstudio.travin.databinding.ActivitySignUpBinding

// 로그인 화면 //

class SignInActivity : AppCompatActivity() {

    private var mBinding : ActivitySignInBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //  로그인하기버튼을 누르면 키워드선택 화면으로 이동
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