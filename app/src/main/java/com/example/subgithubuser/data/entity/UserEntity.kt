package com.example.subgithubuser.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
    (
    tableName = "favorite"
)
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "username")
    var login: String? = null,

    @ColumnInfo(name = "photo")
    var avatarUrl: String? = null,

    ) : Parcelable
