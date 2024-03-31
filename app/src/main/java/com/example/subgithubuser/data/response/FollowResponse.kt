package com.example.subgithubuser.data.response

import com.google.gson.annotations.SerializedName

data class FollowResponse(
	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("followers_url")
	val followersUrl: String,


)
