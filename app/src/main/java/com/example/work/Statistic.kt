package com.example.work

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.work.calender.DateList
import com.example.work.sql.SpHelper
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Statistic : AppCompatActivity() {

    lateinit var tvdateshow : TextView
    lateinit var tvtimeshow : TextView
    lateinit var pieChart: PieChart
    lateinit var barChart: BarChart
    val One = 1
    val Two = 2
    val Three = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stactistic)

        tvdateshow = findViewById(R.id.tv_dateshow_statistic)
        tvtimeshow = findViewById(R.id.tv_timeshow_statistic)
        pieChart = findViewById(R.id.pie_chart)
        barChart = findViewById(R.id.bar_chart)

        tvdateshow.setOnClickListener {
            showDatePickerDialog(this, 2, tvdateshow, Calendar.getInstance())
        }

        tvtimeshow.setOnClickListener {
            showDatePickerDialog(this, 2, tvtimeshow, Calendar.getInstance())
        }
    }

    fun chart(){
        val helper = SpHelper(this)
        val data = helper.queryDate("state",tvdateshow.text.toString(),tvtimeshow.text.toString())
        val cancel = helper.queryDate("cancel",tvdateshow.text.toString(),tvtimeshow.text.toString())
        val expired = helper.queryDate("expired",tvdateshow.text.toString(),tvtimeshow.text.toString())

        val barEntries : ArrayList<BarEntry> = ArrayList()
        val pieEntries : ArrayList<PieEntry> = ArrayList()
        val barEntry1 = BarEntry(One.toFloat(),data.toFloat())
        val barEntry2 = BarEntry(Two.toFloat(),cancel.toFloat())
        val barEntry3 = BarEntry(Three.toFloat(),expired.toFloat())
        val pieEntry1 = PieEntry(data.toFloat(),"完成",data.toFloat())
        val pieEntry2 = PieEntry(cancel.toFloat(),"取消",cancel.toFloat())
        val pieEntry3 = PieEntry(expired.toFloat(),"延期",expired.toFloat())
        if (data != 0){
            barEntries.add(barEntry1)
            pieEntries.add(pieEntry1)
        }
        if (cancel != 0){
            barEntries.add(barEntry2)
            pieEntries.add(pieEntry2)
        }
        if (expired != 0){
            barEntries.add(barEntry3)
            pieEntries.add(pieEntry3)
        }

        val barDataSet = BarDataSet(barEntries, "完成/取消/延期")
        barDataSet.colors = mutableListOf(
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53)
        )
        barDataSet.setDrawValues(false)
        barChart.data= BarData(barDataSet)
//        数据出现时间，括号里的值越大越慢
//        barChart.animateY(5000)
        barChart.description.text="柱状图"
        barChart.setNoDataText("无数据")
        barChart.description.textColor= Color.BLUE
        barChart.invalidate()

        val pieDataSet = PieDataSet(pieEntries, "饼图")
        pieDataSet.colors = mutableListOf(
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0)
        )
        pieChart.data= PieData(pieDataSet)
//        pieChart.animateXY(100,100)
        pieChart.centerText = "统计数据"
        pieChart.setNoDataText("无数据")
        pieChart.setEntryLabelColor(Color.rgb(0,0,0))
        pieChart.description.isEnabled=false
        pieChart.invalidate()
    }
    fun chartInit(dateStart: String, dateEnd: String){
        val helper = SpHelper(this)
        val data = helper.queryDate("state",dateStart,dateEnd)
        val cancel = helper.queryDate("cancel",dateStart,dateEnd)
        val expired = helper.queryDate("expired",dateStart,dateEnd)

        val barEntries : ArrayList<BarEntry> = ArrayList()
        val pieEntries : ArrayList<PieEntry> = ArrayList()
        val barEntry1 = BarEntry(One.toFloat(),data.toFloat())
        val barEntry2 = BarEntry(Two.toFloat(),cancel.toFloat())
        val barEntry3 = BarEntry(Three.toFloat(),expired.toFloat())
        val pieEntry1 = PieEntry(data.toFloat(),"完成",data.toFloat())
        val pieEntry2 = PieEntry(cancel.toFloat(),"取消",cancel.toFloat())
        val pieEntry3 = PieEntry(expired.toFloat(),"延期",expired.toFloat())
        if (data != 0){
            barEntries.add(barEntry1)
            pieEntries.add(pieEntry1)
        }
        if (cancel != 0){
            barEntries.add(barEntry2)
            pieEntries.add(pieEntry2)
        }
        if (expired != 0){
            barEntries.add(barEntry3)
            pieEntries.add(pieEntry3)
        }

        val barDataSet = BarDataSet(barEntries, "完成/取消/延期")
        barDataSet.colors = mutableListOf(
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0),
            Color.rgb(106, 150, 31), Color.rgb(179, 100, 53)
        )
        barDataSet.setDrawValues(false)
        barChart.data= BarData(barDataSet)
//        数据出现时间，括号里的值越大越慢
//        barChart.animateY(5000)
        barChart.description.text="柱状图"
        barChart.setNoDataText("无数据")
        barChart.description.textColor= Color.BLUE
        barChart.invalidate()

        val pieDataSet = PieDataSet(pieEntries, "饼图")
        pieDataSet.colors = mutableListOf(
            Color.rgb(193, 37, 82), Color.rgb(255, 102, 0), Color.rgb(245, 199, 0)
        )
        pieChart.data= PieData(pieDataSet)
//        pieChart.animateXY(100,100)
        pieChart.centerText = "统计数据"
        pieChart.setNoDataText("无数据")
        pieChart.setEntryLabelColor(Color.rgb(0,0,0))
        pieChart.description.isEnabled=false
        pieChart.invalidate()
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
                    if (tv == tvdateshow){
                        if (tvtimeshow.text.toString() != ""){

                            var current_date = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ""
                            var current_time = tvtimeshow.text
                            if (current_date.length==9){
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
                            if(current_time.toString().replace("-","").toInt() < current_date.replace("-","").toInt()){
                                Toast.makeText(view?.context,"初始时间不可晚于结束时间,请重新选择时间", Toast.LENGTH_SHORT).show()
                                return
                            }
                            else{
                                tv.text = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ""
                                chart()
                            }

                            chart()
                        }
                        else
                            tv.text = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ""

                    }

                    else if (tvdateshow.text.toString()!= ""){
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
                            chart()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statistic, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.switch_item2->{
                val intent = Intent(this, DateList::class.java)
                startActivity(intent)
            }
            R.id.main_item2->{
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        val date = Date(System.currentTimeMillis())
        val dateFormat1: DateFormat = SimpleDateFormat("yyyyMMdd")
        var dateEnd: String = dateFormat1.format(date)
        var dateStart1 = dateEnd.toInt()-100
        if (dateStart1.toString()[5]=='0')
            dateStart1 -= 8800
//        Log.d("date23",dateStart1.toString() +"+"+dateEnd)
        var dateStart = dateStart1.toString()
        dateStart = dateStart.substring(0,4)+"-"+dateStart.substring(4,6)+"-"+dateStart.substring(6,8)

        dateEnd = dateEnd.substring(0,4)+"-"+dateEnd.substring(4,6)+"-"+dateEnd.substring(6,8)

        Log.d("date23",dateStart+"+"+dateEnd)
        chartInit(dateStart,dateEnd)
    }
}