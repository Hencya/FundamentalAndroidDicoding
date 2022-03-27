package com.example.githubuserapp.di

import android.content.Context
import com.example.githubuserapp.data.FavoriteRepository
import com.example.githubuserapp.data.local.room.FavoriteDatabase
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import com.example.githubuserapp.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): FavoriteRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteDatabase.getInstance(context)
        val dao = database.favoriteDao()
        val appExecutors = AppExecutors()
        return FavoriteRepository.getInstance(apiService, dao, appExecutors)
    }
}