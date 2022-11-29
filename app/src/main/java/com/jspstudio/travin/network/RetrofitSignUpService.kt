package com.jspstudio.travin.network

import com.jspstudio.travin.model.UserData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface RetrofitSignUpService {

    @Multipart
    @POST("Travin/insertUserData.php") // 회원정보 데이터들을 데이터DB 테이블에 저장 (id, nickname, password)
    fun userDataToServer(
        @PartMap userDataPart: MutableMap<String, String>
    ): Call<String?>?

    @POST("Travin/loadUserData.php") // 회원정보 데이터들이 json형태로 저장되어있음
    open fun loadUserDataFromServer(): Call<ArrayList<UserData?>?>?
}