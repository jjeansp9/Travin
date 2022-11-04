package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jspstudio.travin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var popularItems: MutableList<HomePopularItem> = mutableListOf() // 오늘의 인기글 데이터
    var items: MutableList<HomeItem> = mutableListOf() // 홈화면 업로드한 글 데이터

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homePopularRecycler.adapter = HomePopularRecyclerAdapter(view.context, popularItems) // 홈화면 오늘의 인기글 어댑터연결
        binding.homeRecycler.adapter = HomeRecyclerAdapter(view.context,items) // 홈화면 업로드 글 어댑터 연결

        dummyData() // 리사이클러뷰 테스트용 데이터

        // 플로팅버튼 클릭하면 새 게시물 업로드화면 열림
        binding.fabHomeAddWrite.setOnClickListener { clickFabTabMenu(0) }

    }

    fun clickFabTabMenu(category:Int){
        val intent = Intent(context, UploadActivity::class.java)
        intent.putExtra("tabNumber", category)
        startActivity(intent)
    }

    // 리사이클러뷰 테스트목적 더미데이터
    fun dummyData(){
        for(i in 0..20) popularItems.add(HomePopularItem(R.drawable.newyork))


        for(i in 0..20){
            items.add(HomeItem(R.drawable.ic_profile,
                "홍길동",
                "서울특별시 서초구",
                "1시간 전",
                R.drawable.paris,
                R.drawable.ic_favorite,
                R.drawable.ic_comment,
                "안녕하세요 이번에 가본 곳은 프랑스 파리입니다. 에펠탑이 보이는 것이 정말 아름답네요. 다음에 또 오고 싶은 곳입니다."))
        }

    }

}
