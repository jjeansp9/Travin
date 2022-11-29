package com.jspstudio.travin.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.adapters.AccountFriendListRcyclerAdapter
import com.jspstudio.travin.databinding.ActivityAccountFriendListBinding
import com.jspstudio.travin.model.AccountFriendListItem

class AccountFriendListActivity : AppCompatActivity() {

    val binding: ActivityAccountFriendListBinding by lazy { ActivityAccountFriendListBinding.inflate(layoutInflater) }
    val listAdapter by lazy {  AccountFriendListRcyclerAdapter(this, items)  }

    var items : MutableList<AccountFriendListItem> = mutableListOf()

    val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성
    var friendListRef : CollectionReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.friendListRecycler.adapter = listAdapter // 메세지 리스트

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "내 친구목록"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.responseFriendList.setOnClickListener{click() }
        loadFriendList() // 친구목록 데이터 불러오기
        clickFriendList() //

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 친구요청 목록 보기
    fun click(){
        startActivity(Intent(this, AccountFriendResponseActivity::class.java))
    }

    // 친구 목록 데이터 불러오기
    fun loadFriendList(){
        // 내 닉네임 가져오기
        val pref = getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
        val nickname = pref?.getString("nickname", null)

        friendListRef = firebaseFirestore.collection("[friends]$nickname") // 내 닉네임 + 상대방 닉네임의 컬렉션이름

        friendListRef?.addSnapshotListener { value, error ->
            val documentChangeList: List<DocumentChange> = value!!.documentChanges
            for (documentChange: DocumentChange in documentChangeList) {
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot: DocumentSnapshot = documentChange.document

                // document 안에 있는 필드 값들 얻어오기
                val chatUpload: Map<String, String> = snapshot.data as Map<String, String>
                val nickname = chatUpload["nickname"]

                // 데이터 추가
                val item: AccountFriendListItem = AccountFriendListItem(R.drawable.profile, nickname.toString())
                items.add(item)
                binding.tvMyFriendsNumber.text = items.size.toString()

                // 리사이클러뷰 갱신
                binding.friendListRecycler.adapter?.notifyItemInserted(items.size - 1)
            }
        }
    }

    fun clickFriendList(){
        listAdapter?.setItemClickListener (object : AccountFriendListRcyclerAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {

                val pref = getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString("nickname", items[position].name)
                editor?.commit()

                val intent = Intent(this@AccountFriendListActivity, MessageChattingActivity::class.java)
                startActivity(intent)
            }
        })
    }
}






























