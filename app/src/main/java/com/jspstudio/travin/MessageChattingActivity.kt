package com.jspstudio.travin

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.jspstudio.travin.databinding.ActivityMainBinding
import com.jspstudio.travin.databinding.ActivityMessageChattingBinding

class MessageChattingActivity : AppCompatActivity() {

    val binding: ActivityMessageChattingBinding by lazy { ActivityMessageChattingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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