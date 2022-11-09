package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.ActivitySignInBinding
import java.util.*

// 로그인 화면 //

class SignInActivity : AppCompatActivity() {

    val binding: ActivitySignInBinding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    var items: ArrayList<UserData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //  로그인하기버튼을 누르면 키워드선택 화면으로 이동
        binding.btnSignIn.setOnClickListener { clickSignIn() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 로그인버튼. 클릭하면 데이터 저장 및 화면이동 ( firebase )
    fun clickSignIn(){


        saveUserData()



    // 레트로핏으로 서버에 json형태(연관배열)의 저장된 데이터들 불러오기
    //        val retrofit: Retrofit = RetrofitSignUpHelper().getRetrofitInstance()
//        val retrofitSignUpService = retrofit.create(RetrofitSignUpService::class.java)
//
//        val call: Call<ArrayList<UserData?>?>? = retrofitSignUpService.loadUserDataFromServer()
//        call!!.enqueue(object : Callback<ArrayList<UserData?>?> {
//            override fun onResponse(
//                call: Call<ArrayList<UserData?>?>,
//                response: Response<ArrayList<UserData?>?>) {
//
//                // 결과 json array를 곧바로 파싱하여 Arraylist<MarketItem>로 변환된 리스트 받기
//                val items: ArrayList<UserData?>? = response.body()
//
//                val buffer = StringBuffer()
//                for (item in items!!) {
//                    buffer.append(item?.id )
//                }
//                binding.tv.text = buffer.toString()
//
//            }
//
//            override fun onFailure(call: Call<ArrayList<UserData?>?>, t: Throwable) {
//                Toast.makeText(this@SignInActivity, "error : " + t.message, Toast.LENGTH_SHORT).show()
//            }
//        })

    } // clickSignIn()

    // 데이터 저장 메소드
    fun saveUserData(){
        UserDatas.id= binding.etId.text.toString()

        // 아이디 입력칸이 공백이거나 입력하지 않았다면 실행X
        if(UserDatas.id!!.replace(" ", "") == ""){
            Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show()
        }else{
            // firebase db에 저장하기 위해 Map Collection으로 묶어서 저장
            val firebaseFirestore = FirebaseFirestore.getInstance()

            val userRef: CollectionReference = firebaseFirestore.collection("users")

            // Document 명을 닉네임으로, Field'값'에 이미지경로 url을 저장
            val profile: MutableMap<String, String> = HashMap()
            profile["profile"] = UserDatas.id!!

            userRef.document(UserDatas.id!!).set(profile)

            startActivity(Intent(this, KeywordSelectActivity::class.java))
            finish()
        }



    }
}




























