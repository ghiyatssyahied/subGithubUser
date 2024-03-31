package com.example.subgithubuser.ui.follower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.subgithubuser.diffCallback.UserDiffCallback
import com.example.subgithubuser.adapter.FollowAdapter
import com.example.subgithubuser.data.response.FollowResponse
import com.example.subgithubuser.databinding.FragmentFollowerBinding
import com.example.subgithubuser.ui.detail.DetailActivity

class FollowerFragment : Fragment() {

    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerFragmentViewModel
    private lateinit var adapter: FollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        viewModel = ViewModelProvider(this)[FollowerFragmentViewModel::class.java]

        adapter = FollowAdapter(ArrayList())
        binding.rvFollower.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = this@FollowerFragment.adapter
        }

        val args = arguments
        val username = args?.getString("EXTRA_USER")
        if (username != null) {
            viewModel.getFollower(username)
        }

        viewModel.followerList.observe(viewLifecycleOwner) { follower ->
            if (follower.isEmpty()) {
                Toast.makeText(requireContext(), "Followers not found", Toast.LENGTH_SHORT).show()
            } else {
                val diffCallback = UserDiffCallback(adapter.followList, follower)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                adapter.followList = follower
                diffResult.dispatchUpdatesTo(adapter)
            }
            showLoading(false)
        }

        adapter.setOnFollowClickListener(object : FollowAdapter.OnFollowClickListener {
            override fun onFollowClicked(follow: FollowResponse) {
                selectUser(follow)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectUser(user: FollowResponse) {
        val moveUser = Intent(activity, DetailActivity::class.java)
        moveUser.putExtra("EXTRA_USER", user.login)
        moveUser.putExtra("EXTRA_USER_ID", user.id)
        moveUser.putExtra("EXTRA_AVATAR", user.avatarUrl)
        startActivity(moveUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
