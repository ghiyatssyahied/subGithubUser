package com.example.subgithubuser.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.subgithubuser.data.entity.UserEntity
import com.example.subgithubuser.data.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val repository: UserRepository = UserRepository(application)

    fun getFavoriteUser(): LiveData<List<UserEntity>> {
        return repository.getAllUser()
    }
}
