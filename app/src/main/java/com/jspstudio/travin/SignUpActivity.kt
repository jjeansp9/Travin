package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jspstudio.travin.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

// 회원가입 화면 //

class SignUpActivity : AppCompatActivity() {

    val binding: ActivitySignUpBinding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.btnName.setOnClickListener { clickBtnName() } // 닉네임 중복확인
        binding.btnId.setOnClickListener { clickBtnId() } // 아이디 중복확인
        binding.btnSignUp.setOnClickListener { clickBtnSignUp() } // 가입하기버튼을 누르면 키워드선택 화면으로 이동 및 회원가입

    }

    // 뒤로가기버튼 반응 메소드
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 닉네임 중복확인 버튼
    private fun clickBtnName(){

    }

    // 아이디 중복확인 버튼
    private fun clickBtnId(){

    }

    // 회원가입 버튼
    private fun clickBtnSignUp(){

        // 전송할 데이터들 [ name, title, message, price, imaPath ] 5개
        val nickname: String = binding.etName.text.toString() // 닉네임
        val id: String = binding.etId.text.toString() // 아이디
        val password: String = binding.etPassword.text.toString() // 비밀번호

        val retrofit: Retrofit = RetrofitSignUpHelper().getRetrofitInstance()
        val retrofitSignUpService = retrofit.create(RetrofitSignUpService::class.java)

        // 데이터들 박스

        // 2. 데이터들 박스
        val userDataPart: MutableMap<String, String> = HashMap()
        userDataPart["nickname"] = nickname
        userDataPart["id"] = id
        userDataPart["password"] = password

        val call: Call<String?>? = retrofitSignUpService.userDataToServer(userDataPart)
        call!!.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                val s = response.body()

                Toast.makeText(this@SignUpActivity, "" + s, Toast.LENGTH_SHORT).show()

                val intent: Intent = Intent(this@SignUpActivity, KeywordSelectActivity::class.java)
                startActivity(intent)
                finish()

            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "error : " + t.message, Toast.LENGTH_SHORT).show()
            }
        })

    } // clickBtnSignUp()


}



















