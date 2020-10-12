package com.example.w4e.start.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.model.User

class CategoryAdapter(private val listCategories: MutableList<User>) : RecyclerView.Adapter<CategoryAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category_recycler, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.textViewName.text = listCategories[position].name
        holder.textViewEmail.text = listCategories[position].email
        holder.textViewPassword.text = listCategories[position].password
    }

    override fun getItemCount(): Int = listCategories.size

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textViewName: AppCompatTextView
        var textViewEmail: AppCompatTextView
        var textViewPassword: AppCompatTextView

        init {
            textViewName = view.findViewById(R.id.textViewName) as AppCompatTextView
            textViewEmail = view.findViewById(R.id.textViewEmail) as AppCompatTextView
            textViewPassword = view.findViewById(R.id.textViewPassword) as AppCompatTextView
        }
    }
}