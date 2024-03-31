package com.example.subgithubuser.data.response

import com.google.gson.annotations.SerializedName

data class DetailResponse(


	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("public_repos")
	val repos: Int
)
