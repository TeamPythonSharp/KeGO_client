package com.example.kego_client

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class TaskData(val id: Int, val name: String, val startTime: String, val duration: String)

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {

        var DATABASE_VERSION = 1
        var DATABASE_NAME = "TasksDb"

        lateinit var database: SQLiteDatabase
        private lateinit var dbHelper: DBHelper
        fun init(context: Context) {
            dbHelper = DBHelper(context)
            database = dbHelper.writableDatabase
        }

        fun close() {
            dbHelper.close()
        }

        fun deleteElement(id: Int) {
            val sql = "delete from $TABLE_TASKS where $KEY_ID = $id;"
            database.execSQL(sql)
        }

        fun updateElement(text: String, id: Int, key: String) {
            val sql = "update $TABLE_TASKS set $key = '$text' where $KEY_ID = $id;"
            database.execSQL(sql)
        }

        fun deleteList() {
            database.delete(TABLE_TASKS, null, null)
        }

        var TABLE_TASKS = "tasks"
        var KEY_ID = "_id"
        var KEY_NAME = "name"
        var KEY_START_TIME = "start_time"
        var KEY_DURATION = "duration"
        fun addElement(name: String, startTime: String, duration: String) {
            val contentValues = ContentValues()
            contentValues.put(KEY_NAME, name)
            contentValues.put(KEY_START_TIME, startTime)
            contentValues.put(KEY_DURATION, duration)
            database.insert(TABLE_TASKS, null, contentValues)
        }

        fun loadList(): List<TaskData> {
            val cursor =
                database.query(TABLE_TASKS, null, null, null, null, null, null)

            val data = mutableListOf<TaskData>()
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val indexId = cursor.getColumnIndex(KEY_ID)
                    val nameId = cursor.getColumnIndex(KEY_NAME)
                    val startTimeId = cursor.getColumnIndex(KEY_START_TIME)
                    val durationId = cursor.getColumnIndex(KEY_DURATION)
                    val name = cursor.getString(nameId)
                    val startTime = cursor.getString(startTimeId)
                    val duration = cursor.getString(durationId)
                    data.add(TaskData(cursor.getInt(indexId), name, startTime, duration))
                    cursor.moveToNext()
                }
            }
            cursor.close()
            return data
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table $TABLE_TASKS($KEY_ID integer primary key,$KEY_NAME text,$KEY_START_TIME text,$KEY_DURATION text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists $TABLE_TASKS")
        onCreate(db)
    }
}