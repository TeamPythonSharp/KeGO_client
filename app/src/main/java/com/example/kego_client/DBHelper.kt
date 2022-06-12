package com.example.kego_client

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        var DATABASE_VERSION = 1
        var DATABASE_NAME = "TasksDb"
        var TABLE_TASKS = "tasks"

        var KEY_ID = "_id"
        var KEY_NAME = "name"
        var KEY_START_TIME = "start_time"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_TASKS($KEY_ID integer primary key,$KEY_NAME text,$KEY_START_TIME text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_TASKS")
        onCreate(db)
    }
}