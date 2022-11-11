package com.jspstudio.travin

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.ActivityMessageChattingBinding
import java.util.*

class MessageChattingActivity : AppCompatActivity() {

    val binding: ActivityMessageChattingBinding by lazy { ActivityMessageChattingBinding.inflate(layoutInflater) }

    var items : MutableList<MessageChattingRecyclerItem> = mutableListOf()
    var chatRef : CollectionReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        Toast.makeText(this, "${items.size}", Toast.LENGTH_SHORT).show()
        binding.msgChatRecycler.adapter = MessageChattingRecyclerAdapter(this, items)

        // 액션바에 뒤로가기 버튼
        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.subtitle = UserDatas.nickname // 닉네임

        val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성
        chatRef = firebaseFirestore.collection("chat")

        chatRef?.addSnapshotListener{ value, error ->
            val documentChangeList : List<DocumentChange> = value!!.documentChanges
            for (documentChange : DocumentChange in documentChangeList){
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot : DocumentSnapshot = documentChange.document

                // document 안에 있는 필드 값들 얻어오기
                val chatUpload : Map<String, String> = snapshot.data as Map<String, String>
                val nickname = chatUpload["nickname"]
                val message = chatUpload["message"]
                val time = chatUpload["time"]

                val item : MessageChattingRecyclerItem = MessageChattingRecyclerItem(nickname, message, time)

                items.add(item)

                binding.msgChatRecycler.adapter?.notifyItemInserted(items.size -1)
                binding.msgChatRecycler.scrollToPosition(binding.msgChatRecycler.adapter!!.itemCount -1)

            }
        }
        binding.msgChatSend.setOnClickListener{clickSend()}


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    fun loadDatafromFirebase(){


    }

    fun clickSend(){
        val nickname = UserDatas.nickname
        val message = binding.msgChatInput.text.toString()

        val calendar : Calendar = Calendar.getInstance() // 현재시간 객체
        val time: String = calendar[Calendar.HOUR_OF_DAY].toString() + ":" + calendar[Calendar.MINUTE].toString()

        val item : MessageChattingRecyclerItem = MessageChattingRecyclerItem(nickname,
            message, time)

        chatRef?.document(UserDatas.nickname.toString())?.set(item)

        binding.msgChatInput.setText("")

        // 메세지 전송하면 소프트키보드 밑으로 내려가게 설정
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

    }


}



























