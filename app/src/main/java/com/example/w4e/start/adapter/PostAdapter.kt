package com.example.w4e.start.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.model.Post
import kotlinx.android.synthetic.main.item_post_recycler.view.*

class PostAdapter(private val listPosts: MutableList<Post>, private val listener: View.OnClickListener) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post_recycler, parent, false)
        return PostViewHolder(itemView)
    }

    override fun getItemCount(): Int = listPosts.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.postText.text = listPosts[position].time.toString() // TODO: this should be text
        holder.postText.setOnClickListener {
            it.tag = listPosts[position]
            listener.onClick(it)
        }
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postText: AppCompatButton
        init {
            postText = view.findViewById(R.id.postText) as AppCompatButton
        }
    }
}