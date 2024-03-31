package com.example.subgithubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subgithubuser.data.response.ItemsItem
import com.example.subgithubuser.databinding.ItemRowUserBinding

class ListGithubAdapter(private var listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListGithubAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            Glide.with(binding.root)
                .load(item.avatarUrl)
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = item.login

            binding.root.setOnClickListener {
                onItemClickCallback.onItemClicked(item)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: ItemsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size
}