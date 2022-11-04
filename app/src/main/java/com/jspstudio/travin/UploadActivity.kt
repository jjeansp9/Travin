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
import com.jspstudio.travin.databinding.ActivityUploadBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

// 글 업로드 화면
class UploadActivity : AppCompatActivity() {

    val binding: ActivityUploadBinding by lazy { ActivityUploadBinding.inflate(layoutInflater) }

    var tabNum:Int? = null
    val tabMenu = arrayOf("새 게시물", "여행 질문", "여행 꿀팁", "여행 동행", "여행 후기 ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        uploadName() // 액션바에 탭이름 표시하는 메소드
        binding.imgUpload.setOnClickListener { clickImageSelect() } // 사진 클릭시 사진선택화면 이동 및 선택
        binding.uploadComplete.setOnClickListener { clickUploadComplete() } // 완료 클릭시 글작성 완료되어 업로드됨
    }

    // 제목줄의 뒤로가기버튼(업버튼)을 클릭했을 때 자동실행되는 콜백메소드
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 다른 액티비티에서 전달받은 데이터 받아오고 액션바에 탭이름 표시하는 메소드
    fun uploadName(){
        val intent = intent
        tabNum = intent.getIntExtra("tabNumber", 0)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 글쓰기 탭 선택한 것에 따라 업로드 탭 표시
        supportActionBar?.title = tabMenu[tabNum!!]
    }

    // 사진앱 실행하여 선택하는 메소드
    fun clickImageSelect(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    // 글작성 완료(업로드) 메소드
    fun clickUploadComplete(){

        val contents:String = binding.etContents.text.toString() // 글 내용




    }


    // 사진 업로드 관련 코드
    var resultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_CANCELED) {
            val uri = result.data!!.data
            Glide.with(this@UploadActivity).load(uri).into(binding.imgUpload)
            // uri --> 절대주소로 변환하기 [ 단, 외부저장소에 대한 퍼미션 필요 ]
            val imgPath = getRealPathFromUri(uri)
        }
    }

    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
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