package com.jspstudio.travin.network

import com.jspstudio.travin.model.NidUserInfoResponse
import com.jspstudio.travin.model.UserData
import retrofit2.Call
import retrofit2.http.*

interface RetrofitSignUpService {

    @Multipart
    @POST("Travin/insertUserData.php") // 회원정보 데이터들을 데이터DB 테이블에 저장 (id, nickname, password)
    fun userDataToServer(
        @PartMap userDataPart: MutableMap<String, String>
    ): Call<String?>?

    @POST("Travin/loadUserData.php") // 회원정보 데이터들이 json형태로 저장되어있음
    open fun loadUserDataFromServer(): Call<ArrayList<UserData?>?>?

    // 네아로(네이버아이디로그인) 사용자 정보 API
    @GET("/v1/nid/me")
    fun getNidUser(@Header("Authorization") authorizationInfo:String): Call<NidUserInfoResponse>
}