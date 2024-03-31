package com.example.subgithubuser.ui.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subgithubuser.data.response.FollowResponse
import com.example.subgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragmentViewModel : ViewModel() {

    private val _followList = MutableLiveData<ArrayList<FollowResponse>>()
    val followList: LiveData<ArrayList<FollowResponse>> = _followList

    private val _isLoading = MutableLiveData<Boolean>()


    companion object {
        private const val TAG = "FollowingFragmentViewModel"
    }

    fun getFollowingData(username: String) {
        try {
            _isLoading.value = true
            val following = ApiConfig.getApiService().getUserFollowing(username)

            following.enqueue(object : Callback<ArrayList<FollowResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<FollowResponse>>,
                    response: Response<ArrayList<FollowResponse>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        _followList.value = response.body()
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
