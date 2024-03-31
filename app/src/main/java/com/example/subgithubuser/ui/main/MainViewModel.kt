package com.example.subgithubuser.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subgithubuser.data.response.ItemsItem
import com.example.subgithubuser.data.response.ResponseUser
import com.example.subgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        getListUser("ghiyats")
    }

    fun getListUser(username: String) {
        _loading.value = true
        val apiConfig = ApiConfig.getApiService()

        val apiUser = apiConfig.searchUsername(username)
        apiUser.enqueue(object : Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if (response.isSuccessful) {
                    val userList = response.body()?.items ?: emptyList()
                    _userList.value = userList
                } else {
                    _error.value = "Error: ${response.code()}"
                }
                _loading.value = false
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                _loading.value = false
                _error.value = "Failed to retrieve data"
            }
        })
    }
}
