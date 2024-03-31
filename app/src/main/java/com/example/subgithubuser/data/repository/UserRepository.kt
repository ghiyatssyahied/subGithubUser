package com.example.subgithubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.subgithubuser.data.entity.UserEntity
import com.example.subgithubuser.data.room.UserDao
import com.example.subgithubuser.data.room.UserDatabase
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUser(): LiveData<List<UserEntity>> = mUserDao.getAllUser()

    private fun insert(userEntity: UserEntity) {
        executorService.execute { mUserDao.insert(userEntity) }
    }

    private fun delete(userEntity: UserEntity) {
        executorService.execute { mUserDao.delete(userEntity) }
    }


    fun addFavorite(userEntity: UserEntity) {
        insert(userEntity)
    }

    fun removeFavorite(userEntity: UserEntity) {
        delete(userEntity)
    }

    fun isFavoriteUser(id: Int): LiveData<Boolean> = mUserDao.isFavoriteUser(id)


}
