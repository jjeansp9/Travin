package com.jspstudio.travin.activities

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
import com.jspstudio.travin.model.UserData
import com.jspstudio.travin.network.G
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

// 로그인 & 회원가입 시작 화면 //

class LoginActivity : AppCompatActivity() {

    lateinit var btn: Button

    val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 카카로 계정으로 로그인
        binding.kakaoLogin.setOnClickListener{kakaoLogin()}


        // 회원가입 화면으로 이동
        binding.btn.setOnClickListener { startActivity(Intent(this, SignUpActivity::class.java)) }

        // 로그인 화면으로 이동
        binding.btnLogin.setOnClickListener{ startActivity(Intent(this, SignInActivity::class.java)) }

        // 외부저장소에 대한 퍼미션 - 사진업로드를 위해 필요 - 2개를 요청해도 외부메모리사용 요청은 하나만 요청함
        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION
        )
        if (checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(permissions, 100)
        }
    }

    private fun kakaoLogin(){
        // Kakao Login API를 이용하여 사용자 정보 취득

        // 로그인 시도한 결과를 받았을 때 발동하는 콜백함수를 별도로 만들기
        val callback:(OAuthToken?, Throwable?)->Unit= { token, error ->
            if (error != null){
                Toast.makeText(this, "카카오 로그인실패", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "카카오 로그인성공", Toast.LENGTH_SHORT).show()

                // 사용자 정보 요청
                UserApiClient.instance.me { user, error ->
                    if (user!=null){
                        var id:String = user.id.toString()
                        var nickname:String = user.kakaoAccount?.name ?: "" // 혹시 이메일이 없다면, 이메일의 기본값을 ""로

                        Toast.makeText(this, "사용자 이메일 정보 : $nickname", Toast.LENGTH_SHORT).show()
                        G.userAccount= UserData(id, nickname,"")

                        // 로그인이 성공했으니 Main 화면으로 전환
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }

        // 카카오톡 로그인을 권장하지만 설치가 되어있지 않다면 카카오계정으로 로그인 시도
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
            UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
        }else{
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

}