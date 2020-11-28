package com.example.w4e.start.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.model.Category

class CategoryAdapter(private val listCategories: MutableList<Category>, private val listener: View.OnClickListener) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category_recycler, parent, false)
        return CategoryViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryName.text = listCategories[position].title()
        holder.categoryName.transformationMethod = null
        holder.categoryName.setOnClickListener {
            it.id = position
            listener.onClick(it)
        }
    }

    override fun getItemCount(): Int = listCategories.size

    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var categoryName: AppCompatButton

        init {
            categoryName = view.findViewById(R.id.categoryName) as AppCompatButton
            categoryName.transformationMethod = null
        }
    }
}