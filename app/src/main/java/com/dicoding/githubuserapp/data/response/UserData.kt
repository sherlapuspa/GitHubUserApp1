package com.dicoding.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserData(

    @field:SerializedName("login") val login: String,

    @field:SerializedName("avatar_url") val avatarUrl: String,

    @field:SerializedName("html_url") val htmlUrl: String,

    @field:SerializedName("id") val id: Int,

    )
