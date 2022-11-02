package com.jspstudio.travin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.jspstudio.travin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var com : Boolean = false
    var msg : Boolean = false
    var pro : Boolean = false
    var frags : MutableList<Boolean>? = null

    private var fragments : MutableList<Fragment> = mutableListOf()
    var fragmentManager: FragmentManager? = null

    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createBNV()
    }

    // BNV 생성함수
    fun createBNV(){

        fragments.add(0,HomeFragment()) // 처음에 보여질 fragment 추가
        for(i in 1..3) fragments.add(i, Fragment())

        //프레그먼트 관리자 객체 소환
        fragmentManager = supportFragmentManager
        fragmentManager!!.beginTransaction()?.add(R.id.fragment_container, fragments[0]).commit()

        binding.bnv.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            var tran : FragmentTransaction = fragmentManager!!.beginTransaction()

            for(i in 0..3) tran.hide(fragments[i])

            when (it.itemId) {

                R.id.menu_bnv_home -> tran.show(fragments[0])

                R.id.menu_bnv_community -> {
                    if ( !com){
                        fragments[1] = CommunityFragment()
                        tran.add(R.id.fragment_container,fragments[1])
                        com = true
                    }
                    tran.show(fragments[1])
                }

                R.id.menu_bnv_message -> {
                    if (!msg){
                        fragments[2] = MessageFragment()
                        tran.add(R.id.fragment_container, fragments[2])
                        msg = true
                    }
                    tran.show(fragments[2])

                }

                R.id.menu_bnv_profile -> {
                    if (!pro){
                        fragments[3] = AccountFragment()
                        tran.add(R.id.fragment_container, fragments[3])
                        pro = true
                    }
                    tran.show(fragments[3])

                }
            }

            tran.commit()

            return@OnItemSelectedListener true
        })
    }

}




























