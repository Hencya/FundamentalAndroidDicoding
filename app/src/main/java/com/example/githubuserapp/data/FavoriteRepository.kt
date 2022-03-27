package com.example.githubuserapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.data.local.room.FavoriteDao
import com.example.githubuserapp.data.local.room.FavoriteDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val favDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteDatabase.getInstance(application)
        favDao = db.favoriteDao()
    }

    fun getFavorite(): LiveData<List<UserFavEntity>> = favDao.getFavoriteListUser()

    fun insertFavoriteUser(fav: UserFavEntity) {
        executorService.execute { favDao.insertFavoriteUser(fav) }
    }

    fun deleteFavoriteUser(username: String) {
        executorService.execute { favDao.deleteFavoriteUser(username) }
    }
}



