package com.example.kego_client

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class TasksActivity: AppCompatActivity() {
    override fun finish() {
        super.finish()
        DBHelper.close()
    }

    private lateinit var tasks: RecyclerView
    private lateinit var taskNameField: TextInputLayout
    private lateinit var startTimeField: TextInputLayout
    private lateinit var durationField: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        taskNameField = findViewById(R.id.task_name_field)
        startTimeField = findViewById(R.id.start_time_field)
        durationField = findViewById(R.id.duration_field)
        DBHelper.init(this)

        tasks = findViewById(R.id.tasks)
        tasks.layoutManager = LinearLayoutManager(this)
        updateRecycler()

        val buttonPlus = findViewById<ImageView>(R.id.button_plus)
        buttonPlus.setOnClickListener {
            DBHelper.addElement(
                taskNameField.editText?.text.toString(),
                startTimeField.editText?.text.toString(),
                durationField.editText?.text.toString()
            )
            updateRecycler()
        }
    }

    private fun updateRecycler() {
        tasks.adapter =
            CustomRecyclerAdapter(DBHelper.loadList(), ::updateRecycler)
    }
}