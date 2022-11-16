package com.mantramall.repository

import android.webkit.ConsoleMessage
import com.example.kotlinretrofit.api.RetrofitInstance
import com.mantramall.dataModel.dataEditUserProfile
import com.mantramall.dataModel.dataRegisterResponse
import com.mantramall.dataModel.dataRegisterUser
import com.mantramall.dataModel.dataUserImage
import retrofit2.Response
import java.sql.ClientInfoStatus

class Repository {

    // Repository Class
//    suspend fun getPosts(): dataRegisterUser {
//        return RetrofitInstance.api.getPosts()
//    }
    suspend fun pushPosts(mobile:String,refrence:String,random:String): Response<dataRegisterResponse> {
        return RetrofitInstance.api.pushPost(mobile,refrence,random)
    }
    suspend fun pushMyPost(message: String,status: Boolean):Response<dataEditUserProfile> {
        return RetrofitInstance.api.pushMyPost(message, status)
    }
    suspend fun pushImagePost(message: String,status: Boolean):Response<dataUserImage> {
        return RetrofitInstance.api.pushImagePost(message, status)
    }
    suspend fun pushBankPost(message: String,status: Boolean):Response<dataUserImage> {
        return RetrofitInstance.api.pushImagePost(message, status)
    }
}