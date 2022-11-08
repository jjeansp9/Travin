package com.jspstudio.travin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.jspstudio.travin.databinding.ActivityLoginBinding

// 로그인 & 회원가입 시작 화면 //

class LoginActivity : AppCompatActivity() {

    lateinit var btn: Button

    val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 클릭시 회원가입 화면으로 이동
        binding.btn.setOnClickListener {
            val intent: Intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // 클릭시 로그인 화면으로 이동
        binding.btnLogin.setOnClickListener{
            val intent: Intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }




    }
}