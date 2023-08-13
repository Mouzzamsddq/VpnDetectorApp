package com.example.vpndetector.feature.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vpndetector.data.remote.post.model.PostListItem
import com.example.vpndetector.databinding.PostItemLayoutBinding
import com.example.vpndetector.feature.viewholder.PostItemViewHolder

class PostAdapter : RecyclerView.Adapter<PostItemViewHolder>() {

    private var postList = listOf<PostListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        return PostItemViewHolder(
            PostItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        postList.getOrNull(position)?.let {
            holder.apply {
                postItemLayoutBinding.apply {
                    setPostItem(it)
                }
            }
        }
    }

    fun setPostItemList(postItems: List<PostListItem>) {
        this.postList = postItems
        notifyDataSetChanged()
    }
}
