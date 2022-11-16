package com.jspstudio.travin

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.FragmentMessageBinding

class MessageFragment : Fragment() {

    private var mBinding: FragmentMessageBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var items: MutableList<MessageRecyclerItem> = mutableListOf()
    var friendItems: MutableList<MessageFriendRecyclerItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMessageBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = MessageRecyclerAdapter(view.context, items)
        val friendListAdapter = MessageFriendRecyclerAdapter(view.context, friendItems)
        binding.msgRecycler.adapter = listAdapter // 메세지 리스트
        binding.msgRecyclerFriend.adapter = friendListAdapter // 메세지화면 친구추천 리스트

        dataTest() // 메세지목록 메소드
        msgFriend() // 메세지 친구추천목록 메소드

        // 클릭한 친구 메세지창 오픈
        listAdapter.setItemClickListener (object : MessageRecyclerAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {

                val pref = view.context?.getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString("nickname", items[position].name)
                editor?.commit()

                val intent = Intent(context, MessageChattingActivity::class.java)
                startActivity(intent)

            }
        })

        // 친구추천 목록에 있는 프로필리스트들을 클릭시 친구신청 다이얼로그 오픈
        friendListAdapter.setItemClickListener (object : MessageFriendRecyclerAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {

                val pref = view.context?.getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString("nickname", friendItems[position].name)
                editor?.commit()

                val otherName = pref?.getString("nickname", null)

                val builder = AlertDialog.Builder(view.context)
                    .setTitle("${otherName}님에게 친구신청 하시겠습니까?")
                    .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        Toast.makeText(context, "${otherName}님에게 친구신청을 하였습니다", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("취소",
                        DialogInterface.OnClickListener { dialog, which ->
                            Toast.makeText(context, "친구신청 취소", Toast.LENGTH_SHORT).show()
                        })
                builder.show()
            }
        })

    }


    fun dataTest(){
        for(i in 0..10){
            items.add(MessageRecyclerItem(R.drawable.profile, "홍길동", "안녕하세요 홍길동입니다. 만나서 반갑습니다 우리 같이 여행정보에 대해서 공유해요.", "오후 8:55"))
            items.add(MessageRecyclerItem(R.drawable.profile, "김수현", "안녕하세요 홍길동입니다. 만나서 반갑습니다 우리 같이 여행정보에 대해서 공유해요.", "오후 8:55"))
        }

    }

    // 친구추천 프로필목록 더미데이터
   fun msgFriend(){

        val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성
        val homeUploadRef = firebaseFirestore.collection("users") // firestore에 있는 homeUploads 라는 이름의 컬렉션을 참조

        homeUploadRef.addSnapshotListener { value, error ->
            val documentChangeList: List<DocumentChange> = value!!.documentChanges
            for (documentChange: DocumentChange in documentChangeList) {
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot: DocumentSnapshot = documentChange.document

                // document 안에 있는 필드 값들 얻어오기
                val homeUpload: Map<String, String> = snapshot.data as Map<String, String>
                val profile = homeUpload["profile"]
                val nickName = homeUpload["nickname"]

                // 홈 업로드 글 데이터 추가
                val item : MessageFriendRecyclerItem = MessageFriendRecyclerItem(R.drawable.profile, nickName)

                friendItems.add(item)

                // 오늘의인기글 홈 리사이클러뷰 갱신
                binding.msgRecyclerFriend.adapter?.notifyItemInserted(friendItems.size -1)

            }
        }

    }




}