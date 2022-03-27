package com.example.githubuserapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.data.local.room.FavoriteDao
import com.example.githubuserapp.data.remote.retrofit.ApiService
import com.example.githubuserapp.utils.AppExecutors


class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    fun getFavorite(): LiveData<Result<List<UserFavEntity>>> =
        liveData {
            emit(Result.Loading)
            val localData: LiveData<Result<List<UserFavEntity>>> =
                favDao.getFavoriteListUser().map { Result.Success(it) }
            if (localData == null) {
                emit(Result.Error("Favorite User Doesn't Exist"))
            } else {
                emitSource(localData)
            }
        }

    companion object {
        @Volatile
        private var instance: FavoriteRepository? = null
        fun getInstance(
            apiService: ApiService,
            favDao: FavoriteDao,
            appExecutors: AppExecutors
        ): FavoriteRepository =
            instance ?: synchronized(this) {
                instance ?: FavoriteRepository(apiService, favDao, appExecutors)
            }.also { instance = it }
    }
}




