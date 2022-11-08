package com.jspstudio.travin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jspstudio.travin.databinding.ActivitySignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

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

    fun clickSignIn(){
        val retrofit: Retrofit = RetrofitSignUpHelper().getRetrofitInstance()
        val retrofitSignUpService = retrofit.create(RetrofitSignUpService::class.java)

        val call: Call<ArrayList<UserData?>?>? = retrofitSignUpService.loadUserDataFromServer()
        call!!.enqueue(object : Callback<ArrayList<UserData?>?> {
            override fun onResponse(
                call: Call<ArrayList<UserData?>?>,
                response: Response<ArrayList<UserData?>?>) {

                // 결과 json array를 곧바로 파싱하여 Arraylist<MarketItem>로 변환된 리스트 받기
                val items: ArrayList<UserData?>? = response.body()

                val buffer = StringBuffer()
                for (item in items!!) {
                    buffer.append(item?.id )
                }
                binding.tv.text = buffer.toString()

            }

            override fun onFailure(call: Call<ArrayList<UserData?>?>, t: Throwable) {
                Toast.makeText(this@SignInActivity, "error : " + t.message, Toast.LENGTH_SHORT).show()
            }
        })

    } // clickSignIn()

}
































