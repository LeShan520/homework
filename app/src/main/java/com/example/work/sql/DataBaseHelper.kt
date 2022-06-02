package com.example.work.sql

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(
    context: Context?

) : SQLiteOpenHelper(context, "calender.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
//        val sq = "drop table calendar"
        val sq = "create table calendar (id integer primary key Autoincrement , date varchar(64), time varchar(64), title varchar(64), content varchar(64), uri varchar(64), state integer(2), expired integer(2), cancel integer(2))"
        db?.execSQL(sq)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}