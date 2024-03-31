package com.example.subgithubuser.data.retrofit

import com.example.subgithubuser.data.response.DetailResponse
import com.example.subgithubuser.data.response.FollowResponse
import com.example.subgithubuser.data.response.ResponseUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUsername(
        @Query("q") q: String
    ): Call<ResponseUser>


    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String,
    ): Call<DetailResponse>


    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String,
    ): Call<ArrayList<FollowResponse>>


    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String,
    ): Call<ArrayList<FollowResponse>>

}