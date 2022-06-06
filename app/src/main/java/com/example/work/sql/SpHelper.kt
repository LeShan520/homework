package com.example.work.sql

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.example.work.sql.Calender

class SpHelper (context: Context){
    val mHelper : DataBaseHelper = DataBaseHelper(context)
    val mWritableDatabase = mHelper.writableDatabase

    fun SpHelper(context:Context){
        val readeableDatabase = mHelper.readableDatabase

    }

    fun insertData(a1: TextView,a2: TextView, b: EditText, c: EditText,d:String?){

        var string11 = a1.text.toString()
        val string12 = a2.text.toString()
        val string2 = b.text.toString()
        val string3 = c.text.toString()
        var string4 = d
        if (string11.length==9){
            if (string11[6]=='-'){
                string11=string11.substring(0,5)+"0"+string11.substring(5,9)
            }
            else
                string11=string11.substring(0,8)+"0"+string11.substring(8,9)
        }
        else if(string11.length==8)
            string11 = string11.substring(0,5)+"0"+string11.substring(5,7)+"0"+string11.substring(7,8)
        else
            string11 = string11
        Log.d("string11",string11)
        val contentValues = ContentValues()
        contentValues.put("date", string11)
        contentValues.put("time", string12)
        contentValues.put("title", string2)
        contentValues.put("content",string3)
        contentValues.put("uri",string4)
        contentValues.put("state",0)
        contentValues.put("expired",0)
        contentValues.put("cancel",0)
        mWritableDatabase.insert("calender",null,contentValues)
    }

    fun update(a:Int, b:TextView, c:TextView, d: TextView, e:TextView, f:String){
        val contentValues = ContentValues()
        val string1 = b.text.toString()
        val string2 = c.text.toString()
        val string3 = d.text.toString()
        val string4 = d.text.toString()
        contentValues.put("date", string1)
        contentValues.put("time", string2)
        contentValues.put("title", string3)
        contentValues.put("content",string4)
        contentValues.put("uri",f)
        mWritableDatabase.update("calender",contentValues,"id=?", arrayOf("$a"))
    }

    fun updateState(a: Int?){
        val contentValues = ContentValues()
        contentValues.put("state",1)
        mWritableDatabase.update("calender",contentValues,"id=?", arrayOf("$a"))
    }

    fun updateCancel(a: Int?){
        val contentValues = ContentValues()
        contentValues.put("cancel",1)
        mWritableDatabase.update("calender",contentValues,"id=?", arrayOf("$a"))
    }

    fun updateExpired(a: Int?){
        val contentValues = ContentValues()
        contentValues.put("expired",1)
        mWritableDatabase.update("calender",contentValues,"id=?", arrayOf("$a"))
    }

    fun delete(a: Int?){
        mWritableDatabase.delete("calender","id=?", arrayOf("$a"))
    }
    fun omit(a:String){
        mWritableDatabase.delete("calender","user=?", arrayOf(a))
    }

    fun query(): ArrayList<Calender> {
        val cursor =mWritableDatabase.query("calender", null, "state=? and cancel=?" , arrayOf("0","0"), null, null, null)
        val sb  = ArrayList<Calender>()

        if (cursor.moveToFirst()){
            do {

                val idIndex = cursor.getColumnIndex("id")
                val id = cursor.getInt(idIndex)

                val dateIndex = cursor.getColumnIndex("date")
                val date = cursor.getString(dateIndex)

                val timeIndex = cursor.getColumnIndex("time")
                val time = cursor.getString(timeIndex)

                val contentIndex = cursor.getColumnIndex("content")
                val content = cursor.getString(contentIndex)

                val uriIndex = cursor.getColumnIndex("uri")
                val uri = cursor.getString(uriIndex)

                val titleIndex = cursor.getColumnIndex("title")
                val title = cursor.getString(titleIndex)
                sb.add(Calender(id, date,time,title,uri,content))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return sb
    }
    fun querytodo(): ArrayList<String> {
        val cursor =mWritableDatabase.query("calender", null , null , null, null, null, null)
        val sb  = ArrayList<String>()

        if (cursor.moveToFirst()){
            do {

                val dateIndex = cursor.getColumnIndex("date")
                val date = cursor.getString(dateIndex)

                val stateIndex = cursor.getColumnIndex("state")
                val state = cursor.getString(stateIndex)

                val cancelIndex = cursor.getColumnIndex("cancel")
                val cancel = cursor.getString(cancelIndex)

                if (state.toInt()==0 && cancel.toInt()==0)
                    sb.add(date)
            }while (cursor.moveToNext())
        }
        cursor.close()
        Log.d("sbb",sb.toString())
        return sb
    }

    fun query1(a:Int?): String {
        val cursor =mWritableDatabase.query("calender", null, "id=?" , arrayOf("$a"), null, null, null)
        val sb  = StringBuilder()
//        sb.add(Calender("id","用户"))
//        Log.d("d",cursor.moveToFirst().toString())
        if (cursor.moveToFirst()){
            do {

                val idIndex = cursor.getColumnIndex("id")
                val id = cursor.getInt(idIndex)

                val dateIndex = cursor.getColumnIndex("date")
                val date = cursor.getString(dateIndex)

                val timeIndex = cursor.getColumnIndex("time")
                val time = cursor.getString(timeIndex)

                val contentIndex = cursor.getColumnIndex("content")
                val content = cursor.getString(contentIndex)

                val uriIndex = cursor.getColumnIndex("uri")
                val uri = cursor.getString(uriIndex)

                val titleIndex = cursor.getColumnIndex("title")
                val title = cursor.getString(titleIndex)
                sb.append("$id,$date,$time,$title,$uri,$content")
            }while (cursor.moveToNext())
        }
        cursor.close()
        Log.d("asbsbsb",sb.toString())
        return sb.toString()
    }

    internal class new {
        var id = 0
        var title: String? = null
        var time: String? = null
        var content: String? = null
        var date:String? = null
    }

    fun query2(a:String): ArrayList<Calender> {
        val cursor =mWritableDatabase.query("calender", null, "date=? and state=?" , arrayOf("$a","0"), null, null, null)
        val sb  = ArrayList<Calender>()

        if (cursor.moveToFirst()){
            do {

                val idIndex = cursor.getColumnIndex("id")
                val id = cursor.getInt(idIndex)

                val dateIndex = cursor.getColumnIndex("date")
                val date = cursor.getString(dateIndex)

                val timeIndex = cursor.getColumnIndex("time")
                val time = cursor.getString(timeIndex)

                val contentIndex = cursor.getColumnIndex("content")
                val content = cursor.getString(contentIndex)

                val uriIndex = cursor.getColumnIndex("uri")
                val uri = cursor.getString(uriIndex)

                val titleIndex = cursor.getColumnIndex("title")
                val title = cursor.getString(titleIndex)

                val cancelIndex = cursor.getColumnIndex("cancel")
                val cancel = cursor.getString(cancelIndex)

                if(cancel.toInt() == 0)
                    sb.add(Calender(id, date,time,title,uri,content))

            }while (cursor.moveToNext())
        }
        cursor.close()
        return sb
    }
    fun query3(id:Int): ArrayList<Calender> {
        val cursor =mWritableDatabase.query("calender", null, "id=? and expired=?" , arrayOf("$id","0"), null, null, null)
        val sb  = ArrayList<Calender>()

        if (cursor.moveToFirst()){
            do {

                val idIndex = cursor.getColumnIndex("id")
                val id = cursor.getInt(idIndex)

                val dateIndex = cursor.getColumnIndex("date")
                val date = cursor.getString(dateIndex)

                val timeIndex = cursor.getColumnIndex("time")
                val time = cursor.getString(timeIndex)

                val contentIndex = cursor.getColumnIndex("content")
                val content = cursor.getString(contentIndex)

                val uriIndex = cursor.getColumnIndex("uri")
                val uri = cursor.getString(uriIndex)

                val titleIndex = cursor.getColumnIndex("title")
                val title = cursor.getString(titleIndex)
                sb.add(Calender(id, date,time,title,uri,content))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return sb
    }

    fun queryDate(a:String, b:String, c:String,): Int {
        val cursor =mWritableDatabase.query("calender", null, "$a=?" , arrayOf("1"), null, null, null)
        val date1 = mutableListOf<String>()

        var current_date_prior = b
        if (current_date_prior.length==9){
            if (current_date_prior[6]=='-'){
                current_date_prior=current_date_prior.substring(0,5)+"0"+current_date_prior.substring(5,9)
            }
            else
                current_date_prior=current_date_prior.substring(0,8)+"0"+current_date_prior.substring(8,9)
        }
        else if(current_date_prior.length==8)
            current_date_prior = current_date_prior.substring(0,5)+"0"+current_date_prior.substring(5,7)+"0"+current_date_prior.substring(7,8)
        else
            current_date_prior = current_date_prior
        var current_date_behind = c
        if (current_date_behind.length==9){
            if (current_date_behind[6]=='-'){
                current_date_behind=current_date_behind.substring(0,5)+"0"+current_date_behind.substring(5,9)
            }
            else
                current_date_behind=current_date_behind.substring(0,8)+"0"+current_date_behind.substring(8,9)
        }
        else if(current_date_behind.length==8)
            current_date_behind = current_date_behind.substring(0,5)+"0"+current_date_behind.substring(5,7)+"0"+current_date_behind.substring(7,8)
        else
            current_date_behind = current_date_behind

        if (cursor.moveToFirst()){
            do {
                val dateIndex = cursor.getColumnIndex("date")
                val date = cursor.getString(dateIndex)
                if (date != ""){
                    if (current_date_prior.replace("-","").toInt() <= date.replace("-","").toInt()&&
                        date.replace("-","").toInt() <= current_date_behind.replace("-","").toInt()){
                        date1.add(date)
//                        Log.d("date221",date1.toString())
                    }

                }


            }while (cursor.moveToNext())
        }
        val count = date1.size
//        Log.d("date222",date1.toString())
//        Log.d("date223",count.toString())
        cursor.close()
        return count
    }
}