package com.example.githubuserapp.ui.favoriteUser

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.data.FavoriteRepository
import com.example.githubuserapp.di.Injection

//class FavoriteViewModelFactory private constructor(private val mApplication: Application) :
//    ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
//            return FavoriteViewModel(mApplication) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//
//    companion object {
//        @Volatile
//        private var instance: FavoriteViewModelFactory? = null
//
//        @JvmStatic
//        fun getInstance(application: Application): FavoriteViewModelFactory =
//            instance ?: synchronized(FavoriteViewModelFactory::class.java) {
//                instance ?: FavoriteViewModelFactory(application)
//            }
//
//    }
//}
class FavoriteViewModelFactory private constructor(private val favRepo: FavoriteRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(favRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FavoriteViewModelFactory? = null
        fun getInstance(context: Context): FavoriteViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FavoriteViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}
