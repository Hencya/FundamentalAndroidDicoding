package com.example.githubuserapp.network

import com.example.githubuserapp.model.UserSearchResponse
import com.example.githubuserapp.model.UserDetailResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q")
        query: String
    ): Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username")
        login: String
    ): Call<UserDetailResponseItem>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username")
        login: String
    ): Call<List<UserDetailResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username")
        login: String
    ): Call<List<UserDetailResponseItem>>
}