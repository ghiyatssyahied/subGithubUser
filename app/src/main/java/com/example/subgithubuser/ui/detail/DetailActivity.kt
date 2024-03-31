package com.example.subgithubuser.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.subgithubuser.R
import com.example.subgithubuser.adapter.SectionPagerAdapter
import com.example.subgithubuser.data.entity.UserEntity
import com.example.subgithubuser.databinding.ActivityDetailBinding
import com.example.subgithubuser.ui.main.MainActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory(application)
    }
    private lateinit var binding: ActivityDetailBinding
    private var isFavorite = false
    private lateinit var userEntity: UserEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = intent.getStringExtra("EXTRA_USER").orEmpty()
        val id = intent.getIntExtra("EXTRA_USER_ID", 0)
        val avatar = intent.getStringExtra("EXTRA_AVATAR").orEmpty()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        userEntity = UserEntity(id = id, login = username, avatarUrl = avatar)

        val sectionPagerAdapter = SectionPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        getData()


        viewModel.isFavoriteUser(userEntity.id).observe(this) { isFavorite ->
            this@DetailActivity.isFavorite = isFavorite
            setFavoriteState(isFavorite)
        }


        binding.fabFavorite.setOnClickListener {
            isFavorite = !isFavorite
            setFavoriteState(isFavorite)
        }
        binding.btnShare.setOnClickListener {
            shareData()
        }

    }


    private fun getData() {
        showLoading(true)
        viewModel.userDetail(username)
        viewModel.userDetail.observe(this) { userDetail ->
            userDetail?.let {
                showLoading(false)
                Glide.with(this)
                    .load(it.avatarUrl)
                    .skipMemoryCache(true)
                    .into(binding.imgAvatar)

                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollowing.text = it.following.toString()
                    tvFollower.text = it.followers.toString()
                    tvRepositories.text = it.repos.toString()

                }

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            backNavigation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun backNavigation() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setFavoriteState(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
            viewModel.addFavorite(userEntity)
        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            )
            viewModel.removeFavorite(userEntity)
        }
    }

    private fun shareData() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        val shareMessage = "Name: ${binding.tvName.text}\nUsername: ${binding.tvUsername.text}" +
                "\nFollowers: ${binding.tvFollower.text}\nFollowing: ${binding.tvFollowing.text}" +
                "\nRepository: ${binding.tvRepositories.text}"
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        startActivity(Intent.createChooser(shareIntent, "Share data via"))
    }

    companion object {
        var username = String()


        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }
}
