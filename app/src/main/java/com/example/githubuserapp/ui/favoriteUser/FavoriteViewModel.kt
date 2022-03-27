package com.example.githubuserapp.ui.favoriteUser

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.FavoriteRepository
import com.example.githubuserapp.data.Result
import com.example.githubuserapp.data.local.entity.UserFavEntity

//class FavoriteViewModel(application: Application) :
//    ViewModel() {
//    private val favRepo: FavoriteRepository = FavoriteRepository(application)
//
//    fun getFavorite(): LiveData<List<UserFavEntity>> = favRepo.getFavorite()
//}

class FavoriteViewModel(private val favRepo: FavoriteRepository) : ViewModel() {

    fun getFavorite() = favRepo.getFavorite()
}