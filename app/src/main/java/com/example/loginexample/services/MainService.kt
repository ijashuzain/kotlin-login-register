package com.example.loginexample.services

import com.example.loginexample.Model.LoginData
import com.example.loginexample.Model.RegisterData
import com.example.loginexample.Model.ResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MainService {

    @POST("login")
    fun Login(@Body newLoginData: LoginData): Call<ResponseData>

    @POST("register")
    fun Register(@Body newRegister: RegisterData): Call<ResponseData>
}