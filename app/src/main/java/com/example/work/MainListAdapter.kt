package com.example.work

import android.annotation.SuppressLint
import android.app.Activity
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.work.sql.Calender
import com.example.work.sql.SpHelper

class MainListAdapter (
    activity: Activity,
    resourceId: Int,
    val datalist: java.util.ArrayList<Calender>
): ArrayAdapter<Calender>(activity,resourceId,datalist){

    var data1: java.util.ArrayList<Calender> = ArrayList()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        data1=datalist
        val view = LayoutInflater.from(context).inflate(R.layout.activity_sql_item,parent,false)
        val tv1 : TextView = view.findViewById(R.id.tv_item_title)
        val tv2 : TextView = view.findViewById(R.id.tv_time)
        val tv3 : TextView = view.findViewById(R.id.tv_date)

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
                }
                .setPositiveButton("确定"){ dialog, which ->
                    val id = getItem(position)?.id
                    val sphelper = SpHelper(context)
                    sphelper.updateState(id)
                    data1.removeAt(position)
                    notifyDataSetChanged()
                }
                .create()
            alertDialog.show()

        }


        //导出数据
        val calender = data1.get(position)
        tv1.text = calender.title
        tv2.text = calender.date
        tv3.text = calender.time
        return view
    }


    class Item {

        var checked: Boolean? = null
    }

    override fun notifyDataSetChanged() {
        super.notifyDataSetChanged()
    }
}