package com.example.vpndetector.feature.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.vpndetector.data.remote.post.model.PostListItem
import com.example.vpndetector.databinding.PostItemLayoutBinding

class PostItemViewHolder(val postItemLayoutBinding: PostItemLayoutBinding) :
    RecyclerView.ViewHolder(postItemLayoutBinding.root) {

    fun setPostItem(postItem: PostListItem) {
        postItemLayoutBinding.apply {
            postTitleTv.text = postItem.title
            postBodyTv.text = postItem.body
        }
    }
}
