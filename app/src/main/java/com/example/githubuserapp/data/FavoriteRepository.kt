package com.example.githubuserapp.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.data.local.room.FavoriteDao
import com.example.githubuserapp.data.local.room.FavoriteDatabase
class FavoriteRepository(application: Application) {
    private val favDao: FavoriteDao
    init {
        val db = FavoriteDatabase.getInstance(application)
        favDao = db.favoriteDao()
    }

    fun getFavorite(): LiveData<List<UserFavEntity>> = favDao.getFavoriteListUser()

}






