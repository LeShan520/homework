package com.example.work.sql

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import com.example.work.R
import com.example.work.calender.DateEdit
import com.example.work.calender.DateList
import com.example.work.calender.imageUri
import com.example.work.sql.Calender
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CalenderAdapt(
    activity: Activity,
     resourceId: Int,
    val datalist: java.util.ArrayList<Calender>
):ArrayAdapter<Calender>(activity,resourceId,datalist) {
    var data1: java.util.ArrayList<Calender> = ArrayList()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        data1=datalist
        val view = LayoutInflater.from(context).inflate(R.layout.activity_sql_item,parent,false)
        val tv1 : TextView = view.findViewById(R.id.tv_item_title)
        val tv2 : TextView = view.findViewById(R.id.tv_time)
        val tv3 : TextView = view.findViewById(R.id.tv_date)
        val image : ImageView = view.findViewById(R.id.image_item)

        //删除事件
        val btdelete : ImageButton = view.findViewById(R.id.ib_delete)
        btdelete.setOnClickListener{
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("确认")
                .setMessage("是否删除？")
                .setNegativeButton("取消") { dialog, which ->
//                    Toast.makeText(context, "你取消了", Toast.LENGTH_SHORT).show()
                }
                .setPositiveButton("确定"){ dialog, which ->
                    val id = getItem(position)?.id
                    val sphelper = SpHelper(context)
                    sphelper.delete(id)
                    data1.removeAt(position)
                    notifyDataSetChanged()
                }
                .create()
            alertDialog.show()

        }

        //完成事件
        val cbfinish : CheckBox = view.findViewById(R.id.cb_finish)
        cbfinish.setOnCheckedChangeListener { buttonView, isChecked ->
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("确认")
                .setMessage("是否完成了任务？")
                .setNegativeButton("取消") { dialog, which ->
//                    Toast.makeText(context, "你取消了", Toast.LENGTH_SHORT).show()
                    notifyDataSetChanged()
                }
                .setPositiveButton("确定"){ dialog, which ->
                    val id = getItem(position)?.id
                    val sphelper = SpHelper(context)
                    sphelper.updatestate(id)
                    data1.removeAt(position)
                    notifyDataSetChanged()
//                    Toast.makeText(context,"你确认了", Toast.LENGTH_SHORT).show()
                }
                .create()
            alertDialog.show()
//            alertDialog.setOnDismissListener {
//            }
        }

        tv1.setOnClickListener {
            if (image.visibility == View.GONE){
                image.visibility = View.VISIBLE
            }
        }
        tv1.setOnLongClickListener{
            val a=getItem(position)?.id
            Log.d("putextradata",a.toString())
            val intent = Intent(context, DateEdit::class.java)
            intent.putExtra("data",a)
            parent.context.startActivity(intent)
            notifyDataSetChanged()
            return@setOnLongClickListener true
        }


        //导出数据

        val calender = data1.get(position)
            tv1.text = calender.title
            tv2.text = calender.date
            tv3.text = calender.time
        val uri = Uri.parse(calender.uri)
            image.setImageURI(uri)
        var current_date = calender.date
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
        val date = Date(System.currentTimeMillis())
        val dateFormat1: DateFormat = SimpleDateFormat("yyyyMMdd")
        val format1: String = dateFormat1.format(date)
        if(format1.toInt() > current_date.toString().replace("-","").toInt()){
            val linear : LinearLayout = view.findViewById(R.id.linear_item)
            linear.setBackgroundColor(Color.parseColor("#FF6A6A"))
        }



        return view
    }


    class Item {

        var checked: Boolean? = null
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }



}