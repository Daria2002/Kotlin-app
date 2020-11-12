package com.example.w4e.start.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.w4e.start.R

class Review: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // TODO: create table with reviews (db with username and reviews)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false)
    }

    companion object {
        private lateinit var userName: String
        // newInstance constructor for creating fragment with arguments
        fun newInstance(name: String): Review? {
            userName = name
            return Review()
        }
    }
}