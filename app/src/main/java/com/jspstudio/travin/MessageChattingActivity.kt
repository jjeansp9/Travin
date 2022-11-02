package com.jspstudio.travin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jspstudio.travin.databinding.ActivityMessageChattingBinding
import com.jspstudio.travin.databinding.ActivitySignUpBinding

class MessageChattingActivity : AppCompatActivity() {

    private var mBinding : ActivityMessageChattingBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMessageChattingBinding.inflate(layoutInflater)
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