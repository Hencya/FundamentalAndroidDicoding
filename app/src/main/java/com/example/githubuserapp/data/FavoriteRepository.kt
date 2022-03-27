package com.example.githubuserapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.data.local.room.FavoriteDao
import com.example.githubuserapp.data.local.room.FavoriteDatabase
import com.example.githubuserapp.data.remote.retrofit.ApiService
import com.example.githubuserapp.utils.AppExecutors
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

//class FavoriteRepository(application: Application) {
//    private val favDao: FavoriteDao
//    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
//
//    init {
//        val db = FavoriteDatabase.getInstance(application)
//        favDao = db.favoriteDao()
//    }
//
//    fun getFavorite(): LiveData<List<UserFavEntity>> = favDao.getFavoriteListUser()
//
//    fun insertFavoriteUser(fav: UserFavEntity) {
//        executorService.execute { favDao.insertFavoriteUser(fav) }
//    }
//
//    fun deleteFavoriteUser(username: String) {
//        executorService.execute { favDao.deleteFavoriteUser(username) }
//    }
//}

class FavoriteRepository private constructor(
    private val apiService: ApiService,
    private val favDao: FavoriteDao,
    private val appExecutors: AppExecutors
) {

    fun getFavorite(): LiveData<List<UserFavEntity>> = favDao.getFavoriteListUser()

    fun insertFavoriteUser(userFav: UserFavEntity) {
        appExecutors.diskIO.execute { favDao.insertFavoriteUser(userFav) }
    }

    fun deleteFavoriteUser(username: String) {
        appExecutors.diskIO.execute { favDao.deleteFavoriteUser(username) }
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



