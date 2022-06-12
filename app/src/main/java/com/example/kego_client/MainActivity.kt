package com.example.kego_client

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.util.Pair
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tasksButton = findViewById<ImageView>(R.id.main_logo)
        tasksButton.setOnClickListener{
            val p1 = Pair.create(tasksButton as View, "logo_transition")
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, p1)
            val intent = Intent(this@MainActivity, TasksActivity::class.java)
            startActivity(intent, options.toBundle())
        }
    }
}