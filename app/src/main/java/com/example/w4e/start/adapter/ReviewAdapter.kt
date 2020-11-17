package com.example.w4e.start.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.model.Review

class ReviewAdapter(private val listPosts: MutableList<Review>, private val listener: View.OnClickListener) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_post_recycler, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun getItemCount(): Int = listPosts.size

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.postText.text = listPosts[position].text
        holder.postText.setOnClickListener {
            it.tag = listPosts[position]
            listener.onClick(it)
        }
    }

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postText: AppCompatButton
        init {
            postText = view.findViewById(R.id.postText) as AppCompatButton
            postText.transformationMethod = null // all caps off
        }
    }
}