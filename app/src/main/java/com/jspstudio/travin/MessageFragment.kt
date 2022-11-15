package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.FragmentMessageBinding
import java.text.SimpleDateFormat
import java.util.*

class MessageFragment : Fragment() {

    private var mBinding: FragmentMessageBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var items: MutableList<MessageRecyclerItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMessageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = MessageRecyclerAdapter(view.context, items)
        binding.msgRecycler.adapter = listAdapter

        dataTest()

        // 클릭한 친구 메세지창 오픈
        listAdapter.setItemClickListener (object : MessageRecyclerAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {

                val pref = view.context?.getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString("nickname", items[position].name)
                editor?.commit()

                val intent = Intent(context, MessageChattingActivity::class.java)
                startActivity(intent)

            }
        })

    }


    fun dataTest(){
        for(i in 0..10){
            items.add(MessageRecyclerItem(R.drawable.ic_profile, "홍길동", "안녕하세요 홍길동입니다. 만나서 반갑습니다 우리 같이 여행정보에 대해서 공유해요.", "오후 8:55"))
            items.add(MessageRecyclerItem(R.drawable.ic_profile, "김수현", "안녕하세요 홍길동입니다. 만나서 반갑습니다 우리 같이 여행정보에 대해서 공유해요.", "오후 8:55"))
        }

    }




}