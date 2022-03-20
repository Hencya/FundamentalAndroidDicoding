package com.example.githubuserapp.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.UserSearchItem
import com.example.githubuserapp.model.UserSearchResponse
import com.example.githubuserapp.network.ApiConfig
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    companion object {
        private const val TAG = "MainActivityViewModel"
        private const val FAILED = "Connection Trouble"
        private const val NOT_FOUND = "User Doesn't Exist"
    }

    private val _itemUser = MutableLiveData<List<UserSearchItem>>()
    val itemUser: LiveData<List<UserSearchItem>> = _itemUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackbarText

    init {
        searchUser("")
    }

    fun searchUser(query: String) {
        if (query.isNotEmpty()) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().searchUser(query)
            client.enqueue(object : Callback<UserSearchResponse> {
                override fun onResponse(
                    call: Call<UserSearchResponse>,
                    response: Response<UserSearchResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            if (responseBody.totalCount == 0) {
                                _snackbarText.value = Event(NOT_FOUND)
                            }
                            _itemUser.value = response.body()?.items
                        }
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _snackbarText.value = Event(FAILED)
                    }
                }

                override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                    _isLoading.value = false
                    _snackbarText.value = Event(FAILED)
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }
}