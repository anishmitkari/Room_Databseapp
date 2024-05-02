package com.coding.meet.todo_app.adapters

import android.media.browse.MediaBrowser.ItemCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anish.codingmeettodoapp.R
import com.anish.codingmeettodoapp.models.Task
import java.text.SimpleDateFormat
import java.util.Locale

class TaskRecyclerViewAdapter(private val delete_updateCallback: (type:String,position: Int, task: Task) -> Unit) :
    ListAdapter<Task, TaskRecyclerViewAdapter.ViewHolder>(DiffCallback()) {


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
        val tasklist = getItem(position)
        holder.titletext.text = tasklist.title
        holder.descrTxt.text = tasklist.description

        val dateformat = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a", Locale.getDefault())
        holder.dateTxt.text = dateformat.format(tasklist.date)
        holder.editdelete.setOnClickListener {
            if (holder.adapterPosition != -1) {

                delete_updateCallback("delete",holder.adapterPosition, tasklist)
            }
        }
        holder.editImg.setOnClickListener {
            if (holder.adapterPosition != -1) {

                delete_updateCallback("update",holder.adapterPosition, tasklist)
            }
        }
    }




    // Diff callback

    class DiffCallback :DiffUtil.ItemCallback<Task>(){
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
return oldItem.id==newItem.id

        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
return oldItem == newItem
        }

    }
}