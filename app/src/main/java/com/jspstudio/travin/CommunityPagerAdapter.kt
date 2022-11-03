package com.jspstudio.travin

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CommunityPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private var fragments = arrayOfNulls<Fragment>(5)


    override fun createFragment(position: Int): Fragment {
        return fragments[position]!!
    }

    override fun getItemCount(): Int {
        return fragments.size
    }


    init {
        fragments[0] = Tab1CommunityFragment()
        fragments[1] = Tab2QuestionFragment()
        fragments[2] = Tab3UsefulInfoFragment()
        fragments[3] = Tab4AccompanyFragment()
        fragments[4] = Tab5ReviewFragment()
    }

}