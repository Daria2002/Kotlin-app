package com.example.w4e.start.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.adapter.AddReviewAdapter
import com.example.w4e.start.adapter.PostAdapter
import com.example.w4e.start.helper.PostDatabaseHelper
import com.example.w4e.start.model.Post

/**
 * When user profile is opened there is a tab "In Progress".
 * This tab shows posts that the user is currently involved in and it has button for adding reviews
 * if logged user has posted some post or if logged user has been working on the post that the user has posted.
 */
class InProgressTab: Fragment(), View.OnClickListener {
    private lateinit var recyclerViewAddReview: RecyclerView
    private lateinit var listPosts: List<Post>
    private lateinit var addReviewRecyclerAdapter: AddReviewAdapter

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
        recyclerViewAddReview.layoutManager = mLayoutManager
        recyclerViewAddReview.itemAnimator = DefaultItemAnimator()
        recyclerViewAddReview.setHasFixedSize(true)
        listPosts = mutableListOf<Post>()
        listPosts = PostDatabaseHelper.getInstance(context).getAllPosts(InProgressTab.userName)
        updatePostsInGui()
    }

    private fun updatePostsInGui() {
        // list of posts that the user in currently working on
        addReviewRecyclerAdapter = AddReviewAdapter(listPosts as MutableList<Post>, this)
        recyclerViewAddReview.adapter = addReviewRecyclerAdapter
    }

    private fun initListeners() {
    }

    private fun initViews(view: View) {
        recyclerViewAddReview = view.findViewById<RecyclerView>(R.id.recyclerViewPosts)
    }

    companion object {
        private lateinit var userName: String
        // newInstance constructor for creating fragment with arguments
        fun newInstance(name: String): InProgressTab? {
            userName = name
            return InProgressTab()
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}