package com.example.base.persistence

import android.provider.BaseColumns

object CategoryTable {
    const val NAME = "category"
    const val COLUMN_ID = BaseColumns._ID
    const val COLUMN_NAME = "name"
    const val COLUMN_POST = "post" // text of post that person with name posted
    @JvmField val PROJECTION = arrayOf(COLUMN_ID, COLUMN_NAME, COLUMN_POST)
    @JvmField val CREATE = """CREATE TABLE $NAME ($COLUMN_ID TEXT PRIMARY KEY,
|                               $COLUMN_NAME TEXT NOT NULL, $COLUMN_POST TEXT);"""
}