package com.example.w4e.start.fragment

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.w4e.start.R
import com.example.w4e.start.adapter.PostAdapter
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.helper.UserDatabaseHelper
import com.example.w4e.start.model.Post
import com.example.w4e.start.model.User
import java.io.File

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
        // TODO: remove action bar for all tabs
        return inflater.inflate(R.layout.posts_table, container, false)
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

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}