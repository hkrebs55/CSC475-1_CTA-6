package com.example.cac475_1_cta_6

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SelectedDateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_date)

        val selectedDate = intent.getStringExtra("selectedDate")
        val textViewSelectedDate: TextView = findViewById(R.id.textViewSelectedDate)
        textViewSelectedDate.text = selectedDate

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}