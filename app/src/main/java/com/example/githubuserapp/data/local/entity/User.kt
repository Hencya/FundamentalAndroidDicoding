package com.example.githubuserapp.data.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val username: String,
    val name: String,
    val avatar: String,
    val follower: String,
    val following: String,
    val company: String,
    val location: String,
    val repository: String,
) : Parcelable