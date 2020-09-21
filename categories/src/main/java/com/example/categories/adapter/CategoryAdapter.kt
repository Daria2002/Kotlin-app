package com.example.categories.adapter

import android.app.Activity
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.categories.R

class CategoryAdapter(
    private val activity: Activity,
    private val onItemClickListener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    class ViewHolder(categoryView: ViewGroup) : RecyclerView.ViewHolder(categoryView) {
        val categoryIcon: ImageView = categoryView.findViewById(R.id.category_icon)
        val categoryTitle: TextView = categoryView.findViewById(R.id.category_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}