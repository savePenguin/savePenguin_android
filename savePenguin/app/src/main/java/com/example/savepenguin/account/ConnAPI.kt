package com.example.savepenguin.account
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ConnAPI{

    @FormUrlEncoded
    @POST("/auth/signin")
    fun requestLogin(
        @Field("userid") userid:String,
        @Field("userpw") userpw:String
    ) : Call<Login>

    @POST("/auth/signin")
    fun requestLogin2(
        @Body user: Map<String, String>
    ): Call<Login>

}