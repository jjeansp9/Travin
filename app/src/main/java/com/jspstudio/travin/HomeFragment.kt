package com.jspstudio.travin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.BsCommentBinding
import com.jspstudio.travin.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding

    var popularItems: MutableList<HomePopularItem> = mutableListOf() // 오늘의 인기글 아이템
    var items: MutableList<HomeItem> = mutableListOf() // 홈화면 업로드한 글 아이템
    var commentItems: MutableList<CommentRecyclerItem> = mutableListOf() // 댓글 아이템

    var homeRef : CollectionReference? = null
    val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성

    val listAdapter by lazy { context?.let { HomeRecyclerAdapter(it, items) } }
    val commentAdapter by lazy { context?.let { CommentRecyclerAdapter(it, commentItems) } }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homePopularRecycler.adapter = HomePopularRecyclerAdapter(view.context, popularItems) // 홈화면 오늘의 인기글 어댑터연결
        binding.homeRecycler.adapter = listAdapter // 업로드글 연결

        // 플로팅버튼 클릭하면 새 게시물 업로드화면 열림
        binding.fabHomeAddWrite.setOnClickListener { clickFabTabMenu(0) }

        clickUploadData()
    }

    override fun onResume() {
        super.onResume()
        loadDataFromHomeUpload() // 업로드된 데이터들 불러오기
    }

    // 업로드글들 각 아이템마다 클릭할때 작동
    fun clickUploadData(){
        listAdapter?.setItemClickListener (object : HomeRecyclerAdapter.OnItemClickListener{

            // 이름
            override fun nameClick(v: View, position: Int) {

            }

            // 위치
            override fun locationClick(v: View, position: Int) {

            }

            // 내용
            override fun contentsClick(v: View, position: Int) {

            }

            // 좋아요
            override fun favoriteClick(v: View, position: Int) {

            }

            // 홈화면 업로드된 글에 댓글창 열고 댓글달기
            override fun commentClick(v: View, position: Int) {
                val commentBinding: BsCommentBinding = BsCommentBinding.inflate(layoutInflater) // bs_comment.xml 바인딩
                val bottomSheetDialog = BottomSheetDialog(view!!.context)
                bottomSheetDialog.setContentView(commentBinding.root) // 다이얼로그에 bs_comment.xml 바인딩해서 연결
                commentBinding.commentRecycler.adapter = commentAdapter // 댓글창 어댑터연결
                commentItems.clear()

                bottomSheetDialog.behavior?.isDraggable = false // 드래그할때 바텀시트 크기는 고정되게 설정 (드래그 안되게 설정)
                bottomSheetDialog.behavior?.maxHeight = 1500
                bottomSheetDialog.behavior?.peekHeight = 1500
                bottomSheetDialog.show()

                // 댓글달린 데이터 불러오기
                homeRef = firebaseFirestore.collection("[homeComment]"+items[position].homeName) // 내 닉네임 + 상대방 닉네임의 컬렉션이름

                homeRef?.addSnapshotListener { value, error ->
                    val documentChangeList : List<DocumentChange> = value!!.documentChanges
                    for (documentChange : DocumentChange in documentChangeList){
                        // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                        val snapshot : DocumentSnapshot = documentChange.document

                        // document 안에 있는 필드 값들 얻어오기
                        val homeUpload : Map<String, String> = snapshot.data as Map<String, String>
                        val nickname = homeUpload["nickname"]
                        val comment = homeUpload["comment"]
                        val time = homeUpload["time"]


                        // 데이터 추가
                        val item : CommentRecyclerItem = CommentRecyclerItem(R.drawable.profile,nickname, comment, time)

                        commentItems.add(item)
                        commentBinding.commentRecycler.adapter?.notifyDataSetChanged()

                        // 리사이클러뷰 갱신
                        commentBinding.commentRecycler.adapter?.notifyItemInserted(commentItems.size -1)
                        commentBinding.commentRecycler.scrollToPosition(commentBinding.commentRecycler.adapter!!.itemCount -1)
                    }
                }

                // 댓글 보내기버튼 클릭하면 입력된 채팅 내용대로 댓글 달아짐
                commentBinding.commentSend.setOnClickListener{

                    val nickname = UserDatas.nickname.toString() // 내 닉네임
                    val comment = commentBinding.etBsComment.text.toString() // 댓글 내용

                    val calendar : Calendar = Calendar.getInstance() // 현재시간 객체
                    val time: String = calendar[Calendar.HOUR_OF_DAY].toString() + ":" + calendar[Calendar.MINUTE].toString()

                    homeRef = firebaseFirestore.collection("[homeComment]"+items[position].homeName) // 내 닉네임 + 상대방 닉네임의 컬렉션이름

                    val sdf: SimpleDateFormat = SimpleDateFormat("yyyyMMddHHmmss")
                    val fileName:String = sdf.format(Date()) + "_" + nickname // 저장될 파일명 : 닉네임 + 날짜 + .png
                    val com: MutableMap<String, String> = HashMap() // Object 사용하면 int string 다 가능. <식별자, 값>

                    com["nickname"] = nickname // ("식별자", 값)
                    com["comment"] = comment
                    com["time"] = time

                    homeRef?.document(fileName)?.set(com)

                    commentBinding.etBsComment.setText("")

                    // 댓글 보내기 버튼누르면 소프트키보드 내려가게 설정
                    val imm: InputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(commentBinding.etBsComment.windowToken, 0)
                }
            }
        })
    }



    // 인기글 업로드창 열기
    fun clickFabTabMenu(category:Int){
        val intent = Intent(context, UploadActivity::class.java)
        intent.putExtra("tabNumber", category)
        startActivity(intent)
    }


    fun loadDataFromHomeUpload(){
        val firebaseFirestore = FirebaseFirestore.getInstance() // 파이어스토어 생성
        val profileRef = firebaseFirestore.collection("users") // firestore에 있는 homeUploads 라는 이름의 컬렉션을 참조
        val homeUploadRef = firebaseFirestore.collection("homeUploads")

        homeUploadRef.addSnapshotListener { value, error ->
            val documentChangeList : List<DocumentChange> = value!!.documentChanges
            for (documentChange : DocumentChange in documentChangeList){
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot : DocumentSnapshot = documentChange.document

                val pref: SharedPreferences = view?.context!!.getSharedPreferences("account", Context.MODE_PRIVATE)
                UserDatas.profileUrl = pref.getString("profileUrl", null)

                // document 안에 있는 필드 값들 얻어오기
                val homeUpload : Map<String, String> = snapshot.data as Map<String, String>
                val nickName = homeUpload["homeUploadNickname"]
                val uploadImg = homeUpload["homeUploadImgUrl"]
                val uploadContents = homeUpload["homeUploadContents"]
                val uploadTime = homeUpload["homeUploadTime"]
                val uploadProfile = homeUpload["homeUploadProfileUrl"]

                // 프로필사진 등록하지 않은경우 기본이미지로, 등록했다면 등록한 프로필이미지로 보여주기
                if(uploadProfile == "null"){

                    val item : HomeItem = HomeItem(R.drawable.profile,
                        nickName,
                        "서울특별시 서초구",
                        uploadTime,
                        uploadImg,
                        R.drawable.ic_favorite,
                        R.drawable.ic_comment,
                        uploadContents)
                    items.add(item)
                }else{
                    val item : HomeItem = HomeItem(uploadProfile!!,
                        nickName,
                        "서울특별시 서초구",
                        uploadTime,
                        uploadImg,
                        R.drawable.ic_favorite,
                        R.drawable.ic_comment,
                        uploadContents)
                    items.add(item)
                }



                // 홈 오늘의 인기글 데이터추가
                val popularItem : HomePopularItem = HomePopularItem(uploadImg)

                popularItems.add(popularItem)

                // 오늘의인기글 홈 리사이클러뷰 갱신
                binding.homePopularRecycler.adapter?.notifyItemInserted(popularItems.size -1)

                // 홈 리사이클러뷰 갱신
                binding.homeRecycler.adapter?.notifyItemInserted(items.size -1)
                binding.homeRecycler.scrollToPosition(binding.homeRecycler.adapter!!.itemCount -1)

            }
        }

    }

}






























