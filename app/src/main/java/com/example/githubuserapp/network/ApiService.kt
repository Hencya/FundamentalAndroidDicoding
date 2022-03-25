package com.example.githubuserapp.network

import com.example.githubuserapp.BuildConfig
import com.example.githubuserapp.model.UserDetailResponseItem
import com.example.githubuserapp.model.UserSearchResponse
import com.example.githubuserapp.model.UserSocialResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun searchUser(
        @Query("q")
        query: String
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUser(
        @Path("username")
        login: String
    ): Call<UserDetailResponseItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUserFollowers(
        @Path("username")
        login: String
    ): Call<List<UserSocialResponse>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.TOKEN}")
    fun getUserFollowing(
        @Path("username")
        login: String
    ): Call<List<UserSocialResponse>>
}