package com.example.subgithubuser.ui.detail

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.subgithubuser.data.entity.UserEntity
import com.example.subgithubuser.data.repository.UserRepository
import com.example.subgithubuser.data.response.DetailResponse
import com.example.subgithubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val application: Application) : ViewModel() {


    private val _userDetail = MutableLiveData<DetailResponse>()
    val userDetail: LiveData<DetailResponse> = _userDetail

    private val isLoading = MutableLiveData<Boolean>()

    private val mUserRepository: UserRepository = UserRepository(application)


    fun userDetail(username: String) {
        isLoading.value = true
        val detail = ApiConfig.getApiService().getUserDetail(username)
        detail.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                }
                isLoading.value = false
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                Toast.makeText(application, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                isLoading.value = false
            }
        })
    }

    fun addFavorite(userEntity: UserEntity) {
        mUserRepository.addFavorite(userEntity)

    }

    fun removeFavorite(userEntity: UserEntity) {
        mUserRepository.removeFavorite(userEntity)
    }

    fun isFavoriteUser(id: Int): LiveData<Boolean> {
        return mUserRepository.isFavoriteUser(id)
    }


}


