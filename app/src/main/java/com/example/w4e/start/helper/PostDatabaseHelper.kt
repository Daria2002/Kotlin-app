package com.example.w4e.start.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.w4e.start.model.Category
import com.example.w4e.start.model.Post

class PostDatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // create table sql query
    private val CREATE_POST_TABLE = ("CREATE TABLE " + PostDatabaseHelper.TABLE_POST + "("
            + PostDatabaseHelper.COLUMN_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PostDatabaseHelper.COLUMN_USER_NAME + " TEXT,"
            + PostDatabaseHelper.COLUMN_TEXT + " TEXT," + PostDatabaseHelper.COLUMN_CATEGORY + " TEXT" + ")")

    // drop table sql query
    private val DROP_POST_TABLE = "DROP TABLE IF EXISTS ${PostDatabaseHelper.TABLE_POST}"

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "Posts.db"
        private val TABLE_POST = "Post"
        private val COLUMN_POST_ID = "post_id"
        // name of user that posted
        private val COLUMN_USER_NAME = "user_name"
        // post text
        private val COLUMN_TEXT = "text"
        private val COLUMN_CATEGORY = "category"

        fun titleToCategory(title: String): Category {
            for(c : Category in Category.values()) {
                if(c.title() == title) return c
            }
            return Category.GRAPHICS
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_POST_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop Post Table if exist
        db.execSQL(DROP_POST_TABLE)
        // Create tables again
        onCreate(db)
    }

    /**
     * Add post
     * @param post to add
     */
    fun addPost(post: Post) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(PostDatabaseHelper.COLUMN_USER_NAME, post.user_name)
        values.put(PostDatabaseHelper.COLUMN_CATEGORY, post.category.title())
        values.put(PostDatabaseHelper.COLUMN_TEXT, post.text)
        // Inserting row
        db.insert(PostDatabaseHelper.TABLE_POST, null, values)
        db.close()
    }

    /**
     * This method fetches all posts and returns the list of post records in given category
     * @return list
     */
    fun getPostsForCategory(c: String) : List<Post> {
        // arr of columns to fetch
        val columns = arrayOf(PostDatabaseHelper.COLUMN_POST_ID,
            PostDatabaseHelper.COLUMN_USER_NAME,
            PostDatabaseHelper.COLUMN_CATEGORY,
            PostDatabaseHelper.COLUMN_TEXT)
        // sorting orders
        val sortOrder = "${PostDatabaseHelper.COLUMN_CATEGORY} ASC"
        val postList = ArrayList<Post>()
        val db = this.readableDatabase
        // selection criteria
        val selection = "${PostDatabaseHelper.COLUMN_CATEGORY} = ?"
        // selection arg
        val selectionArgs = arrayOf(c)
        // query the user table
        val cursor = db.query(PostDatabaseHelper.TABLE_POST,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder)
        if(cursor.moveToFirst()) {
            do {
                val post = Post(id = cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_POST_ID)).toInt(),
                    user_name = cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_USER_NAME)),
                    category = titleToCategory(cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_CATEGORY))),
                    text = cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_TEXT)))
                postList.add(post)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return postList
    }

    /**
     * This method fetches all posts and returns the list of post records
     * @return list
     */
    fun getAllPosts() : List<Post> {
        // arr of columns to fetch
        val columns = arrayOf(PostDatabaseHelper.COLUMN_POST_ID,
            PostDatabaseHelper.COLUMN_USER_NAME,
            PostDatabaseHelper.COLUMN_CATEGORY,
            PostDatabaseHelper.COLUMN_TEXT)
        // sorting orders
        val sortOrder = "${PostDatabaseHelper.COLUMN_CATEGORY} ASC"
        val postList = ArrayList<Post>()
        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(PostDatabaseHelper.TABLE_POST,
            columns,
            null,
            null,
            null,
            null,
            sortOrder)
        if(cursor.moveToFirst()) {
            do {
                val post = Post(id = cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_POST_ID)).toInt(),
                    user_name = cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_USER_NAME)),
                    category = titleToCategory(cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_CATEGORY))),
                    text = cursor.getString(cursor.getColumnIndex(PostDatabaseHelper.COLUMN_TEXT)))
                postList.add(post)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return postList
    }
}