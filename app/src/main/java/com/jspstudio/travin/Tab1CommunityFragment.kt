package com.jspstudio.travin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jspstudio.travin.databinding.FragmentTab1CommunityBinding
import com.jspstudio.travin.databinding.FragmentTab2QuestionBinding

class Tab1CommunityFragment : Fragment() {

    private var mBinding: FragmentTab1CommunityBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    val recycler: RecyclerView by lazy { binding.tab1Recycler}
    val recycler2: RecyclerView by lazy { binding.tab2Recycler}
    val recycler3: RecyclerView by lazy { binding.tab3Recycler}
    val recycler4: RecyclerView by lazy { binding.tab4Recycler}
    val recycler5: RecyclerView by lazy { binding.tab5Recycler}

    var items: MutableList<Tab1CommunityItem> = mutableListOf()
    var items2: MutableList<Tab2QuestionItem> = mutableListOf()
    var items3: MutableList<Tab3UsefulInfoItem> = mutableListOf()
    var items4: MutableList<Tab4AccompanyItem> = mutableListOf()
    var items5: MutableList<Tab5ReviewItem> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTab1CommunityBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewConnect() // 리사이클러뷰 아답터 연결
        dummyData() // 테스트용 데이터

    }

    // 테스트용 데이터
    fun dummyData(){
        for(i in 0..30){ // 제어변수를 만드는 var키워드 없음
            items.add(Tab1CommunityItem(R.drawable.profile))
            items2.add(Tab2QuestionItem("안녕하세요 제가 이번에 가본 여행지에 대해 꿀팁을 전수해드리겠습니다.", R.drawable.sydney, "jinsol", "1시간 전"))
            items3.add(Tab3UsefulInfoItem("안녕하세요 제가 이번에 가본 여행지에 대해 꿀팁을 전수해드리겠습니다.", R.drawable.sydney, "jinsol", "1시간 전"))
            items4.add(Tab4AccompanyItem("질문이 있습니다. 제가 얼마전에 여행을 다녀왔는데 여행지에 대해 궁금한 것이 생겼습니다", R.drawable.test1, "jinsol", "1시간 전"))
            items5.add(Tab5ReviewItem("질문이 있습니다. 제가 얼마전에 여행을 다녀왔는데 여행지에 대해 궁금한 것이 생겼습니다", R.drawable.test1, "jinsol", "1시간 전"))
        }
    }

    fun viewConnect(){
        recycler.adapter = context?.let { Tab1CommunityRecyclerAdapter(it, items) }
        recycler2.adapter = context?.let { Tab2QuestionRecyclerAdapter(it, items2) }
        recycler3.adapter = context?.let { Tab3UsefulInfoRecyclerAdapter(it, items3) }
        recycler4.adapter = context?.let { Tab4AccompanyRecyclerAdapter(it, items4) }
        recycler5.adapter = context?.let { Tab5ReviewRecyclerAdapter(it, items5) }
    }



}



















