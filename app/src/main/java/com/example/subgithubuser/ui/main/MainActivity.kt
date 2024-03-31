package com.example.subgithubuser.ui.main


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.subgithubuser.R
import com.example.subgithubuser.adapter.ListGithubAdapter
import com.example.subgithubuser.data.response.ItemsItem
import com.example.subgithubuser.databinding.ActivityMainBinding
import com.example.subgithubuser.ui.detail.DetailActivity
import com.example.subgithubuser.ui.favorite.FavoriteActivity
import com.example.subgithubuser.ui.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvUsers: RecyclerView
    private lateinit var tvStatus: TextView
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvStatus = binding.tvStatus

        setupViews()
        getMainData()
    }

    private fun setupViews() {
        binding.searchView.setupWithSearchBar(binding.searchBar)
        binding.searchBar.inflateMenu(R.menu.option_menu)
        binding.searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    val intentSetting = Intent(this@MainActivity, SettingActivity::class.java)
                    startActivity(intentSetting)
                    true
                }

                R.id.favorite_button -> {
                    val intentFav = Intent(this@MainActivity, FavoriteActivity::class.java)
                    startActivity(intentFav)
                    true
                }

                else -> false
            }
        }
        binding.searchView.editText.setOnEditorActionListener { _, _, _ ->
            binding.searchView.hide()
            val username = binding.searchView.text.toString()
            viewModel.getListUser(username)
            true
        }

        rvUsers = binding.rvUser
        rvUsers.setHasFixedSize(true)
    }

    private fun getMainData() {
        viewModel.userList.observe(this) { userList ->
            if (userList.isNotEmpty()) {
                tvStatus.visibility = View.GONE
                val uAdapter = ListGithubAdapter(ArrayList(userList))
                uAdapter.setOnItemClickCallback(object : ListGithubAdapter.OnItemClickCallback {
                    override fun onItemClicked(user: ItemsItem) {
                        val intentDetail =
                            Intent(this@MainActivity, DetailActivity::class.java).apply {
                                putExtra("EXTRA_USER", user.login)
                                putExtra("EXTRA_USER_ID", user.id)
                                putExtra("EXTRA_AVATAR", user.avatarUrl)
                            }
                        startActivity(intentDetail)
                    }
                })
                rvUsers.adapter = uAdapter
            } else {
                tvStatus.visibility = View.VISIBLE
                rvUsers.adapter = null
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                rvUsers.adapter = null
            }
        }

        viewModel.loading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}