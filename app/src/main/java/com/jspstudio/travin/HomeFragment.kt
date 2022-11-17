package com.jspstudio.travin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.jspstudio.travin.databinding.BsCommentBinding
import com.jspstudio.travin.databinding.FragmentHomeBinding
import java.util.*

class HomeFragment : Fragment() {

    val commentBinding: BsCommentBinding by lazy { BsCommentBinding.inflate(layoutInflater) }

    lateinit var binding:FragmentHomeBinding

    var popularItems: MutableList<HomePopularItem> = mutableListOf() // 오늘의 인기글 데이터
    var items: MutableList<HomeItem> = mutableListOf() // 홈화면 업로드한 글 데이터
    var commentItems: MutableList<CommentRecyclerItem> = mutableListOf() // 댓글 데이터

    val listAdapter by lazy { context?.let { HomeRecyclerAdapter(it, items) } }


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



        loadDataFromHomeUpload() // 업로드된 데이터들 불러오기

        // 플로팅버튼 클릭하면 새 게시물 업로드화면 열림
        binding.fabHomeAddWrite.setOnClickListener { clickFabTabMenu(0) }

        clickUploadData()
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

            // 댓글
            override fun commentClick(v: View, position: Int) {
                val bottomSheetDialog = BottomSheetDialog(view!!.context)
                bottomSheetDialog.setContentView(commentBinding.root) // bs_comment.xml 바인딩해서 연결
                commentBinding.commentRecycler.adapter = CommentRecyclerAdapter(view!!.context, commentItems)

                // 댓글 더미데이터 테스트
                for (i in 0..10){
                    commentItems.add(CommentRecyclerItem(R.drawable.profile, "name", "댓글내용 아무거나", "1"))
                }

                bottomSheetDialog.show()
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
        val homeUploadRef = firebaseFirestore.collection("homeUploads") // firestore에 있는 homeUploads 라는 이름의 컬렉션을 참조

        homeUploadRef.addSnapshotListener { value, error ->
            val documentChangeList : List<DocumentChange> = value!!.documentChanges
            for (documentChange : DocumentChange in documentChangeList){
                // 변경된 document의 데이터를 촬영한 스냅샷 얻어오기
                val snapshot : DocumentSnapshot = documentChange.document

                // document 안에 있는 필드 값들 얻어오기
                val homeUpload : Map<String, String> = snapshot.data as Map<String, String>
                val nickName = homeUpload["homeUploadNickname"]
                val uploadImg = homeUpload["homeUploadImgUrl"]
                val uploadContents = homeUpload["homeUploadContents"]
                val uploadTime = homeUpload["homeUploadTime"]

                // 홈 업로드 글 데이터 추가
                val item : HomeItem = HomeItem(R.drawable.ic_profile,
                    nickName,
                    "서울특별시 서초구",
                    uploadTime,
                    uploadImg,
                    R.drawable.ic_favorite,
                    R.drawable.ic_comment,
                    uploadContents)

                // 홈 오늘의 인기글 데이터추가
                val popularItem : HomePopularItem = HomePopularItem(uploadImg)

                items.add(item)
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






























