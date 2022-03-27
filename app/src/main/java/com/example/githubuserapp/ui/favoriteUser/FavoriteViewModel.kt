package com.example.githubuserapp.ui.favoriteUser

import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.FavoriteRepository

class FavoriteViewModel(private val favRepo: FavoriteRepository) : ViewModel() {
    fun getFavorite() = favRepo.getFavorite()
}