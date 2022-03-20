package com.example.githubuserapp.model

import com.google.gson.annotations.SerializedName

data class UserSocialResponse(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("type")
    val type: String,
)