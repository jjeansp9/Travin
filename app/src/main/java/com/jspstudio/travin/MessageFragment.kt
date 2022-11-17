package com.jspstudio.travin

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.FragmentMessageBinding
import java.util.*

class MessageFragment : Fragment() {

    private var mBinding: FragmentMessageBinding? = null
    // 매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재 선언
    private val binding get() = mBinding!!

    var first:MutableList<Int> = mutableListOf(0)

    var items: MutableList<MessageRecyclerItem> = mutableListOf()
    var friendItems: MutableList<MessageFriendRecyclerItem> = mutableListOf()

    val listAdapter by lazy { context?.let { MessageRecyclerAdapter(it, items) } }
    val friendListAdapter by lazy { context?.let { MessageFriendRecyclerAdapter(it, friendItems) } }

    var chatRef : CollectionReference? = null
    var otherChatRef : CollectionReference? = null
    val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성

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

        binding.msgRecycler.adapter = listAdapter // 메세지 리스트
        binding.msgRecyclerFriend.adapter = friendListAdapter // 메세지화면 친구추천 리스트


        msgFriend() // 메세지 친구추천목록 메소드
        openMessageFriend() // 클릭한 친구 메세지창 오픈 메소드
        requestFriend() // 친구신청 메소드
        msgList() // 메세지목록 메소드
    }


    // 메세지한 기록 불러오기
    fun msgList(){
        // 내 닉네임 가져오기
        val pref = context?.getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
        val nickname = pref?.getString("nickname", null)

        // 메시지 상대방 닉네임 가져오기
        val otherPref = context?.getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
        val otherName = otherPref?.getString("nickname", null)

        chatRef = firebaseFirestore.collection(nickname + "," + "[msgList]") // 내 닉네임 + 상대방 닉네임의 컬렉션이름

        chatRef?.addSnapshotListener { value, error ->
            val documentChangeList : List<DocumentChange> = value!!.documentChanges
            for (documentChange : DocumentChange in documentChangeList){
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot : DocumentSnapshot = documentChange.document

                val buffer = StringBuffer()

                // document 안에 있는 필드 값들 얻어오기
                val chatUpload : Map<String, String> = snapshot.data as Map<String, String>
                val nickname = chatUpload["nickname"]
                val message = chatUpload["message"]
                val time = chatUpload["time"]


                val item : MessageRecyclerItem = MessageRecyclerItem(R.drawable.profile,"$nickname", "$message", "$time")
                items.add(item)



                // 채팅 리사이클러뷰 갱신
                binding.msgRecycler.adapter?.notifyItemInserted(items.size -1)
                binding.msgRecycler.scrollToPosition(binding.msgRecycler.adapter!!.itemCount -1)
            }
        }
    }

    // 친구추천 프로필목록
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

    // 친구신청 메소드
    fun requestFriend(){
        friendListAdapter?.setItemClickListener (object : MessageFriendRecyclerAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {


                val otherPref = view?.context?.getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
                val otherName = otherPref?.getString("nickname", null)


                val builder = view?.let {
                    AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                        .setTitle("${otherName}님에게 친구신청 하시겠습니까?")
                        .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                            val pref = view?.context?.getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
                            val nickname = pref?.getString("nickname", null)

                            // 파이어스토어에 친구신청 컬렉션만들고 저장하기
                            chatRef = firebaseFirestore.collection("$nickname[requestFriendsList]") // 내 닉네임 + 상대방 닉네임의 컬렉션이름
                            otherChatRef = firebaseFirestore.collection("$otherName[responseList]") // 상대방 + 내 닉네임의 컬렉션이름

                            val fileName:String = "$nickname" // 저장될 파일명 : 닉네임 + 날짜 + .png
                            val otherFileName:String = "$otherName" // 저장될 파일명 : 닉네임 + 날짜 + .png
                            val chat: MutableMap<String, String> = HashMap() // Object 사용하면 int string 다 가능. <식별자, 값>
                            val otherChat: MutableMap<String, String> = HashMap() // Object 사용하면 int string 다 가능. <식별자, 값>

                            chat["nickname"] = "$otherName"
                            otherChat["nickname"] = "$nickname"

                            chatRef?.document(otherFileName)?.set(chat)
                            otherChatRef?.document(fileName)?.set(otherChat)


                            Toast.makeText(context, "${otherName}님에게 친구신청을 하였습니다", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("취소",
                            DialogInterface.OnClickListener { dialog, which ->
                                Toast.makeText(context, "친구신청 취소", Toast.LENGTH_SHORT).show()
                            })
                }
                builder?.show()
            }
        })
    }

    // 클릭한 친구 메세지창 오픈
    fun openMessageFriend(){
        listAdapter?.setItemClickListener (object : MessageRecyclerAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int) {

                val pref = view?.context?.getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
                val editor = pref?.edit()
                editor?.putString("nickname", items[position].name)
                editor?.commit()

                val intent = Intent(context, MessageChattingActivity::class.java)
                startActivity(intent)
            }
        })
    }




}