package com.example.subgithubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.subgithubuser.R
import com.example.subgithubuser.data.response.FollowResponse

class FollowAdapter(var followList: ArrayList<FollowResponse>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    private var listener: OnFollowClickListener? = null

    interface OnFollowClickListener {
        fun onFollowClicked(follow: FollowResponse)
    }

    fun setOnFollowClickListener(listener: OnFollowClickListener) {
        this.listener = listener
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val follow = followList[position]
                listener?.onFollowClicked(follow)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follow = followList[position]
        Glide.with(holder.itemView.context)
            .load(follow.avatarUrl)
            .into(holder.imgPhoto)
        holder.tvName.text = follow.login
    }

    override fun getItemCount(): Int = followList.size


}
