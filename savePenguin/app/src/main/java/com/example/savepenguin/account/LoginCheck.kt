package com.example.savepenguin.account

import android.util.Log
import com.example.savepenguin.retrofitSetting
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginCheck(id : String,pw : String) {

    var userid = ""
    var userpw = ""
    var login: Login? = null

    init {
        userid = id
        userpw = pw
    }
    var retrofit = Retrofit.Builder()
        .baseUrl(retrofitSetting.getBaseurl())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var loginService: ConnAPI = retrofit.create(ConnAPI::class.java)

    fun tryLogin() {
        var user = HashMap<String, String>()
        user["userid"] = userid
        user["userpw"] = userpw

        loginService.requestLogin2(user).enqueue(object : Callback<Login> {
            override fun onFailure(call: Call<Login>, t: Throwable) {
                //실패할 경우
                Log.d("DEBUG", t.message.toString())

            }

            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                //정상응답이 올경우
                login = response.body()
                Log.d("LOGIN", "msg : " + login?.msg)
                Log.d("LOGIN", "code : " + login?.code)

                if (login?.code == "0000") {

                } else {

                }
            }
        })
    }
}