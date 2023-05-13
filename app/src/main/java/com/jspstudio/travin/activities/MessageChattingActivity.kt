package com.jspstudio.travin.activities

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.adapters.MessageChattingRecyclerAdapter
import com.jspstudio.travin.databinding.ActivityMessageChattingBinding
import com.jspstudio.travin.model.MessageChattingRecyclerItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class MessageChattingActivity : AppCompatActivity() {

    val binding: ActivityMessageChattingBinding by lazy { ActivityMessageChattingBinding.inflate(layoutInflater) }

    var items : MutableList<MessageChattingRecyclerItem> = mutableListOf()

    val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성.
    var chatRef : CollectionReference? = null
    var otherChatRef : CollectionReference? = null
    var num: Int= 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.msgChatRecycler.adapter = MessageChattingRecyclerAdapter(this, items)

        actionBar()
        loadDatafromFirebase() // 파이어베이스 데이터 불러오기

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun actionBar(){
        // 메시지 상대방 닉네임 가져오기
        val pref = getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
        val otherName = pref.getString("nickname", null)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = otherName // 메세지 상대 닉네임.
    }



    // 파이어베이스 데이터 불러오기
    fun loadDatafromFirebase(){

        // 내 닉네임 가져오기
        val pref = getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
        val userName = pref?.getString("nickname", null)

        // 메시지 상대방 닉네임 가져오기
        val otherPref = getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
        val otherName = otherPref.getString("nickname", null)

        chatRef = firebaseFirestore.collection(userName + "," + otherName) // 내 닉네임 + 상대방 닉네임의 컬렉션이름

        chatRef?.addSnapshotListener { value, error ->
            val documentChangeList : List<DocumentChange> = value!!.documentChanges
            for (documentChange : DocumentChange in documentChangeList){
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot : DocumentSnapshot = documentChange.document

                // document 안에 있는 필드 값들 얻어오기
                val chatUpload : Map<String, String> = snapshot.data as Map<String, String>
                val nickname = chatUpload["nickname"]
                val message = chatUpload["message"]
                val time = chatUpload["time"]

                // 홈 업로드 글 데이터 추가

                if ("$userName"==nickname || "$otherName" == nickname){
                    val item : MessageChattingRecyclerItem = MessageChattingRecyclerItem(nickname, message, time)
                    items.add(item)
                }

                // 채팅 리사이클러뷰 갱신
                binding.msgChatRecycler.adapter?.notifyItemInserted(items.size -1)
                binding.msgChatRecycler.scrollToPosition(binding.msgChatRecycler.adapter!!.itemCount -1)
            }
        }
        binding.msgChatSend.setOnClickListener{clickSend()}
    }


    fun clickSend(){
        val getPref = getSharedPreferences("account", AppCompatActivity.MODE_PRIVATE)
        val nickname = getPref?.getString("nickname", null)

        val message = binding.msgChatInput.text.toString()

        val calendar : Calendar = Calendar.getInstance() // 현재시간 객체
        val time: String = calendar[Calendar.HOUR_OF_DAY].toString() + ":" + calendar[Calendar.MINUTE].toString()

        val pref = getSharedPreferences("otherAccount", AppCompatActivity.MODE_PRIVATE)
        val otherName = pref.getString("nickname", null)

        chatRef = firebaseFirestore.collection(nickname + "," + otherName) // 내 닉네임 + 상대방 닉네임의 컬렉션이름
        otherChatRef = firebaseFirestore.collection(otherName + "," + nickname) // 상대방 + 내 닉네임의 컬렉션이름

        val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        val fileName:String = sdf.format(Date()) + "_" + nickname // 저장될 파일명 : 닉네임 + 날짜 + .png
        val chat: MutableMap<String, String> = HashMap() // Object 사용하면 int string 다 가능. <식별자, 값>
        val msg: MutableMap<String, String> = HashMap()

        chat["nickname"] = nickname.toString() // ("식별자", 값)
        chat["message"] = message
        chat["time"] = time

        msg["nickname"] = "$otherName" // ("식별자", 값)
        msg["message"] = message
        msg["time"] = time

        chatRef?.document(fileName)?.set(chat)
        otherChatRef?.document(fileName)?.set(chat)

        chatRef = firebaseFirestore.collection("[msgList]"+nickname) // 내 닉네임 + 상대방 닉네임의 컬렉션이름
        otherChatRef = firebaseFirestore.collection("[msgList]"+otherName) // 상대방 + 내 닉네임의 컬렉션이름

        chatRef?.document("$otherName")?.set(msg)
        otherChatRef?.document(nickname.toString())?.set(chat)

        binding.msgChatInput.setText("")

        // 메세지 전송하면 소프트키보드 밑으로 내려가게 설정
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
    }


}



























