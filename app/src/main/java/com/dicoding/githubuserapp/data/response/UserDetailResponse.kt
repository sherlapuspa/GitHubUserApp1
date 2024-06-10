package com.dicoding.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponse(

    @field:SerializedName("bio")
    val bio: Any,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("html_url")
    val htmlUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("name")
    val name: String,
)
