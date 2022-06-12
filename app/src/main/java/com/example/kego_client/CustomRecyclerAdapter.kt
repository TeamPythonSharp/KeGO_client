package com.example.kego_client

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class CustomRecyclerAdapter(
    private val listData: List<TaskData>,
    private val updateRecycler: () -> Unit
) : RecyclerView
.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var id: Int = 0
        private val taskName = itemView.findViewById<TextInputLayout>(R.id.task_name)
        private val startTime = itemView.findViewById<TextInputLayout>(R.id.start_time)
        private val duration = itemView.findViewById<TextInputLayout>(R.id.duration)
        private val taskDelete = itemView.findViewById<ImageView>(R.id.task_delete)

        fun bind(data: TaskData) {
            id = data.id
            taskName.editText?.setText(data.name)
            startTime.editText?.setText(data.startTime)
            duration.editText?.setText(data.duration)

            taskDelete.setOnClickListener {
                DBHelper.deleteElement(id)
                updateRecycler()
            }

            taskName.editText?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    DBHelper.updateElement(
                        taskName.editText?.text.toString(),
                        id,
                        DBHelper.KEY_NAME
                    )
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
            startTime.editText?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    DBHelper.updateElement(
                        taskName.editText?.text.toString(),
                        id,
                        DBHelper.KEY_START_TIME
                    )
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
            duration.editText?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    DBHelper.updateElement(
                        taskName.editText?.text.toString(),
                        id,
                        DBHelper.KEY_DURATION
                    )
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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount() = listData.size
}