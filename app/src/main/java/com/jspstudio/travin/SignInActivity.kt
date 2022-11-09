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


        loadData()



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

    // 회원 아이디,비밀번호 데이터 불러오기
    fun loadData(){
        UserDatas.id= binding.etId.text.toString()
        UserDatas.password= binding.etPassword.text.toString()

        // FireStore DB 데이터들 가져오기
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val userRef = firebaseFirestore.collection("users")

        // firestore DB 아이디,비밀번호가 맞지않으면 로그인 실패
        userRef.get().addOnCompleteListener { task ->
            val snapshots = task.result
            val bufferId = StringBuffer()
            val bufferPw = StringBuffer()
            for (snapshot in snapshots) {
                val user = snapshot.data
                val id = user["id"].toString()
                val password = user["password"].toString()
                bufferId.append("$id")
                bufferPw.append("$password")
                if(UserDatas.id!!.replace(" ", "") == ""){

                    Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    break
                }else if(bufferId.toString() != UserDatas.id || bufferPw.toString() != UserDatas.password){

                }else if (bufferId.toString() == UserDatas.id && bufferPw.toString() == UserDatas.password){



                    // 앱을 처음 실행할때 한번 입력한 닉네임,사진을 폰에 저장 (다시 입력하지 않기위해)
                    // 2. SharedPreferences 에 저장
                    val pref = getSharedPreferences("account", MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putString("nickname", UserDatas.id)

                    editor.commit()

                    startActivity(Intent(this, KeywordSelectActivity::class.java))
                    finish()
                    break
                }
                binding.tv.text = bufferId.toString() + bufferPw.toString()
            }

        }

    }

}




























