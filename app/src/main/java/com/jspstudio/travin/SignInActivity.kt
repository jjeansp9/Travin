package com.jspstudio.travin

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
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}