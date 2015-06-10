package com.example.lijing.coolwwath.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lijing on 15/6/8.
 */
//用来装载天气信息的数据库
public class DBHelper extends SQLiteOpenHelper {
//    //    数据库表名
//    private static final String DB_NAME = "coolWeath.db";
//    //    数据版本
//    private static final int VERSION = 1;
//    //    创建省份数据库
    private static final String CREATE_PROVINCE = "create table Province(_id integer primary key autoincrement," +
            "province_id integer,province_name text,province_code text)";
    //    市
    private static final String CREATE_CITY = "create table City(_id integer primary key autoincrement," +
            "city_id integer,city_name text,city_code text,province_id integer)";
    //    县
    private static final String CREATE_COUNTY = "create table County(_id integer primary key autoincrement," +
            "county_id integer,county_name text,county_code text,city_id integer)";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //    //  静态对象的引用
//    private static DBHelper dbHelper = null;
//
//    //    获得类的唯一对象
//    public static DBHelper getInstance(Context context) {
//        if (dbHelper == null) {
//            dbHelper = new DBHelper(context);
//        }
//        return dbHelper;
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_PROVINCE);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
