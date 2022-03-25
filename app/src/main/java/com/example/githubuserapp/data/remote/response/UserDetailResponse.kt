package com.example.githubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserDetailResponseItem(

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following_url")
    val followingUrl: String,

    @field:SerializedName("followers_url")
    val followersUrl: String,

    @field:SerializedName("company")
    val company: String,

    @field:SerializedName("location")
    val location: String,

    @field:SerializedName("name")
    val name: String,


    @field:SerializedName("public_repos")
    val publicRepos: Int,
)
