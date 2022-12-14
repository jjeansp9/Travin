package com.jspstudio.travin.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

import com.bumptech.glide.Glide

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jspstudio.travin.databinding.ActivityUploadBinding
import com.jspstudio.travin.model.UploadDatas
import com.jspstudio.travin.model.UserDatas

import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

// 글 업로드 화면
class UploadActivity : AppCompatActivity() {

    val binding: ActivityUploadBinding by lazy { ActivityUploadBinding.inflate(layoutInflater) }

    lateinit var imgUri:Uri

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

        if(supportActionBar?.title == "새 게시물"){
            homeUpload() // 홈화면 업로드 메소드
        }else if (supportActionBar?.title == "여행 질문"){
            questionUpload() // 여행질문 글 업로드 메소드
        }else if (supportActionBar?.title == "여행 꿀팁"){
            usefulInfoUpload() // 여행꿀팁 글 업로드 메소드
        }else if (supportActionBar?.title == "여행 동행"){
            accompanyUpload() // 여행동행 글 업로드 메소드
        }else if(supportActionBar?.title == "여행 후기"){
            reviewUpload() // 여행후기 글 업로드 메소드
        }
        finish()
    }

    fun homeUpload(){

        val pref = getSharedPreferences("account", MODE_PRIVATE)
        UserDatas.nickname = pref.getString("nickname", null)

        val profileRef: SharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
        UserDatas.profileUrl = profileRef.getString("profileUrl", null)

        UploadDatas.uploadContents= binding.etContents.text.toString() // 홈화면 업로드글 내용

        val sdf:SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val fileName:String = sdf.format(Date()) + "_" + UserDatas.nickname + ".png" // 저장될 파일명 : 닉네임 + 날짜 + .png

        val firebaseStorage = FirebaseStorage.getInstance() // storage
        val uploadRef: StorageReference = firebaseStorage.getReference("homeUpload/$fileName") // 스토리지 컬렉션이름 : homeUpload

        uploadRef.putFile(imgUri).addOnSuccessListener {
            uploadRef.downloadUrl.addOnSuccessListener {
                UploadDatas.uploadImg = it.toString()



                val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance() // firestore
                val homeUploadRef = firebaseFireStore.collection("homeUploads") // 파이어스토어 컬렉션 생성 : homeUploads

                val calendar : Calendar = Calendar.getInstance() // 현재시간 객체
                val timeHour: String = calendar[Calendar.HOUR_OF_DAY].toString()
                val timeMinute: String = calendar[Calendar.MINUTE].toString()
                val timeSecond: String = calendar[Calendar.SECOND].toString()

                val homeUpload : MutableMap<String, String> = HashMap()
                homeUpload["homeUploadNickname"] = UserDatas.nickname!! // "필드명" = 업로드한 유저닉네임
                homeUpload["homeUploadImgUrl"] = UploadDatas.uploadImg!! // "필드명" = 업로드 이미지
                homeUpload["homeUploadContents"] = UploadDatas.uploadContents!! // "필드명" = 업로드 내용
                homeUpload["homeUploadTime"] = "$timeHour:$timeMinute:$timeSecond" // "필드명" = 업로드 타임
                homeUpload["homeUploadHour"] = timeHour // "필드명" = 업로드 시간
                homeUpload["homeUploadMinute"] = timeMinute // "필드명" = 업로드 분
                homeUpload["homeUploadSecond"] = timeSecond // "필드명" = 업로드 초
                homeUpload["homeUploadProfileUrl"] = UserDatas.profileUrl.toString()

                homeUploadRef.document( sdf.format(Date()) + "_" + UserDatas.nickname!! ).set(homeUpload) // 파일명 : 날짜 + 닉네임
                Toast.makeText(this, "업로드 성공", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun questionUpload(){
        val pref = getSharedPreferences("account", MODE_PRIVATE)
        UserDatas.nickname = pref.getString("nickname", null)

        UploadDatas.uploadContents= binding.etContents.text.toString() // 홈화면 업로드글 내용

        val sdf:SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val fileName:String = sdf.format(Date()) + "_" + UserDatas.nickname + ".png" // 저장될 파일명 : 닉네임 + 날짜 + .png

        val firebaseStorage = FirebaseStorage.getInstance() // storage
        val uploadRef: StorageReference = firebaseStorage.getReference("questionUpload/$fileName") // 컬렉션이름 : questionUpload

        uploadRef.putFile(imgUri).addOnSuccessListener {
            uploadRef.downloadUrl.addOnSuccessListener {
                UploadDatas.uploadImg = it.toString()

                val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance() // firestore
                val uploadRef = firebaseFireStore.collection("questionUploads") // 컬렉션 생성 : questionUploads

                val calendar : Calendar = Calendar.getInstance() // 현재시간 객체
                val timeHour: String = calendar[Calendar.HOUR_OF_DAY].toString()
                val timeMinute: String = calendar[Calendar.MINUTE].toString()
                val timeSecond: String = calendar[Calendar.SECOND].toString()

                val upload : MutableMap<String, String> = HashMap()
                upload["questionUploadNickname"] = UserDatas.nickname!! // "필드명" = 업로드한 유저닉네임
                upload["questionUploadImgUrl"] = UploadDatas.uploadImg!! // "필드명" = 업로드 이미지
                upload["questionUploadContents"] = UploadDatas.uploadContents!! // "필드명" = 업로드 내용
                upload["questionUploadTime"] = "$timeHour:$timeMinute:$timeSecond" // "필드명" = 업로드 타임
                upload["questionUploadHour"] = timeHour // "필드명" = 업로드 시간
                upload["questionUploadMinute"] = timeMinute // "필드명" = 업로드 분
                upload["questionUploadSecond"] = timeSecond // "필드명" = 업로드 초

                uploadRef.document( sdf.format(Date()) + "_" + UserDatas.nickname!! ).set(upload) // 파일명 : 날짜 + 닉네임
                Toast.makeText(this, "업로드 성공", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun usefulInfoUpload(){
        val pref = getSharedPreferences("account", MODE_PRIVATE)
        UserDatas.nickname = pref.getString("nickname", null)

        UploadDatas.uploadContents= binding.etContents.text.toString() // 홈화면 업로드글 내용

        val sdf:SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val fileName:String = sdf.format(Date()) + "_" + UserDatas.nickname + ".png" // 저장될 파일명 : 닉네임 + 날짜 + .png

        val firebaseStorage = FirebaseStorage.getInstance() // storage
        val uploadRef: StorageReference = firebaseStorage.getReference("questionUpload/$fileName") // 컬렉션이름 : questionUpload

        uploadRef.putFile(imgUri).addOnSuccessListener {
            uploadRef.downloadUrl.addOnSuccessListener {
                UploadDatas.uploadImg = it.toString()

                val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance() // firestore
                val uploadRef = firebaseFireStore.collection("usefulInfoUploads") // 컬렉션 생성 : questionUploads

                val calendar : Calendar = Calendar.getInstance() // 현재시간 객체
                val timeHour: String = calendar[Calendar.HOUR_OF_DAY].toString()
                val timeMinute: String = calendar[Calendar.MINUTE].toString()
                val timeSecond: String = calendar[Calendar.SECOND].toString()

                val upload : MutableMap<String, String> = HashMap()
                upload["usefulInfoUploadNickname"] = UserDatas.nickname!! // "필드명" = 업로드한 유저닉네임
                upload["usefulInfoUploadImgUrl"] = UploadDatas.uploadImg!! // "필드명" = 업로드 이미지
                upload["usefulInfoUploadContents"] = UploadDatas.uploadContents!! // "필드명" = 업로드 내용
                upload["usefulInfoUploadTime"] = "$timeHour:$timeMinute:$timeSecond" // "필드명" = 업로드 타임
                upload["usefulInfoUploadHour"] = timeHour // "필드명" = 업로드 시간
                upload["usefulInfoUploadMinute"] = timeMinute // "필드명" = 업로드 분
                upload["usefulInfoUploadSecond"] = timeSecond // "필드명" = 업로드 초

                uploadRef.document( sdf.format(Date()) + "_" + UserDatas.nickname!! ).set(upload) // 파일명 : 날짜 + 닉네임
                Toast.makeText(this, "업로드 성공", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun accompanyUpload(){

    }

    fun reviewUpload(){

    }


    // 사진 업로드 관련 코드
    var resultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_CANCELED) {
            imgUri = result.data!!.data!!
            Glide.with(this@UploadActivity).load(imgUri).into(binding.imgUpload)
        }
    }



}