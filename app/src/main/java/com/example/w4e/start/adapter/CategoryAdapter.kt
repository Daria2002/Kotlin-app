package com.example.w4e.start.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.model.Category
import com.google.android.instantapps.InstantApps

class CategoryAdapter(
    private val activity: Activity,
    private val onItemClickListener: AdapterView.OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val resources = activity.resources
    private val layoutInflater = LayoutInflater.from(activity)
    private var categories = Category.values()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(layoutInflater
            .inflate(R.layout.item_category, parent, false) as ViewGroup)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView) {
            categories[position].run {
                setCategoryIcon(this, holder.categoryIcon)
                holder.categoryTitle.run {
                    text = title()
                }
            }
        }
    }

    fun getItem(position: Int): Category = categories[position]

    private fun setCategoryIcon(category: Category, icon: ImageView) {
        val packageName =
            if(InstantApps.isInstantApp(activity))
                "${activity.packageName}.categories"
            else activity.packageName
        val imageRes = resources.getIdentifier(
            "icon_category_${category.id}",
            "drawable",
            "$packageName.categories")
        icon.setImageResource(imageRes)
    }

    override fun getItemCount(): Int = categories.size

    class ViewHolder(categoryView: ViewGroup) : RecyclerView.ViewHolder(categoryView) {
        val categoryIcon: ImageView = categoryView.findViewById(R.id.category_icon)
        val categoryTitle: TextView = categoryView.findViewById(R.id.category_title)
    }
}