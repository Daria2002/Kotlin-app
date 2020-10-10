package com.example.w4e.start.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.w4e.start.model.User

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    // create table sql query
    private val CREATE_USER_TABLE = ("CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")")

    // drop table sql query
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop User Table if exist
        db.execSQL(DROP_USER_TABLE)
        // Create tables again
        onCreate(db)
    }

    fun getUser(email: String?) : String {
        // arr of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)
        // sorting orders
        val sortOrder = "$COLUMN_USER_EMAIL ASC"
        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(TABLE_USER,
            columns,
            null,
            null,
            null,
            null,
            sortOrder)
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)) == email) {
                    val name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
                    return cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME))
                }
            } while (cursor.moveToNext())
        }
        return ""
    }

    /**
     * This method is to fetch all user and return the list of user records
     * @return list
     */
    fun getAllUser() : List<User> {
        // arr of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID, COLUMN_USER_EMAIL, COLUMN_USER_NAME, COLUMN_USER_PASSWORD)
        // sorting orders
        val sortOrder = "$COLUMN_USER_NAME ASC"
        val userList = ArrayList<User>()
        val db = this.readableDatabase
        // query the user table
        val cursor = db.query(TABLE_USER,
            columns,
            null,
            null,
            null,
            null,
            sortOrder)
        if(cursor.moveToFirst()) {
            do {
                val user = User(id = cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID)).toInt(),
                                name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)),
                                email = cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                                password = cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)))
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return userList
    }

    /**
     * This method is to create user record
     * @param user
     */
    fun addUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        // Inserting row
        db.insert(TABLE_USER, null, values)
        db.close()
    }

    /**
     * This method updates user record
     * @param user
     */
    fun updateUser(user: User) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.name)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, user.password)
        // updating row
        db.update(TABLE_USER, values, "$COLUMN_USER_ID = ?",
                arrayOf(user.id.toString()))
        db.close()
    }

    /**
     * This method deletes user record
     * @param user
     */
    fun deleteUser(user: User) {
        val db = this.writableDatabase
        // delete user record by id
        db.delete(TABLE_USER, "$COLUMN_USER_ID = ?", arrayOf(user.id.toString()))
        db.close()
    }

    /**
     * This method checks is user exists or not
     * @param email
     * @return true/false
     */
    fun checkUser(email: String): Boolean {
        // arr of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.writableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ?"
        // selection arg
        val selectionArgs = arrayOf(email)
        // query user table with condition
        /**
         * query function fetches records from user table
         * sql query of this function:
         * SELECT user_ide FROM user WHERE user_email = 'jack@app.com'
         */
        val cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null)

        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if(cursorCount > 0) {
            return true
        }
        return false
    }

    /**
     * This method checks if user exists or not
     * @param email
     * @param password
     * @return true/false
     */
    fun checkUser(email: String, password: String): Boolean {
        // arr of columns to fetch
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        // selection criteria
        val selection = "$COLUMN_USER_EMAIL = ? AND $COLUMN_USER_PASSWORD = ?"
        // selection args
        val selectionArgs = arrayOf(email, password)
        // query user table with conditions
        val cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null)
        val cursorCount = cursor.count
        cursor.close()
        db.close()
        if(cursorCount > 0) return true
        return false
    }

    companion object {
        // Database Version
        private val DATABASE_VERSION = 1
        // Database Name
        private val DATABASE_NAME = "UserManager.db"
        // User table name
        private val TABLE_USER = "User"
        // User Table Columns names
        private val COLUMN_USER_ID = "user_id"
        private val COLUMN_USER_NAME = "user_name"
        private val COLUMN_USER_EMAIL = "user_email"
        private val COLUMN_USER_PASSWORD = "user_password"
    }
}