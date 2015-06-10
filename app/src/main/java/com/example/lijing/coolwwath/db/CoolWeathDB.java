package com.example.lijing.coolwwath.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lijing.coolwwath.Mode.City;
import com.example.lijing.coolwwath.Mode.County;
import com.example.lijing.coolwwath.Mode.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijing on 15/6/9.
 */
public class CoolWeathDB {
//    数据库名
    private static final String DB_NAME = "cool_weath";
//    数据库版本
    private static final int VERSION = 1;

    private static CoolWeathDB coolWeathDB;
    private SQLiteDatabase db;
//    将构造方法私有化
    private CoolWeathDB(Context context){
        DBHelper dbHelper = new DBHelper(context,DB_NAME,null,VERSION);
        db = dbHelper.getWritableDatabase();
    }
//    获取实例的方法
    public synchronized  static CoolWeathDB getInstance(Context context){
        if(coolWeathDB == null){
            coolWeathDB = new CoolWeathDB(context);
        }
        return coolWeathDB;
    }
//    将province实例存入到数据库
    public void saveProvince(Province province){
        if(province !=null){
            ContentValues values = new ContentValues();
            values.put("province_name",province.getProvince_name());
            values.put("province_code",province.getProvince_code());
            db.insert("Province",null,values);

        }
    }
//   从数据库读取所欲哦省份的信息
    public List<Province> loadProvicne(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
               Province province = new Province();
                province.setProvince_id(cursor.getInt(cursor.getColumnIndex("_id")));
                province.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvince_code(cursor.getString(cursor.getColumnIndex("province_code")));
                list.add(province);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
//  将City实例存入数据库
    public void saveCity(City city){
        if(city !=null){
            ContentValues values = new ContentValues();
            values.put("city_name",city.getCity_name());
            values.put("city_code",city.getCity_code());
            values.put("province_id",city.getProvince_id());
            db.insert("City",null,values);
        }
    }
//    从数据库读取所有的城市信息
    public List<City> loadCity(int provicne_id){
        List<City> list = new ArrayList<City>();
        Cursor cursor = db.query("City",null,"province_id = ?",
                new String[]{String.valueOf(provicne_id)},null,null,null);
        if(cursor.moveToFirst()){
            do {
                City city = new City();
                city.setCity_id(cursor.getInt(cursor.getColumnIndex("_id")));
                city.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvince_id(provicne_id);
                list.add(city);
            }while (cursor.moveToNext());
        }
        return list;
    }
//    将county实例存入数据库
    public void saveCounty(County county){
        if(county!=null){
            ContentValues values = new ContentValues();
            values.put("county_name",county.getCounty_name());
            values.put("county_code",county.getCounty_code());
            values.put("city_id",county.getCityu_id());
            db.insert("County",null,values);
        }
    }
//    从数据库读取县城的信息
    public List<County> loadCounty(int cityId){
        List<County> list = new ArrayList<County>();
        Cursor cursor = db.query("County",null,"city_id = ?",
                new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.moveToFirst()){
            do {
                County county = new County();
                county.setCounty_name(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCounty_code(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCityu_id(cityId);
                list.add(county);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
