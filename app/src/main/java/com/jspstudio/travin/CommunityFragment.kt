package com.jspstudio.travin

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayoutMediator
import com.jspstudio.travin.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    private var mBinding: FragmentCommunityBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var adapter : CommunityPagerAdapter? = null

    private lateinit var resultText: TextView
    private lateinit var alertBtn: Button
    private lateinit var listBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    val tabMenu = arrayOf(" 커뮤니티", "여행 질문", "여행 꿀팁", "여행 동행", "여행 후기 ")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        adapter= activity?.let { CommunityPagerAdapter(it) }
        binding.pager.adapter = adapter

        //탭레이아웃과 뷰페이저 연동해주는 중재자(Mediator)
        val mediator = TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position -> tab.text = tabMenu[position] }
        mediator.attach()



    }




}
