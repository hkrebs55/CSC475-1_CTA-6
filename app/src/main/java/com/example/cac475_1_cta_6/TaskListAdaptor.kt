package com.example.cac475_1_cta_6

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TaskListAdaptor(private val context: Context, private val task: List<Task>) : RecyclerView.Adapter<TaskListAdaptor.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int {
        return task.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val screenWidth = context.resources.displayMetrics.widthPixels
        holder.taskContainerView.layoutParams.width = screenWidth
        holder.taskNameView.text = task[position].taskName
        holder.taskDescriptionView.text = task[position].taskDescription
        holder.taskCompletedView.isChecked = task[position].completed
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskContainerView: ConstraintLayout = itemView.findViewById(R.id.taskContainer)
        val taskNameView: TextView = itemView.findViewById(R.id.taskName)
        val taskDescriptionView: TextView = itemView.findViewById(R.id.taskDescription)
        val taskCompletedView: CheckBox = itemView.findViewById(R.id.taskCompleted)
    }
}