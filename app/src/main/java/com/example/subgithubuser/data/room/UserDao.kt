package com.example.subgithubuser.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.subgithubuser.data.entity.UserEntity


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity)

    @Update
    fun update(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("SELECT  * from favorite")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("SELECT  * from favorite WHERE id = :id")
    fun getUserFavoriteById(id: Int): LiveData<List<UserEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite WHERE id = :id)")
    fun isFavoriteUser(id: Int): LiveData<Boolean>

}