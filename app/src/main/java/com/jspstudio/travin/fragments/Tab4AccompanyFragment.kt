package com.jspstudio.travin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jspstudio.travin.R
import com.jspstudio.travin.adapters.Tab4AccompanyRecyclerAdapter
import com.jspstudio.travin.databinding.FragmentTab4AccompanyBinding
import com.jspstudio.travin.model.Tab4AccompanyItem


class Tab4AccompanyFragment : Fragment() {

    private var mBinding: FragmentTab4AccompanyBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    val recycler: RecyclerView by lazy { binding.tab4Recycler  }
    var items: MutableList<Tab4AccompanyItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTab4AccompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.adapter = Tab4AccompanyRecyclerAdapter(view.context, items)

        for(i in 0..20){ // 제어변수를 만드는 var키워드 없음
            items.add(Tab4AccompanyItem("질문이 있습니다. 제가 얼마전에 여행을 다녀왔는데 여행지에 대해 궁금한 것이 생겼습니다", R.drawable.test1, "jinsol", "1시간 전"))
        }
    }

}