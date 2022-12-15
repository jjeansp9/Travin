package com.jspstudio.travin.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jspstudio.travin.databinding.ActivityUploadProfileBinding
import com.jspstudio.travin.model.UserDatas
import java.text.SimpleDateFormat
import java.util.*

class UploadProfileActivity : AppCompatActivity() {

    val binding:ActivityUploadProfileBinding by lazy { ActivityUploadProfileBinding.inflate(layoutInflater) }

    lateinit var imgUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "프로필사진 수정"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.accountProfile.setOnClickListener{uploadProfile()}
        binding.profileUploadComplete.setOnClickListener{completeUploadProfile()}
    }

    // 프로필이미지를 업로드하는 메소드
    private fun uploadProfile(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    // 사진 업로드 관련 코드
    var resultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != AppCompatActivity.RESULT_CANCELED) {
            imgUri = result.data!!.data!!
            Glide.with(this).load(imgUri).into(binding.accountProfile)
        }
    }

    fun completeUploadProfile(){
        val pref = getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
        UserDatas.nickname = pref?.getString("nickname", null)

        val firebaseStorage = FirebaseStorage.getInstance() // storage 객체생성

        // getReference를 사용하여 profileImg의 폴더를 만들고 유저닉네임이름으로 문서를 만들고 사진을 저장합니다
        val profileRef: StorageReference = firebaseStorage.getReference("profileImg/${UserDatas.nickname.toString()}")

        profileRef.putFile(imgUri).addOnSuccessListener {
            profileRef.downloadUrl.addOnSuccessListener {
                UserDatas.profileUrl = it.toString()

                val firebaseFireStore: FirebaseFirestore = FirebaseFirestore.getInstance() // firestore
                val profileRef = firebaseFireStore.collection("users") // 파이어스토어 컬렉션 : users

                val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmSS")
                // Document 명을 닉네임으로, Field'값'에 이미지경로 url을 저장
                val profile: MutableMap<String, String> = HashMap()
                profile["nickname"] = UserDatas.nickname.toString()
                profile["id"] = UserDatas.id.toString()
                profile["password"] = UserDatas.password.toString()
                profile["startTime"] = sdf.format(Date())
                profile["profile"] = UserDatas.profileUrl.toString()

                profileRef.document(UserDatas.id.toString()).set(profile)

                val pref = getSharedPreferences("account", MODE_PRIVATE)
                val editor = pref.edit()
                editor.putString("profileUrl", UserDatas.profileUrl)

                editor.commit()

                finish()
                Toast.makeText(this, "프로필사진을 변경하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}