package com.example.subgithubuser.adapter


import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.subgithubuser.ui.follower.FollowerFragment
import com.example.subgithubuser.ui.following.FollowingFragment
class SectionPagerAdapter(fragmentActivity: FragmentActivity,private val username : String) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2


    override fun createFragment(position: Int): Fragment {
        val bundle = bundleOf("EXTRA_USER" to username)
        return when (position) {
            0 -> FollowerFragment().apply {
                arguments = bundle
            }
            1 -> FollowingFragment().apply {
                arguments = bundle
            }
            else -> throw IndexOutOfBoundsException("Invalid position")
        }

    }
}

