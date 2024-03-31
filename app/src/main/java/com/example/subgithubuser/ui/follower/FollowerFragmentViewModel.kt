package com.example.subgithubuser.ui.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subgithubuser.data.response.FollowResponse
import com.example.subgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFragmentViewModel : ViewModel() {
    private val _followerList = MutableLiveData<ArrayList<FollowResponse>>()
    val followerList: LiveData<ArrayList<FollowResponse>> = _followerList

    private val _isLoading = MutableLiveData<Boolean>()


    companion object {
        private const val TAG = "FollowerFragmentViewModel"
    }

    fun getFollower(username: String) {
        try {
            _isLoading.value = true
            val followerCall = ApiConfig.getApiService().getUserFollowers(username)

            followerCall.enqueue(object : Callback<ArrayList<FollowResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<FollowResponse>>,
                    response: Response<ArrayList<FollowResponse>>
                ) {
                    Log.d("debugFollower", response.toString())
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _followerList.value = response.body()
                    }
                }

                override fun onFailure(call: Call<ArrayList<FollowResponse>>, t: Throwable) {
                    _isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }
            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }
}
