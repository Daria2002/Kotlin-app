package com.example.w4e.start.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.adapter.ReviewAdapter
import com.example.w4e.start.helper.ReviewDatabaseHelper
import com.example.w4e.start.model.Review

class ReviewTab: Fragment(), OnClickListener {
    private lateinit var recyclerViewReview: RecyclerView
    private lateinit var listReview: List<Review>
    private lateinit var reviewRecyclerAdapter: ReviewAdapter

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
        recyclerViewReview.layoutManager = mLayoutManager
        recyclerViewReview.itemAnimator = DefaultItemAnimator()
        recyclerViewReview.setHasFixedSize(true)
        listReview = mutableListOf<Review>()
        listReview = ReviewDatabaseHelper.getInstance(context).getAllReviews(ReviewTab.userName)
        updatePostsInGui()
    }

    private fun updatePostsInGui() {
        reviewRecyclerAdapter = ReviewAdapter(listReview as MutableList<Review>, this)
        recyclerViewReview.adapter = reviewRecyclerAdapter
    }

    private fun initListeners() {
    }

    private fun initViews(view: View) {
        recyclerViewReview = view.findViewById<RecyclerView>(R.id.recyclerViewPosts)
    }

    companion object {
        private lateinit var userName: String
        // newInstance constructor for creating fragment with arguments
        fun newInstance(name: String): ReviewTab? {
            userName = name
            return ReviewTab()
        }
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}