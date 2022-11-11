package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private var mBinding: FragmentMessageBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    val recycler:RecyclerView by lazy { binding.msgRecycler }
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

        recycler.adapter = MessageRecyclerAdapter(view.context, items)

        binding.btnTest.setOnClickListener { clickTest() }

        dataTest()

    }

    fun dataTest(){
        for(i in 0..20){
            items.add(MessageRecyclerItem(R.drawable.ic_profile, "홍길동", "안녕하세요 홍길동입니다. 만나서 반갑습니다 우리 같이 여행정보에 대해서 공유해요.", "오후 8:55"))
        }
    }

    fun clickTest(){
        startActivity(Intent(context, MessageChattingActivity::class.java))
    }

}