package com.mantramall.api

import android.os.Message
import com.mantramall.dataModel.*
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.sql.ClientInfoStatus

interface API {

    // get posts api interface
//    @GET("posts/1")
//    suspend fun getPosts(): dataRegisterUser

    @FormUrlEncoded
    @POST("public/api/user-rgister")
    suspend fun pushPost(
        @Field("mobile") mobile:String,
        @Field("refrence") refrence: String,
        @Field("random") random:String,
//        @Field("body") body:String
    ): Response<dataRegisterResponse>

    @FormUrlEncoded
    @POST("public/api/user-edit")
    suspend fun pushMyPost(
        @Field("message") message:String,
        @Field("status") status: Boolean,

    ): Response<dataEditUserProfile>

    @FormUrlEncoded
    @POST("public/api/upload-file")
    suspend fun pushImagePost(
        @Field("message") message:String,
        @Field("status") status: Boolean,

        ): Response<dataUserImage>

    @FormUrlEncoded
    @POST("public/api/user-bank")
    suspend fun pushBankEditPost(
        @Field("message") message:String,
        @Field("status") status: Boolean,

        ): Response<dataBankEdit>
}