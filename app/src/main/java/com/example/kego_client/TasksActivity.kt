package com.example.kego_client

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.example.kego_client.DataClasses.ListData

class TasksActivity: AppCompatActivity() {
    override fun finish() {
        super.finish()
        dbHelper.close()
    }

    private lateinit var dbHelper: DBHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var tasks: RecyclerView
    private lateinit var taskNameField: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        taskNameField = findViewById(R.id.task_name_field)
        dbHelper = DBHelper(this)
        database = dbHelper.writableDatabase

        tasks = findViewById(R.id.tasks)
        tasks.layoutManager = LinearLayoutManager(this)
        tasks.adapter = CustomRecyclerAdapter(loadList(), ::deleteFun, ::updateFun)

        val buttonPlus = findViewById<ImageView>(R.id.button_plus)
        buttonPlus.setOnClickListener {
            addObj()
            updateRecycler()
        }
    }

    private fun updateRecycler() {
        tasks.adapter =
            CustomRecyclerAdapter(loadList(), ::deleteFun, ::updateFun)
    }

    private fun deleteFun(id: Int) {
        val sql = "delete from ${DBHelper.TABLE_TASKS} where ${DBHelper.KEY_ID} = $id;"
        database.execSQL(sql)
        updateRecycler()
    }

    private fun updateFun(text: String, id: Int) {
        val sql = "update ${DBHelper.TABLE_TASKS} set ${DBHelper.KEY_NAME} = '$text' where ${DBHelper.KEY_ID} = $id;"
        database.execSQL(sql)
    }

    private fun deleteList() {
        database.delete(DBHelper.TABLE_TASKS, null, null)
    }

    private fun addObj() {
        val contentValues = ContentValues()
        contentValues.put(DBHelper.KEY_NAME, taskNameField.editText?.text.toString())
        contentValues.put(DBHelper.KEY_START_TIME, "00:00")
        database.insert(DBHelper.TABLE_TASKS, null, contentValues)
    }

    private fun loadList(): List<ListData> {
        val cursor = database.query(DBHelper.TABLE_TASKS, null, null, null, null, null, null)

        val data = mutableListOf<ListData>()
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val indexId = cursor.getColumnIndex(DBHelper.KEY_ID)
                val nameId = cursor.getColumnIndex(DBHelper.KEY_NAME)
                val name = cursor.getString(nameId)
                data.add(ListData(cursor.getInt(indexId), name))
                cursor.moveToNext()
            }
        }
        cursor.close()
        return data
    }
}