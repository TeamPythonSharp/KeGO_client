package com.example.kego_client

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kego_client.DataClasses.ListData
import com.google.android.material.textfield.TextInputLayout


class CustomRecyclerAdapter(
    private val listData: List<ListData>,
    private val deleteFun: (Int) -> Unit,
    private val updateFun: (String, Int) -> Unit,
) : RecyclerView
.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var id: Int = 0
        private val taskName = itemView.findViewById<TextInputLayout>(R.id.task_name)
        private val taskDelete = itemView.findViewById<ImageView>(R.id.task_delete)

        fun bind(data: ListData) {
            id = data.id
            taskName.editText?.setText(data.name)

            taskDelete.setOnClickListener {
                deleteFun(id)
            }

            taskName.editText?.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    updateFun(taskName.editText?.text.toString(), id)
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