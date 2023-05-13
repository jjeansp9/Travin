package com.jspstudio.travin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.ActivitySignInBinding
import com.jspstudio.travin.model.UserData
import com.jspstudio.travin.model.UserDatas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// 로그인 화면 //

class SignInActivity : AppCompatActivity() {

    val binding: ActivitySignInBinding by lazy { ActivitySignInBinding.inflate(layoutInflater) }

    var items: ArrayList<UserData> = ArrayList()

    var idPass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "로그인"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar
        loadData()


        //  로그인하기버튼을 누르면 키워드선택 화면으로 이동
        binding.btnSignIn.setOnClickListener { clickSignIn() }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 로그인버튼. 클릭하면 데이터 저장 및 화면이동 ( firebase )
    fun clickSignIn(){

        UserDatas.id= binding.etId.text.toString()
        UserDatas.password= binding.etPassword.text.toString()

        // FireStore DB 데이터들 가져오기
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val userRef = firebaseFirestore.collection("users")

        // firestore DB 아이디,비밀번호가 맞지않으면 로그인 실패
        userRef.get().addOnCompleteListener { task ->
            val snapshots = task.result
            val buffer = StringBuffer()

            // 회원정보 확인
            for (snapshot in snapshots) {
                val user = snapshot.data
                val id = user["id"].toString()
                val password = user["password"].toString()
                val nickname = user["nickname"].toString()
                buffer.append("$id"+"$password")
                buffer.append("$nickname")

                if(UserDatas.id!!.replace(" ", "") == ""){
                    Toast.makeText(this, "아이디를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    break
                }else if(id != UserDatas.id || password != UserDatas.password){

                }else if (id+password == UserDatas.id+UserDatas.password){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()



                    // 폰에 회원정보 저장
                    val pref = getSharedPreferences("account", MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putString("id", UserDatas.id)
                    editor.putString("nickname", nickname)
                    editor.putString("password", UserDatas.password)
                    editor.commit()

                    // 키워드선택화면으로 이동
                    startActivity(Intent(this@SignInActivity, KeywordSelectActivity::class.java ))
                    finish()
                    idPass = true
                    break
                }
            } // 회원정보 확인
            if(!idPass){ Toast.makeText(this, "회원정보가 맞지 않습니다", Toast.LENGTH_SHORT).show() }
        }

    // 레트로핏으로 서버에 json형태(연관배열)의 저장된 데이터들 불러오기
//            val retrofit: Retrofit = RetrofitSignUpHelper().getRetrofitInstance()
//        val retrofitSignUpService = retrofit.create(RetrofitSignUpService::class.java)
//
//        val call: Call<ArrayList<UserData?>?>? = retrofitSignUpService.loadUserDataFromServer()
//        call!!.enqueue(object : Callback<ArrayList<UserData?>?> {
//            override fun onResponse(
//                call: Call<ArrayList<UserData?>?>,
//                response: Response<ArrayList<UserData?>?>
//            ) {
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
//
//
//            }
//
//            override fun onFailure(call: Call<ArrayList<UserData?>?>, t: Throwable) {
//                Toast.makeText(this@SignInActivity, "error : " + t.message, Toast.LENGTH_SHORT).show()
//            }
//        })

    } // clickSignIn()

    // 로그인입력란에 저장된 회원정보로 설정하는 메소드
    fun loadData(){

        val pref = getSharedPreferences("account", MODE_PRIVATE)
        UserDatas.id = pref.getString("id", null)
        UserDatas.password = pref.getString("password", null)
        UserDatas.nickname = pref.getString("nickname", null)

        binding.etId.setText(UserDatas.id)
        binding.etPassword.setText(UserDatas.password)

    }

}




























