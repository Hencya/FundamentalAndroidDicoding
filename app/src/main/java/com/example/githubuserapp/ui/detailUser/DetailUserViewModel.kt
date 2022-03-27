package com.example.githubuserapp.ui.detailUser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuserapp.data.local.entity.UserFavEntity
import com.example.githubuserapp.data.local.room.FavoriteDao
import com.example.githubuserapp.data.local.room.FavoriteDatabase
import com.example.githubuserapp.data.remote.response.UserDetailResponseItem
import com.example.githubuserapp.data.remote.retrofit.ApiConfig
import com.example.githubuserapp.utils.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val _userDetail = MutableLiveData<UserDetailResponseItem>()
    val userDetail: LiveData<UserDetailResponseItem> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackbarText

    private var favDao: FavoriteDao? = null
    private var favDb: FavoriteDatabase? = null

    fun setUserDetail(query: String) {
        if (query.isNotEmpty()) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getUser(query)
            client.enqueue(object : Callback<UserDetailResponseItem> {
                override fun onResponse(
                    call: Call<UserDetailResponseItem>,
                    response: Response<UserDetailResponseItem>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _userDetail.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _snackbarText.value = Event(FAILED)
                    }
                }

                override fun onFailure(call: Call<UserDetailResponseItem>, t: Throwable) {
                    _isLoading.value = false
                    _snackbarText.value = Event(FAILED)
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }

    init {
        favDb = FavoriteDatabase.getInstance(application)
        favDao = favDb?.favoriteDao()
    }

    fun insertToFavorite(userType: String, username: String, avatarURL: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userIn = UserFavEntity(
                userType, username, avatarURL
            )
            favDao?.insertFavoriteUser(userIn)
        }
    }

    fun checkIsFavorited(username: String) = favDao?.isFavorite(username)

    fun removeFromFavorite(username: String) {
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.deleteFavoriteUser(username)
        }
    }

    companion object {
        private const val TAG = "DetailUserViewModel"
        private const val FAILED = "Connection Failed"
    }
}

