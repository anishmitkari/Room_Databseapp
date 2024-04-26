package com.coding.meet.todo_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anish.codingmeettodoapp.R
import com.anish.codingmeettodoapp.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRecyclerViewAdapter(private val deleteCallback: (position: Int, task: Task) -> Unit) :
    RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder>() {

    private val taskList = arrayListOf<Task>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titletext: TextView = itemView.findViewById(R.id.titleTxt)
        val descrTxt: TextView = itemView.findViewById(R.id.descrTxt)
        val dateTxt: TextView = itemView.findViewById(R.id.dateTxt)
        val editdelete: ImageView = itemView.findViewById(R.id.editdelete)
        val editImg: ImageView = itemView.findViewById(R.id.editImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.view_task_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tasklist = taskList[position]
        holder.titletext.text = tasklist.title
        holder.descrTxt.text = tasklist.description

        val dateformat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
        holder.dateTxt.text = dateformat.format(tasklist.date)
        holder.editdelete.setOnClickListener {
            if (holder.adapterPosition != -1) {

                deleteCallback(holder.adapterPosition, tasklist)
            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun addAlltask(newTaskList: List<Task>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }
}