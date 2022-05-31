package com.example.work.calender

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.work.sql.Calender
import com.example.work.sql.CalenderAdapt
import com.example.work.sql.SpHelper
import com.example.work.R


class DateList : AppCompatActivity() {
  var calender: java.util.ArrayList<Calender> = ArrayList()
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_list)
        val helper = SpHelper(this)
         calender = helper.query()
            Log.d("calendarAdapter", "getView: "+calender.size)
        val adapter  = CalenderAdapt(this, R.layout.activity_sql_item, calender )
        val listview :ListView = findViewById(R.id.date_listview)
        listview.adapter = adapter
        val fab :View = findViewById(R.id.fab_add)
        fab.setOnClickListener { view->
            val intent = Intent(this, DatePicker::class.java)
            startActivity(intent)
        }
        listview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                onResume()
            }

        listview.onItemLongClickListener=
            AdapterView.OnItemLongClickListener { parent, view, position, id ->

                val a=adapter.getItem(position)?.id
                Log.d("putextradata",a.toString())
                val intent = Intent(this, DateEdit::class.java)
                intent.putExtra("data",a)
                startActivity(intent)
                adapter.notifyDataSetChanged()
                return@OnItemLongClickListener true
        }


    }

    override fun onResume() {
        super.onResume()

        var calender = ArrayList<Calender>()
        val helper = SpHelper(this)
        calender = helper.query()

        val adapter  = CalenderAdapt(this, R.layout.activity_sql_item, calender, )
        val listview :ListView = findViewById(R.id.date_listview)
        listview.adapter = adapter

    }


}