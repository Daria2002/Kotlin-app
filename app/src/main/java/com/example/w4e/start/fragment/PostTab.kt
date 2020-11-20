package com.example.w4e.start.fragment

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.adapter.PostAdapter
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.model.Post

class PostTab: Fragment(), OnClickListener {
    private lateinit var recyclerViewPosts: RecyclerView
    private lateinit var listPosts: List<Post>
    private lateinit var postsRecyclerAdapter: PostAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_item_table, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initListeners()
        initObjects(view.context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initObjects(context: Context) {
        val mLayoutManager = LinearLayoutManager(context)
        recyclerViewPosts.layoutManager = mLayoutManager
        recyclerViewPosts.itemAnimator = DefaultItemAnimator()
        recyclerViewPosts.setHasFixedSize(true)
        listPosts = mutableListOf<Post>()
        listPosts = PostDatabaseHelper.getInstance(context).getAllPosts(userName)
        updatePostsInGui()
    }

    private fun updatePostsInGui() {
        postsRecyclerAdapter = PostAdapter(listPosts as MutableList<Post>, this)
        recyclerViewPosts.adapter = postsRecyclerAdapter
    }

    private fun initListeners() {
    }

    private fun initViews(view: View) {
        recyclerViewPosts = view.findViewById<RecyclerView>(R.id.recyclerViewPosts)
    }

    companion object {
        private lateinit var userName: String
        // newInstance constructor for creating fragment with arguments
        fun newInstance(name: String): PostTab? {
            userName = name
            return PostTab()
        }
    }

    private fun showPostDetails(c: Context, post: Post) {
        val postEditText = EditText(c)
        var postDetails = post.text + "\nPost category: " + post.category + "\nPosted on: " + post.time
        postEditText.setTextColor(Color.WHITE)
        val dialog = AlertDialog.Builder(c, R.style.Work4Experience_AddPostDialog)
            .setTitle("Post details")
            .setMessage(postDetails)
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
    }

    override fun onClick(v: View?) {
        when(v!!.id) {
            R.id.postText -> showPostDetails(v.context, v.tag as Post)
        }
    }
}