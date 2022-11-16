package com.mantramall.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantramall.dataModel.*
import com.mantramall.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class MainViewModel(private val repository: Repository) : ViewModel() {

    val myResponse: MutableLiveData<dataRegisterUser> = MutableLiveData()
    val myPushResponse: MutableLiveData<Response<dataRegisterResponse>> = MutableLiveData()
    val myPushResponse1: MutableLiveData<Response<dataUserProfile>> = MutableLiveData()
    val myPushEditUserResponse: MutableLiveData<Response<dataEditUserProfile>> = MutableLiveData()
    val myBankPushResponse: MutableLiveData<Response<dataBankEdit>> = MutableLiveData()
    val myPushImageResponse: MutableLiveData<Response<dataUserImage>> = MutableLiveData()

//    fun getPosts() {
//        viewModelScope.launch {
//            val response = repository.getPosts()
//            myResponse.value = response
//        }
//    }
    fun pushMyPost(message: String,status: Boolean){
        viewModelScope.launch {
            val response=repository.pushMyPost(message,status)
            myPushEditUserResponse.value=response
        }
    }




    fun pushPosts(mobile:String,refrence:String,random:String) {
        viewModelScope.launch {
            val response = repository.pushPosts(mobile,refrence,random)
            myPushResponse.value = response
        }
    }
    fun pushImagePost(message: String,status: Boolean) {
        viewModelScope.launch {
            val response=repository.pushImagePost(message,status)
            myPushImageResponse.value=response
        }
    }
}