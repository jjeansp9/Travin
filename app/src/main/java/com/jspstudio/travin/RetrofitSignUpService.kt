package com.jspstudio.travin

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap

interface RetrofitSignUpService {

    @Multipart
    @POST("Travin/insertUserData.php")
    fun userDataToServer(
        @PartMap userDataPart: MutableMap<String, String>
    ): Call<String?>?

    @POST("Travin/loadUserData.php")
    open fun loadUserDataFromServer(): Call<ArrayList<UserData?>?>?
}