package com.example.work

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.work.calender.DateEdit
import com.example.work.calender.DateList
import com.example.work.calender.DatePicker
import com.example.work.sql.Calender
import com.example.work.sql.CalenderAdapt
import com.example.work.sql.SpHelper
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), CalendarView.OnCalendarSelectListener ,AdapterView.OnItemClickListener{
    var calender: java.util.ArrayList<Calender> = ArrayList()
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter  = CalenderAdapt(this, R.layout.activity_sql_item, calender)
        val listview : ListView = findViewById(R.id.lv_main)
        listview.adapter = adapter
        val tvmain :TextView= findViewById(R.id.tv_main)
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnCalendarSelectListener(this)

        val date = Date(System.currentTimeMillis())
        val dateFormat: DateFormat = SimpleDateFormat("yyyy年MM月dd日")
        val dateFormat1: DateFormat = SimpleDateFormat("yyyyMMdd")
        val format: String = dateFormat.format(date)
        val format1: String = dateFormat1.format(date)
        tvmain.text=format
        select(format1)
        val fab :View = findViewById(R.id.fab_main_add)
        fab.setOnClickListener { view->
            val intent = Intent(this, DatePicker::class.java)
            startActivity(intent)
        }

        listview.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                onResume()
            }

//        listview.onItemLongClickListener=
//            AdapterView.OnItemLongClickListener { parent, view, position, id ->
//
//                val a=adapter.getItem(position)?.id
//                Log.d("putextradata",a.toString())
//                val intent = Intent(this, DateEdit::class.java)
//                intent.putExtra("data",a)
//                startActivity(intent)
//                adapter.notifyDataSetChanged()
//                return@OnItemLongClickListener true
//            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.switch_item1->{
                val intent = Intent(this,DateList::class.java)
                startActivity(intent)
            }
            R.id.statistic_item1->{
                val intent = Intent(this,Statistic::class.java)
                startActivity(intent)
            }
        }
        return true
    }


    private fun select(time: String) {
        val adapter  = CalenderAdapt(this, R.layout.activity_sql_item, calender)
        adapter.datalist.clear()
        var args = String()
        for((index,i) in time.withIndex()){
             if (index==3||index==5) {
                 args += i
                 args +="-"
            }
            else{
                args += i
             }
        }

        val helper  = SpHelper(this)
        val new = helper.query2(args)

        for (i in new){
            adapter.datalist.add(i)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {

        var select_data = Integer.toString(calendar!!.year)
        select_data += if (calendar!!.month / 10 == 0) {
            "0" + calendar!!.month
        } else {
            Integer.toString(calendar!!.month)
        }
        select_data += if (calendar!!.day / 10 == 0) {
            "0" + calendar!!.day
        } else {
            Integer.toString(calendar!!.day)
        }

        var select_data1 = Integer.toString(calendar!!.year)+"年"
        select_data1 += if (calendar!!.month / 10 == 0) {
            "0" + calendar!!.month + "月"
        } else {
            Integer.toString(calendar!!.month) + "月"
        }
        select_data1 += if (calendar!!.day / 10 == 0) {
            "0" + calendar!!.day + "日"
        } else {
            Integer.toString(calendar!!.day) + "日"
        }
        val tvmain = findViewById<TextView>(R.id.tv_main)
        tvmain.text = select_data1
        val listView: ListView = findViewById(R.id.lv_main)
        val adapter  = CalenderAdapt(this, R.layout.activity_sql_item, calender)
        listView.adapter = adapter
        listView.onItemClickListener = this
        select(select_data)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onResume() {
        super.onResume()
        val listView: ListView = findViewById(R.id.lv_main)
        val adapter  = CalenderAdapt(this, R.layout.activity_sql_item, calender)
        listView.adapter = adapter
        listView.onItemClickListener = this
        val date = Date(System.currentTimeMillis())
        val dateFormat1: DateFormat = SimpleDateFormat("yyyyMMdd")
        val format1: String = dateFormat1.format(date)

        select(format1)
    }
}