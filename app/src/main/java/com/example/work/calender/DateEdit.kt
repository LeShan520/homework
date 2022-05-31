package com.example.work.calender

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.work.R
import com.example.work.sql.SpHelper
import java.io.File
import java.io.IOException
import java.util.*

var imageUriedit: Uri? = null
var E_CAMERA_CODE = 0


@SuppressLint("StaticFieldLeak")
lateinit var edbtpicture: ImageButton
lateinit var edpictureshow: ImageView

class DateEdit : AppCompatActivity() {

    companion object {//控制两种打开方式
    val TAKE_PHOTO = 1

        val CHOOSE_PHOTO = 2

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_edit)

        val tvdateshow : TextView = findViewById(R.id.tv_edit_dateshow)
        val tvtimeshow : TextView = findViewById(R.id.tv_edit_timeshow)
        edbtpicture = findViewById(R.id.bt_edit_picture)
        edpictureshow = findViewById(R.id.iv_edit_etimage)
        val update : Button = findViewById(R.id.bt_edit_edupdate)
        val cancel : Button = findViewById(R.id.bt_edit_edcancel)
        val edtitle : EditText = findViewById(R.id.ed_edit_title)
        val edcontent : EditText = findViewById(R.id.ed_edit_content)

        val a = intent.getIntExtra("data",1)
        Log.d("heredata",a.toString())
        val sql  = SpHelper(this)
        val data = sql.query1(a).split(",")

        tvdateshow.text = data[1]
        tvtimeshow.text = data[2]
        edtitle.setText(data[3])
        edpictureshow.setImageURI(Uri.parse(data[4]))
        edcontent.setText(data[5])

        tvdateshow.setOnClickListener{
            showDatePickerDialog(this,2,tvdateshow, Calendar.getInstance())
        }

        tvtimeshow.setOnClickListener{
            showTimePickerDialog(this,2,tvtimeshow, Calendar.getInstance())
        }

        edbtpicture.setOnClickListener {
            takePhoto()
        }

        update.setOnClickListener {
            val sql = SpHelper(this)

            when {
                edtitle.text.toString()=="" -> {
                    Toast.makeText(this,"修改失败,请输入标题",Toast.LENGTH_LONG).show()
                }
                edcontent.text.toString()=="" -> {
                    Toast.makeText(this,"修改失败,请输入内容",Toast.LENGTH_LONG).show()
                }
                else -> {
                    sql.update(a,tvdateshow,tvtimeshow,edtitle,edcontent,imageUri.toString())
                    Handler().postDelayed({
                        Toast.makeText(this,"修改成功",Toast.LENGTH_LONG).show()
                        finish()
//                        val intent = Intent(this,DateList::class.java)
//                        startActivity(intent)
                    },1000)
                }
            }
        }

        cancel.setOnClickListener {
            finish()
        }

    }

    private fun takePhoto() {

        // 1. 创建File对象，用于存储拍照后的图片
        val outputImage = File(externalCacheDir, "output_image.jpg")
        try {
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }

//2.
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage)
        } else {
            imageUri = FileProvider.getUriForFile(
                this@DateEdit,
                "com.ywjh.cameraalbumtest.fileprovider",//定义唯一标识，关联后面的内容提供器
                outputImage
            )
        }
        // 3. 启动相机程序
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, TAKE_PHOTO)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                try {
                    // 将拍摄的照片显示出来
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!))
                    edpictureshow.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    println("异常："+e)
                }
            }

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
            object : OnDateSetListener {

//                val  builder: AlertDialog.Builder = AlertDialog.Builder(context)

                // 绑定监听器(How the parent is notified that the date is set.)
                override fun onDateSet(
                    view: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int,
                ) {
                    // 此处得到选择的时间，可以进行你想要的操作
                    tv.text = "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + ""
                }

            } // 设置初始日期
            ,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    fun showTimePickerDialog(
        activity: Activity?,
        themeResId: Int,
        tv: TextView,
        calendar: Calendar,
    ) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        TimePickerDialog(activity!!,
            themeResId,
            { _, hourOfDay, minute ->
                // 绑定监听器(How the parent is notified that the date is set.)
                //                override fun onTimeSet(
                //                    view: DatePicker?,
                //                    hour: Int,
                //                    minute: Int,
                //                ) {
                //                    // 此处得到选择的时间，可以进行你想要的操作
                //                    tv.text = "" + hour + "-" + minute
                //                }
                tv.text= "" + hourOfDay + ":" + minute+""
            } // 设置初始日期
            ,
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),true).show()
    }

}