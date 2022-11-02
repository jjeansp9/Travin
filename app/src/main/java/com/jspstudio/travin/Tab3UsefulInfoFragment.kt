package com.jspstudio.travin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jspstudio.travin.databinding.FragmentTab2QuestionBinding
import com.jspstudio.travin.databinding.FragmentTab3UsefulInfoBinding


class Tab3UsefulInfoFragment : Fragment() {

    private var mBinding: FragmentTab3UsefulInfoBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    val recycler: RecyclerView by lazy { binding.tab3Recycler  }
    var items: MutableList<Tab3UsefulInfoItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTab3UsefulInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = Tab3UsefulInfoRecyclerAdapter(view.context, items)

        for (i in 0..20){
            items.add(Tab3UsefulInfoItem("안녕하세요 제가 이번에 가본 여행지에 대해 꿀팁을 전수해드리겠습니다.", R.drawable.sydney, "jinsol", "1시간 전"))
        }

    }

}