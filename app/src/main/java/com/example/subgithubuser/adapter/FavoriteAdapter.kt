package com.example.subgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subgithubuser.diffCallback.FavoriteDiffCallback
import com.example.subgithubuser.data.entity.UserEntity
import com.example.subgithubuser.databinding.ItemRowUserBinding

class FavoriteAdapter(
    private val listener: OnFavoriteUserClickListener
) : RecyclerView.Adapter<FavoriteAdapter.FavViewHolder>() {

    private var listFavorite = listOf<UserEntity>()

    fun setFavoriteList(favoriteList: List<UserEntity>) {
        val diffCallback = FavoriteDiffCallback(listFavorite, favoriteList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listFavorite = favoriteList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {
        val user = listFavorite[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = listFavorite[position]
                    listener.onFavoriteUserClick(user)
                }
            }
        }

        fun bind(user: UserEntity) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = user.login
        }
    }

    interface OnFavoriteUserClickListener {
        fun onFavoriteUserClick(user: UserEntity)
    }

}
