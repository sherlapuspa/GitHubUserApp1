package com.dicoding.githubuserapp.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuserapp.data.response.UserDetailResponse
import com.dicoding.githubuserapp.data.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailVM : ViewModel() {
    val userDetails = MutableLiveData<UserDetailResponse>()

    fun setUserDetails(username: String) {
        ApiClient.apiInstance.getUserDetails(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>, response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        userDetails.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.e("TAG", "onFailure: ${t.message}")
                }

            })
    }

    fun getUserDetails(): LiveData<UserDetailResponse> {
        return userDetails
    }
}