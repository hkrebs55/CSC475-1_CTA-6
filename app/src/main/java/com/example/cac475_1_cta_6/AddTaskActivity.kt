package com.example.cac475_1_cta_6

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.google.gson.JsonArray;
import java.util.UUID

class AddTaskActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        sharedPreferences = getSharedPreferences("TaskPrefs", Context.MODE_PRIVATE)
        var selectedDate = ""
        var taskName = ""
        var taskDescription = ""

        findViewById<CalendarView>(R.id.calendarView).setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }


        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }

        findViewById<TextInputEditText>(R.id.taskNameInput).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                taskName = s.toString()
            }
        })

        findViewById<TextInputEditText>(R.id.taskDescriptionInput).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                taskDescription = s.toString()
            }
        })

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            if (taskName != "" && taskDescription != "" && selectedDate != "") {
                saveTask(selectedDate, taskName, taskDescription)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }

    private fun saveTask(date: String, taskName: String, taskDescription: String) {
        val editor = sharedPreferences.edit()
        val newTask = gson.toJson(Task(date, taskName, taskDescription, false, UUID.randomUUID()))
        val existingTasks = sharedPreferences.getString(date, null)
        val taskList: JsonArray?

        if (existingTasks == null) {
            taskList = JsonArray();
            taskList.add(newTask)
        } else {
            taskList = gson.fromJson(existingTasks, JsonArray::class.java);
            taskList.add(newTask)
        }

        editor.putString(date, gson.toJson(taskList))
        editor.apply()
    }
}