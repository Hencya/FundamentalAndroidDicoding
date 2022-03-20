package com.example.githubuserapp.ui.userFollower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.model.UserSocialResponse
import com.example.githubuserapp.network.ApiConfig
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {
    companion object {
        private const val TAG = "FollowersViewModel"
        private const val FAILED = "Connection Trouble"
    }

    private val _itemFollowers = MutableLiveData<List<UserSocialResponse>>()
    val itemFollowers: LiveData<List<UserSocialResponse>> = _itemFollowers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText

    fun getUserFollowers(query: String) {
        if (query.isNotEmpty()) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getUserFollowers(query)
            client.enqueue(object : Callback<List<UserSocialResponse>> {
                override fun onResponse(
                    call: Call<List<UserSocialResponse>>,
                    response: Response<List<UserSocialResponse>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _itemFollowers.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _snackbarText.value = Event(FAILED)
                    }
                }

                override fun onFailure(call: Call<List<UserSocialResponse>>, t: Throwable) {
                    _isLoading.value = false
                    _snackbarText.value = Event(FAILED)
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
        }
    }
}