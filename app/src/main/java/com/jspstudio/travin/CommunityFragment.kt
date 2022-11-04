package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jspstudio.travin.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    private var mBinding: FragmentCommunityBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var adapter : CommunityPagerAdapter? = null

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

        // 뷰페이저2 아답터 연결
        adapter= activity?.let { CommunityPagerAdapter(it) }
        binding.pager.adapter = adapter

        //탭레이아웃과 뷰페이저 연동해주는 중재자(Mediator)
        val mediator = TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position -> tab.text = tabMenu[position] }
        mediator.attach()

        binding.fabCommunityAddWrite.setOnClickListener{
            val popupMenu:PopupMenu = PopupMenu(view.context, binding.fabCommunityAddWrite)
            MenuInflater(view.context).inflate(R.menu.popup_community, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.menu_question_upload -> activity?.let { clickFabTabMenu(0) }
                    R.id.menu_useful_info_upload -> activity?.let { clickFabTabMenu(1) }
                    R.id.menu_accompany_upload -> activity?.let { clickFabTabMenu(2) }
                    R.id.menu_review_upload -> activity?.let { clickFabTabMenu(3) }


                    else->{}

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

    }

    fun clickFabTabMenu(category:Int){
        val intent = Intent(context, UploadCommunityActivity::class.java)
        intent.putExtra("tabNumber", category)
        startActivity(intent)
    }




}
