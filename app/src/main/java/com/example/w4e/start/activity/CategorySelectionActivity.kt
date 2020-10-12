package com.example.w4e.start.activity

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.w4e.start.R
import com.example.w4e.start.adapter.CategoryAdapter
import com.example.w4e.start.helper.DatabaseHelper
import com.example.w4e.start.model.User

class CategorySelectionActivity : AppCompatActivity() {
    private val activity = this@CategorySelectionActivity
    private lateinit var textViewName: AppCompatTextView
    private lateinit var recyclerViewUsers: RecyclerView
    private lateinit var listUsers: MutableList<User>
    private lateinit var usersRecyclerAdapter: CategoryAdapter
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        supportActionBar!!.title = ""
        initViews()
        initObjects()
    }

    private fun initObjects() {
        listUsers = ArrayList()
        databaseHelper = DatabaseHelper(activity)
        listUsers = databaseHelper.getAllUser().toMutableList()
        usersRecyclerAdapter = CategoryAdapter(listUsers)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerViewUsers.layoutManager = mLayoutManager
        recyclerViewUsers.itemAnimator = DefaultItemAnimator()
        recyclerViewUsers.setHasFixedSize(true)
        recyclerViewUsers.adapter = usersRecyclerAdapter
        val emailFromIntent = intent.getStringExtra("EMAIL")
        textViewName.text = databaseHelper.getUser(emailFromIntent)
        /*
        var getDataFromSQLite = GetDataFromSQLite()
        getDataFromSQLite.execute()
         */
    }

    /**
     * This class fetches all user records from SQLite
     */
    inner class GetDataFromSQLite : AsyncTask<Void, Void, List<User>>() {
        override fun doInBackground(vararg p0: Void?): List<User> {
            return databaseHelper.getAllUser()
        }

        override fun onPostExecute(result: List<User>?) {
            super.onPostExecute(result)
            listUsers.clear()
            listUsers.addAll(result!!)
        }
    }

    private fun initViews() {
        textViewName = findViewById(R.id.textViewName) as AppCompatTextView
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers) as RecyclerView
    }
}