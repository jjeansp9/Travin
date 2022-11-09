package com.jspstudio.travin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.ActivityKeywordSelectBinding

// 키워드 선택화면 //

class KeywordSelectActivity : AppCompatActivity() {

    val binding:ActivityKeywordSelectBinding by lazy { ActivityKeywordSelectBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼 ( 로그인&회원가입 시작화면으로 이동 )
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // fab 버튼을 클릭하면 메인 홈화면으로 이동
        binding.fab.setOnClickListener{ nextActivity() }

        binding.imgProfile.setOnClickListener{ uploadProfile() }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 다음액티비티로 넘어가는 메소드
    fun nextActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    // 프로필이미지를 업로드하는 메소드
    fun uploadProfile(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)

    }

    // 사진 업로드 관련 코드
    var resultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_CANCELED) {
            val uri = result.data!!.data
            Glide.with(this@KeywordSelectActivity).load(uri).into(binding.imgProfile)
            // uri --> 절대주소로 변환하기 [ 단, 외부저장소에 대한 퍼미션 필요 ]
            val imgPath = getRealPathFromUri(uri)
        }
    }

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드 ( 사진관련 )
    fun getRealPathFromUri(uri: Uri?): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(
            this,
            uri!!, proj, null, null, null
        )
        val cursor = loader.loadInBackground()
        val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result = cursor.getString(column_index)
        cursor.close()
        return result
    }

}