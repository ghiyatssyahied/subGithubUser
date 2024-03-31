package com.example.subgithubuser.diffCallback

import androidx.recyclerview.widget.DiffUtil
import com.example.subgithubuser.data.response.FollowResponse

class UserDiffCallback(
    private val oldList: ArrayList<FollowResponse>,
    private val newList: List<FollowResponse>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}