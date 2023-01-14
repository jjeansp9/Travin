package com.jspstudio.travin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.R
import com.jspstudio.travin.databinding.ActivitySignUpBinding
import com.jspstudio.travin.model.UserDatas
import com.jspstudio.travin.network.RetrofitSignUpHelper
import com.jspstudio.travin.network.RetrofitSignUpService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// 회원가입 화면 //

class SignUpActivity : AppCompatActivity() {

    val binding: ActivitySignUpBinding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "회원가입"
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

        // 레트로핏 라이브러리를 이용하여 회원정보 저장
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

                Toast.makeText(this@SignUpActivity, "" + s, Toast.LENGTH_SHORT).show() // 메세지 : 회원가입 완료

                val intent: Intent = Intent(this@SignUpActivity, KeywordSelectActivity::class.java)
                startActivity(intent)
                finish()

            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "error : " + t.message, Toast.LENGTH_SHORT).show()
            }
        })


        // 파이어베이스를 이용하여 회원정보 저장
        UserDatas.nickname= binding.etName.text.toString()
        UserDatas.id= binding.etId.text.toString()
        UserDatas.password= binding.etPassword.text.toString()

        // firebase db에 저장하기 위해 Map Collection으로 묶어서 저장
        val firebaseFirestore = FirebaseFirestore.getInstance()

        val userRef: CollectionReference = firebaseFirestore.collection("users") // 컬렉션명 : users

        val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmSS")

        // Document 명을 닉네임으로, Field'값'에 이미지경로 url을 저장
        val profile: MutableMap<String, String> = HashMap()
        profile["nickname"] = UserDatas.nickname.toString()
        profile["id"] = UserDatas.id.toString()
        profile["password"] = UserDatas.password.toString()
        profile["profile"] = ""
        profile["startTime"] = sdf.format(Date())

        userRef.document(UserDatas.id.toString()).set(profile)

        // 앱을 처음 실행할때 한번 입력한 회원정보를 폰에 저장 (다시 입력하지 않기위해)
        // 2. SharedPreferences 에 저장
        val pref = getSharedPreferences("account", MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("id", UserDatas.id)
        editor.putString("nickname", UserDatas.nickname)
        editor.putString("password", UserDatas.password)
        editor.putString("profile", R.drawable.profile.toString())

        editor.commit()

    } // clickBtnSignUp()


}



















