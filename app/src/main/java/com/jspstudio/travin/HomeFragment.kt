package com.jspstudio.travin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.jspstudio.travin.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    val recyclerPopular: RecyclerView by lazy { binding.homePopularRecycler }
    val recycler: RecyclerView by lazy { binding.homeRecycler }

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

        recyclerPopular.adapter = HomePopularRecyclerAdapter(view.context, popularItems)

        recycler.adapter = HomeRecyclerAdapter(view.context, items)

        for(i in 0..4){
            popularItems.add(HomePopularItem(R.drawable.newyork))
        }

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
