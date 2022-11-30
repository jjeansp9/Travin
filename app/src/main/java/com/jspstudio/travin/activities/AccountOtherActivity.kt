package com.jspstudio.travin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jspstudio.travin.databinding.ActivityAccountOtherBinding

class AccountOtherActivity : AppCompatActivity() {

    val binding:ActivityAccountOtherBinding by lazy { ActivityAccountOtherBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}