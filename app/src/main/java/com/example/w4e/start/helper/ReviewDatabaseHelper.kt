package com.example.w4e.start.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.w4e.start.model.Post
import com.example.w4e.start.model.Review

class ReviewDatabaseHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // create table sql query
    private val CREATE_REVIEW_TABLE = ("CREATE TABLE " + ReviewDatabaseHelper.TABLE_REVIEW + "("
            + ReviewDatabaseHelper.COLUMN_REVIEW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + ReviewDatabaseHelper.COLUMN_REVIEWER + " TEXT,"
            + ReviewDatabaseHelper.COLUMN_TEXT + " TEXT," + ReviewDatabaseHelper.COLUMN_REVIEWED_USER + " TEXT," + ReviewDatabaseHelper.COLUMN_TIME + " TEXT" + ")")

    // drop table sql query
    private val DROP_REVIEW_TABLE = "DROP TABLE IF EXISTS ${ReviewDatabaseHelper.TABLE_REVIEW}"

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "Review.db"
        private val TABLE_REVIEW = "Review"
        private val COLUMN_REVIEW_ID = "review_id"
        // name of reviewer
        private val COLUMN_REVIEWER = "reviewer"
        // review text
        private val COLUMN_TEXT = "text"
        // name of reviewed user
        private val COLUMN_REVIEWED_USER = "reviewed_user"
        private val COLUMN_TIME = "time"
        private var instance: ReviewDatabaseHelper? = null
        fun getInstance(context: Context): ReviewDatabaseHelper {
            if(instance == null)
            {
                instance = ReviewDatabaseHelper(context)
            }
            return instance!!
        }
    }

    /**
     * This method fetches all reviews that other users wrote for user with userName
     * @return list
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllReviews(userName: String): List<Review> {
        // arr of columns to fetch
        val columns = arrayOf(ReviewDatabaseHelper.COLUMN_REVIEW_ID,
            ReviewDatabaseHelper.COLUMN_REVIEWER,
            ReviewDatabaseHelper.COLUMN_TEXT,
            ReviewDatabaseHelper.COLUMN_REVIEWED_USER,
            ReviewDatabaseHelper.COLUMN_TIME)
        val reviewList = ArrayList<Review>()
        val db = this.readableDatabase
        // selection criteria
        val selection = "${ReviewDatabaseHelper.COLUMN_REVIEWED_USER} = ?"
        // selection arg
        val selectionArgs = arrayOf(userName)
        // query the user table
        val cursor = db.query(ReviewDatabaseHelper.TABLE_REVIEW,
            columns,
            selection,
            selectionArgs,
            null,
            null,
            null)
        if (cursor.moveToFirst()) {
            do {
                val review =
                    Review(id = cursor.getString(cursor.getColumnIndex(ReviewDatabaseHelper.COLUMN_REVIEW_ID)).toInt(),
                        reviewer = cursor.getString(cursor.getColumnIndex(ReviewDatabaseHelper.COLUMN_REVIEWER)),
                        text = cursor.getString(cursor.getColumnIndex(ReviewDatabaseHelper.COLUMN_TEXT)),
                        reviewed_user = cursor.getString(cursor.getColumnIndex(ReviewDatabaseHelper.COLUMN_REVIEWED_USER)),
                        time = cursor.getString(cursor.getColumnIndex(ReviewDatabaseHelper.COLUMN_TIME)))
                reviewList.add(review)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reviewList
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_REVIEW_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop Post Table if exist
        db.execSQL(DROP_REVIEW_TABLE)
        // Create tables again
        onCreate(db)
    }
}