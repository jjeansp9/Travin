package com.jspstudio.travin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.jspstudio.travin.databinding.FragmentAccountBinding
import com.jspstudio.travin.databinding.FragmentCommunityBinding

class AccountFragment : Fragment() {

    lateinit var binding:FragmentAccountBinding
    lateinit var imgUri:Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.accountProfile.setOnClickListener{uploadProfile()}

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
        if (result.resultCode != AppCompatActivity.RESULT_CANCELED) {
            imgUri = result.data!!.data!!
            Glide.with(this@AccountFragment).load(imgUri).into(binding.accountProfile)
        }
    }
}