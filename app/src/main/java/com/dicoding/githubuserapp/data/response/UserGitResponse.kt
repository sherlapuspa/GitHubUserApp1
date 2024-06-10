package com.dicoding.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class UserGitResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val items: ArrayList<UserData>

)
