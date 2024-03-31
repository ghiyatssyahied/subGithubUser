package com.example.subgithubuser.data.response

import com.google.gson.annotations.SerializedName

data class ResponseUser(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("id")
	val id: Int = 0,

	@field:SerializedName("login")
	val login: String,


	@field:SerializedName("avatar_url")
	val avatarUrl: String,


)
