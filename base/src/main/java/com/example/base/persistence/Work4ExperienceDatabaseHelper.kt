package com.example.base.persistence

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.view.SurfaceControl
import com.example.base.R
import com.example.base.model.Category
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class Work4ExperienceDatabaseHelper private constructor(
    context: Context
) : SQLiteOpenHelper(context.applicationContext, "Work4Experience.db", null, 1) {
    private val resources: Resources = context.resources
    private val categories: MutableList<Category> = loadCategories()

    override fun onCreate(db: SQLiteDatabase) {
        with(db) {
            execSQL(CategoryTable.CREATE)
            fillCategories(db)
        }
    }

    @Throws(JSONException::class, IOException::class)
    private fun fillCategories(db: SQLiteDatabase) {
        db.transact {
            val values = ContentValues()
            val jsonArray = JSONArray(readCategoriesFromResources())
        }
    }

    private fun readCategoriesFromResources(): String {
        val rawCategories = resources.openRawResource(R.raw.categories)
        val reader = BufferedReader(InputStreamReader(rawCategories))

        return reader.readText()
    }

    private fun loadCategories(): MutableList<Category> {
        val data = getCategoryCursor()
        val tmpCategories = ArrayList<Category>(data.count)
        do {
            tmpCategories.add(getCategory(data))
        } while (data.moveToNext())
        return  tmpCategories
    }

    /**
     * Gets all categories wrapped in a [Cursor] positioned at it's 1st el
     * @return All categories stored in the db
     */
    private fun getCategoryCursor(): Cursor {
        synchronized(readableDatabase) {
            return readableDatabase
                .query(CategoryTable.NAME, CategoryTable.PROJECTION,
                null, null, null, null, null)
                .also {
                    it.moveToFirst()
                }
        }
    }

    /**
     * Gets a category from the given position of the cursor provided
     * @param cursor The Cursor containing the data
     * @return The found category
     */
    private fun getCategory(cursor: Cursor): Category {
        // "magic numbers" based on CategoryTable#PROJECTION
        with(cursor) {
            val id = getString(0)
            val category = Category(
                id = id,
                name = getString(1),
                post = getString(200)
            )
            return category
        }
    }
}

inline fun SQLiteDatabase.transact(transaction: SQLiteDatabase.() -> Unit) {
    try {
        beginTransaction()
        transaction()
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}