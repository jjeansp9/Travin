package com.jspstudio.travin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jspstudio.travin.databinding.FragmentTab4AccompanyBinding
import com.jspstudio.travin.databinding.FragmentTab5ReviewBinding


class Tab5ReviewFragment : Fragment() {

    private var mBinding: FragmentTab5ReviewBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    val recycler: RecyclerView by lazy { binding.tab5Recycler  }
    var items: MutableList<Tab5ReviewItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTab5ReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = Tab5ReviewRecyclerAdapter(view.context, items)

        for(i in 0..20){ // 제어변수를 만드는 var키워드 없음
            items.add(Tab5ReviewItem("질문이 있습니다. 제가 얼마전에 여행을 다녀왔는데 여행지에 대해 궁금한 것이 생겼습니다", R.drawable.test2, "jinsol", "1시간 전"))
        }
    }


}