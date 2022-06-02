package com.example.work

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Statistic : AppCompatActivity() {

    lateinit var tvdateshow : TextView
    lateinit var tvtimeshow : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stactistic)

        tvdateshow = findViewById(R.id.tv_dateshow_statistic)
        tvtimeshow = findViewById(R.id.tv_timeshow_statistic)

        tvdateshow.setOnClickListener {
            showDatePickerDialog(this, 2, tvdateshow, Calendar.getInstance())
        }

        tvtimeshow.setOnClickListener {
            showDatePickerDialog(this, 2, tvtimeshow, Calendar.getInstance())

        }
    }

    /**
     * 日期选择
     * @param activity
     * @param themeResId
     * @param tv
     * @param calendar
     */
    fun showDatePickerDialog(
        activity: Activity?,
        themeResId: Int,
        tv: TextView,
        calendar: Calendar,
    ) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        DatePickerDialog(activity!!,
            themeResId,
            object : DatePickerDialog.OnDateSetListener {

//                val  builder: AlertDialog.Builder = AlertDialog.Builder(context)

                // 绑定监听器(How the parent is notified that the date is set.)
                override fun onDateSet(
                    view: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int,
                ) {
                    // 此处得到选择的时间，可以进行你想要的操作
                    if (tvdateshow.text.toString()!= ""){
                        var current_date = tvdateshow.text
                        var current_time = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ""
                        if (current_date.toString().length==9){
                            if (current_date[6]=='-'){
                                current_date=current_date.substring(0,5)+"0"+current_date.substring(5,9)
                            }
                            else
                                current_date=current_date.substring(0,8)+"0"+current_date.substring(8,9)
                        }
                        else if(current_date.length==8)
                            current_date = current_date.substring(0,5)+"0"+current_date.substring(5,7)+"0"+current_date.substring(7,8)
                        else
                            current_date = current_date
                        if (current_time.length==9){
                            if (current_time[6]=='-'){
                                current_time=current_time.substring(0,5)+"0"+current_time.substring(5,9)
                            }
                            else
                                current_time=current_time.substring(0,8)+"0"+current_time.substring(8,9)
                        }
                        else if(current_time.length==8)
                            current_time = current_time.substring(0,5)+"0"+current_time.substring(5,7)+"0"+current_time.substring(7,8)
                        else
                            current_time = current_time

//                        Log.d("current12",current_time.replace("-","")+"LLLL"+current_date.toString().replace("-",""))
                        if(current_time.replace("-","").toInt() < current_date.toString().replace("-","").toInt()){

                            Toast.makeText(view?.context,"结束时间不可早于初始时间,请重新选择时间", Toast.LENGTH_SHORT).show()
                            return
                        }
                        else{
                            tv.text = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ""
                        }
                    }
                    else
                        tv.text = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ""

                }

            } // 设置初始日期
            ,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))
            .show()
    }

}