package com.example.kego_client

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Pair
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tasksButton = findViewById<ImageView>(R.id.main_logo)
        tasksButton.setOnClickListener{
            val p1 = Pair.create(tasksButton as View, "logo_transition")
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this@MainActivity, p1)
            val intent = Intent(this@MainActivity, TasksActivity::class.java)
            startActivity(intent, options.toBundle())
        }

        val botInput = findViewById<TextInputLayout>(R.id.bot_input)
        botInput.editText?.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                if ('\n' in s.toString()){
                    botInput.editText?.setText("")
                    val p1 = Pair.create(tasksButton as View, "logo_transition")
                    val options = ActivityOptions
                        .makeSceneTransitionAnimation(this@MainActivity, p1)
                    val intent = Intent(this@MainActivity, BotActivity::class.java)
                    startActivity(intent, options.toBundle())
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })

        val toWidgetConfigButton = findViewById<ImageView>(R.id.to_widget_config)
        toWidgetConfigButton.setOnClickListener{
            val intent = Intent(this@MainActivity, TimerConfigActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }
}