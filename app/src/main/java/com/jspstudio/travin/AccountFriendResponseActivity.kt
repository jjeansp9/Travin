package com.jspstudio.travin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.ActivityAccountFriendResponseBinding
import java.util.HashMap


class AccountFriendResponseActivity : AppCompatActivity() {

    val binding: ActivityAccountFriendResponseBinding by lazy { ActivityAccountFriendResponseBinding.inflate(layoutInflater) }
    val listAdapter by lazy {  AccountFriendResponseRecyclerAdapter(this, items)  }

    var items : MutableList<AccountFriendResponseItem> = mutableListOf()

    val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성
    var requestRef : CollectionReference? = null
    var otherRequestRef : CollectionReference? = null

    var friendRef : CollectionReference? = null
    var otherFriendRef : CollectionReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.responseFriendRecycler.adapter = listAdapter // 메세지 리스트

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        loadRequestList()

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    // 나에게 친구요청한 유저들 불러오기
    fun loadRequestList(){

        val nickname = UserDatas.nickname.toString()
        requestRef = firebaseFirestore.collection("$nickname[responseList]") // 내 닉네임 + 상대방 닉네임의 컬렉션이름

        requestRef?.addSnapshotListener { value, error ->
            val documentChangeList : List<DocumentChange> = value!!.documentChanges
            for (documentChange : DocumentChange in documentChangeList){
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot : DocumentSnapshot = documentChange.document

                // document 안에 있는 필드 값들 얻어오기
                val chatUpload : Map<String, String> = snapshot.data as Map<String, String>
                val nickname = chatUpload["nickname"]

                val item : AccountFriendResponseItem = AccountFriendResponseItem(R.drawable.profile, nickname, 1, 0)

                items.add(item)

                // 채팅 리사이클러뷰 갱신
                binding.responseFriendRecycler.adapter?.notifyItemInserted(items.size -1)
                binding.responseFriendRecycler.scrollToPosition(binding.responseFriendRecycler.adapter!!.itemCount -1)

            }
        }
        loadRequestFriends() // 친구요청 목록의 리사이클러뷰(수락,거절,친구 요청인 프로필) 클릭
    }

    // 리사이클러뷰 아이템 클릭
    private fun loadRequestFriends(){
        listAdapter.setItemClickListener(object : AccountFriendResponseRecyclerAdapter.OnItemClickListener{

            // 리사이클러뷰 수락 클릭
            override fun onAllowClick(v: View, position: Int) {

                val nickname = UserDatas.nickname.toString() // 내이름 가져오기
                val otherName = "${items[position].responseName}" // 상대이름 가져오기

                // 친구요청목록 삭제
                requestRef?.firestore?.collection("$nickname[responseList]")?.document("$otherName")?.delete()
                // 친구요청목록 삭제
                requestRef?.firestore?.collection("$otherName[requestFriendsList]")?.document("$nickname")?.delete()

                friendRef = firebaseFirestore.collection("$nickname[friends]") // 내 닉네임 + 상대방 닉네임의 컬렉션이름
                otherFriendRef = firebaseFirestore.collection("$otherName[friends]") // 상대방 + 내 닉네임의 컬렉션이름

                val fileName:String = nickname // 저장될 파일명 : 닉네임 + 날짜 + .png
                val otherFileName:String = "$otherName" // 저장될 파일명 : 닉네임 + 날짜 + .png
                val friend: MutableMap<String, String> = HashMap() // Object 사용하면 int string 다 가능. <식별자, 값>
                val otherFriend: MutableMap<String, String> = HashMap() // Object 사용하면 int string 다 가능. <식별자, 값>

                friend["nickname"] = "$otherName"
                otherFriend["nickname"] = nickname

                friendRef?.document(otherFileName)?.set(friend)
                otherFriendRef?.document(fileName)?.set(otherFriend)

                Toast.makeText(this@AccountFriendResponseActivity, "${items[position].responseName}님의 친구요청을 수락했습니다.", Toast.LENGTH_SHORT).show()
            }

            // 리사이클러뷰 거절 클릭
            override fun onDenyClick(v: View, position: Int) {

                val nickname = UserDatas.nickname.toString() // 내이름 가져오기
                val otherName = "${items[position].responseName}" // 상대이름 가져오기

                // 친구요청목록 삭제
                requestRef?.firestore?.collection("$nickname[responseList]")?.document("$otherName")?.delete()
                // 친구요청목록 삭제
                requestRef?.firestore?.collection("$otherName[requestFriendsList]")?.document("$nickname")?.delete()

                Toast.makeText(this@AccountFriendResponseActivity, "${items[position].responseName}님의 친구요청을 거절했습니다.", Toast.LENGTH_SHORT).show()
            }

            // 리사이클러뷰 이름 클릭
            override fun onNameClick(v: View, position: Int) {
                // Todo 클릭한 유저 프로필화면 열기
            }
        }) // listAdapter.setItemClickListener()
    } // loadResponseFriends()



}