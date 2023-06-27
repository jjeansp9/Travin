package com.jspstudio.travin.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.ActivityLoginBinding
import com.jspstudio.travin.model.NidUserInfoResponse
import com.jspstudio.travin.network.RetrofitHelper
import com.jspstudio.travin.network.RetrofitSignUpService
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// 로그인 & 회원가입 시작 화면 //

class LoginActivity : AppCompatActivity() {

    lateinit var btn: Button

    val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.kakaoLogin.setOnClickListener{kakaoLogin()} // 카카오 계정으로 로그인
        binding.naverLogin.setOnClickListener { naverLogin() } // 네이버 계정으로 로그인
        binding.googleLogin.setOnClickListener { googleLogin() } // 구글 계정으로 로그인

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

        // 카카오 SDK용 키해시 값 얻어오기
        var keyHash:String= Utility.getKeyHash(this)
        Log.i("keyHash", keyHash)
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
                        var nickname:String = user.kakaoAccount?.profile?.nickname ?: "" // 혹시 이메일이 없다면, 이메일의 기본값을 ""로

                        Toast.makeText(this, "사용자 닉네임 : $nickname", Toast.LENGTH_SHORT).show()

                        // SharedPreferences 에 저장
                        val pref = getSharedPreferences("account", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("nickname", nickname)
                        editor.commit()

                        // firebase db에 저장하기 위해 Map Collection으로 묶어서 저장
                        val firebaseFirestore = FirebaseFirestore.getInstance()

                        val userRef: CollectionReference = firebaseFirestore.collection("users") // 컬렉션명 : users

                        val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmSS")

                        // Document 명을 닉네임으로, Field'값'에 이미지경로 url을 저장
                        val profile: MutableMap<String, String> = HashMap()
                        profile["nickname"] = nickname
                        profile["profile"] = ""
                        profile["startTime"] = sdf.format(Date())

                        userRef.document(nickname).set(profile)

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

    private fun naverLogin(){
        // 사용자정보를 취득하는 토큰값을 발급받아 REST API 방식으로 사용자정보 취득
        // 네이버 개발자 센터 가이드 문서 참고 - 애플리케이션 등록 완료

        // 네아로(네이버 아이디 로그인) SDK 초기화
        NaverIdLoginSDK.initialize(this, "Phd4fO7whvOewufQLFfo", "4G8KnmugN5", "Travin")

        // 네아로 전용버튼 뷰 사용 대신에 직접 로그인 요청 메소드를 사용
        NaverIdLoginSDK.authenticate(this, object : OAuthLoginCallback {
            override fun onSuccess() {
                Toast.makeText(this@LoginActivity, "네이버로그인 성공", Toast.LENGTH_SHORT).show()

                // 사용자 정보를 가져오려면 서버와 HTTP REST API 통신을 해야함
                // 단, 필요한 요청파라미터가 있음. 사용자정보에 접속할 수 있는 인증키 같은 값 - 토큰이라고 부름
                val accessToken : String? = NaverIdLoginSDK.getAccessToken()

                // 토큰값을 확인해보기 - 토큰값은 그때그때마다 갱신됨 [ 즉, 1회용 접속키임 ]
                Log.i("token", accessToken.toString())

                // Retrofit 작업을 통해 사용자 정보 가져오기
                val retrofit= RetrofitHelper.getRetrofitInstance("https://openapi.naver.com")
                retrofit.create(RetrofitSignUpService::class.java).getNidUser("Bearer $accessToken").enqueue(object :
                    Callback<NidUserInfoResponse> {
                    override fun onResponse(
                        call: Call<NidUserInfoResponse>,
                        response: Response<NidUserInfoResponse>
                    ) {
                        val userInfo:NidUserInfoResponse? = response.body()
                        var id:String= userInfo?.response?.id ?: ""
                        val email:String= userInfo?.response?.email ?: ""

                        Toast.makeText(this@LoginActivity, "$id", Toast.LENGTH_SHORT).show()

                        // SharedPreferences 에 저장
                        val pref = getSharedPreferences("account", MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putString("nickname", id)
                        editor.commit()

                        // main 화면으로 이동.
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onFailure(call: Call<NidUserInfoResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "회원정보 불러오기 실패 : ${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })
            }
            override fun onFailure(httpStatus: Int, message: String) {
                Toast.makeText(this@LoginActivity, "로그인 실패 : $message", Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                Toast.makeText(this@LoginActivity, "error : $message", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun googleLogin(){

        // 구글 로그인 옵션객체 생성 - Builder 이용
        val signInOptions: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .build()

        val intent:Intent= GoogleSignIn.getClient(this, signInOptions).signInIntent

        // 로그인 결과를 받기위해 액티비티를 실행
        googleResultLauncher.launch(intent)
    }

    // 구글 로그인 화면액티비티를 실행시키고 그 결과를 되돌려받는 작업을 관리하는 객체 생성.
    val googleResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object : ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            // 로그인 결과를 가져온 Intent 객체 소환
            val intent: Intent? = result?.data
            // 돌아온 Intent 객체에게 구글 계정정보 빼오기.
            val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)

            val account:GoogleSignInAccount = task.result
            var id:String= account.id.toString()

            Toast.makeText(this@LoginActivity, "구글 로그인 성공", Toast.LENGTH_SHORT).show()
            // SharedPreferences 에 저장
            val pref = getSharedPreferences("account", MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("nickname", id)
            editor.commit()

            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            finish()
        }

    })

}




















