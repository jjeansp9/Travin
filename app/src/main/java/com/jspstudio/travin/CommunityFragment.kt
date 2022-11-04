package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.jspstudio.travin.databinding.FragmentCommunityBinding

class CommunityFragment : Fragment() {

    val tabMenu = arrayOf(" 커뮤니티", "여행 질문", "여행 꿀팁", "여행 동행", "여행 후기 ") // 탭메뉴 이름

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰페이저2 아답터 연결
        adapter= activity?.let { CommunityPagerAdapter(it) }
        binding.pager.adapter = adapter

        //탭레이아웃과 뷰페이저 연동해주는 중재자(Mediator)
        val mediator = TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position -> tab.text = tabMenu[position] }
        mediator.attach()

        // 플로팅버튼 클릭하면 업로드할 탭 메뉴 팝업창 띄움
        clickFabButton()

    }

    // 플로팅버튼 클릭시 업로드화면으로 전환하는 메소드
    fun clickFabButton(){
        binding.fabCommunityAddWrite.setOnClickListener{
            val popupMenu:PopupMenu = PopupMenu(view?.context, binding.fabCommunityAddWrite)
            MenuInflater(view?.context).inflate(R.menu.popup_community, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {

                when(it.itemId){
                    R.id.menu_question_upload -> activity?.let { clickFabTabMenu(1) } // 클릭시 여행질문 업로드화면으로 전환
                    R.id.menu_useful_info_upload -> activity?.let { clickFabTabMenu(2) } // 클릭시 여행꿀팁 업로드화면으로 전환
                    R.id.menu_accompany_upload -> activity?.let { clickFabTabMenu(3) } // 클릭시 여행동행 업로드화면으로 전환
                    R.id.menu_review_upload -> activity?.let { clickFabTabMenu(4) } // 클릭시 여행후기 업로드화면으로 전환


                    else->{}

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }
    }

    // 업로그화면으로 화면전환 및 탭메뉴 이름데이터 업로드화면으로 넘겨주는 메소드
    fun clickFabTabMenu(category:Int){
        val intent = Intent(context, UploadActivity::class.java)
        intent.putExtra("tabNumber", category)
        startActivity(intent)
    }




}
