package com.example.job

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar : View = findViewById(R.id.calendar)
        calendar.setOnClickListener {
            Toast.makeText(this,"你点击了",Toast.LENGTH_SHORT).show()
        }
        val calendarview = CalendarView(this)
        val listener = calendarview.
        calendarview.setOnDateChangeListener()
    }
}