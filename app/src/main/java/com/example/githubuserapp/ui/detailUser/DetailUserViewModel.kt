package com.example.githubuserapp.ui.detailUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.UserDetailResponseItem
import com.example.githubuserapp.network.ApiConfig
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {
    companion object {
        private const val TAG = "DetailUserViewModel"
        private const val FAILED = "Connection Failed"
    }

    private val _userDetail = MutableLiveData<UserDetailResponseItem>()
    val userDetail: LiveData<UserDetailResponseItem> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackbarText

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
}