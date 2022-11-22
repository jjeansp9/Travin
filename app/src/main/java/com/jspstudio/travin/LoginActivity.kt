package com.jspstudio.travin

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.jspstudio.travin.databinding.ActivityLoginBinding

// 로그인 & 회원가입 시작 화면 //

class LoginActivity : AppCompatActivity() {

    lateinit var btn: Button

    val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btn.setOnClickListener { moveSignupActivity() } // 회원가입 화면으로 이동
        binding.btnLogin.setOnClickListener{ moveSigninActivity() } // 로그인 화면으로 이동

        // 외부저장소에 대한 퍼미션 - 사진업로드를 위해 필요 - 2개를 요청해도 외부메모리사용 요청은 하나만 요청함
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION
        )
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions, 100) //requestCode는 식별코드라서 아무거나 쓰면됨
        }

    }

    fun moveSignupActivity(){ startActivity(Intent(this, SignUpActivity::class.java)) }

    fun moveSigninActivity(){ startActivity(Intent(this, SignInActivity::class.java)) }

}