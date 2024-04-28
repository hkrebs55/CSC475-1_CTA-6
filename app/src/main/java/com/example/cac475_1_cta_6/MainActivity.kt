package com.example.cac475_1_cta_6

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var gson: Gson = Gson()
    private var selectedDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE)
        print(sharedPreferences.all.values)
        setContentView(R.layout.activity_main)

        val calendarView: CalendarView = findViewById(R.id.calendarView)
        val addTaskButton: Button = findViewById(R.id.addTaskButton)
        initializeTaskList()

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            initializeTaskList()
        }

        addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private fun initializeTaskList() {
        val taskRecyclerView: RecyclerView = findViewById(R.id.taskList)

        if (sharedPreferences.all.isNotEmpty() && selectedDate != "") {
            taskRecyclerView.layoutManager = LinearLayoutManager(this)
            val listType: Type = object : TypeToken<List<Task>>() {}.type
            val tasksString = sharedPreferences.getString(selectedDate, "").toString().replace("[\"", "[").replace("\"]", "]").replace("\\", "").replace("}\",\"{", "},{")
            if (tasksString != "") {
                val taskList: List<Task> = gson.fromJson(tasksString, listType)
                taskRecyclerView.adapter = TaskListAdaptor(this, taskList)
            } else {
                val taskList: List<Task> = gson.fromJson("[]", listType)
                taskRecyclerView.adapter = TaskListAdaptor(this, taskList)
            }

        }
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            initializeTaskList()
        }
    }
}