package com.example.subgithubuser.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subgithubuser.R
import com.example.subgithubuser.adapter.FavoriteAdapter
import com.example.subgithubuser.data.entity.UserEntity
import com.example.subgithubuser.databinding.ActivityFavoriteBinding
import com.example.subgithubuser.ui.detail.DetailActivity

@Suppress("DEPRECATION")
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    private val viewModel: FavoriteViewModel by viewModels { FavViewModelFactory(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(getString(R.string.favorite))
        setupRecyclerView()
        observeFavoriteUsers()
    }

    private fun setupRecyclerView() {
        adapter = FavoriteAdapter(object : FavoriteAdapter.OnFavoriteUserClickListener {
            override fun onFavoriteUserClick(user: UserEntity) {
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java).apply {
                    putExtra("EXTRA_USER", user.login)
                    putExtra("EXTRA_USER_ID", user.id)
                    putExtra("EXTRA_AVATAR", user.avatarUrl)
                }
                startActivity(intent)
            }
        })
        binding.rvFavorite.layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.adapter = adapter
    }

    private fun observeFavoriteUsers() {
        viewModel.getFavoriteUser().observe(this) { favoriteUsers ->

            if (favoriteUsers.isEmpty()) {
                showMessage()
            } else {
                adapter.setFavoriteList(favoriteUsers)
            }
        }
    }

    private fun setToolbar(title: String) {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayShowHomeEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            this.title = title
        }
    }

    private fun showMessage() {
        binding.apply {
            tvStatus.visibility = View.VISIBLE
            rvFavorite.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
