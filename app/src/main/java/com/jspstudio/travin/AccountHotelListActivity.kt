package com.jspstudio.travin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jspstudio.travin.databinding.ActivityAccountHotelListBinding

class AccountHotelListActivity : AppCompatActivity() {

    val binding: ActivityAccountHotelListBinding by lazy { ActivityAccountHotelListBinding.inflate(layoutInflater) }
    var hotelItems: MutableList<AccountHotelItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val listAdapter = AccountHotelRecyclerAdapter(this, hotelItems)
        binding.hotelListRecycler.adapter = listAdapter // 호텔리스트 리사이클러뷰에 아답터연결

        binding.hotelListRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL)) // 리사이클러뷰 구분선

        // 호텔리스트 인덱스 클릭반응
        listAdapter.setItemClickListener(object : AccountHotelRecyclerAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {
                Toast.makeText(this@AccountHotelListActivity, "${hotelItems[position].hotelName}", Toast.LENGTH_SHORT).show()
            }
        })

        testData() // 호텔데이터 리사이클러뷰 작동하는지 테스트하는 메소드

    }

    fun testData(){
        for (i in 0..10){
            hotelItems.add(AccountHotelItem("호텔이름", "호텔 소개", "2000원", R.drawable.newyork))
            hotelItems.add(AccountHotelItem("호텔", "호텔 소개", "2000원", R.drawable.paris))
            hotelItems.add(AccountHotelItem("숙소", "호텔 소개", "2000원", R.drawable.sydney))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 오픈 API를 통해 호텔리스트 데이터들 불러오기
    fun loadHotelList(){



    }

}