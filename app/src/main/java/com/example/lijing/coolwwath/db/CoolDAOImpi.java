package com.example.lijing.coolwwath.db;

/**
 * Created by lijing on 15/6/8.
 */
//public class CoolDAOImpi implements CoolDAO{
//    private DBHelper dbHelper = null;
//    public CoolDAOImpi(Context context) {
//        dbHelper = DBHelper.getInstance(context);
//    }
//
//    @Override
//    public void insertProvince(Province province) {
//        SQLiteDatabase  db = dbHelper.getWritableDatabase();
//        db.execSQL("insert into Province(province_id,province_name,province_code) values(?,?,?)",
//                new Object[]{province.getProvince_id(),province.getProvince_name(),province.getProvince_code()});
//                db.close();
//    }
//
//    @Override
//    public List<Province> getProvinceInfo(int id) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        List<Province> list = new ArrayList<Province>();
//        Cursor cursor = db.rawQuery("select * from Province where id = ?",new String[]{String.valueOf(id)});
//        while(cursor.moveToNext()){
//            Province province = new Province();
//            province.setProvince_id(cursor.getInt(cursor.getColumnIndex("province_id")));
//            province.setProvince_name(cursor.getString(cursor.getColumnIndex("province_name")));
//            province.setProvince_code(cursor.getString(cursor.getColumnIndex("province_code")));
//            list.add(province);
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
//
//    @Override
//    public void insertCity(City city) {
//    SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("insert into City(city_id,city_name,city_code,province_id) values(?,?,?,?)",
//                new Object[]{city.getCity_id(),city.getCity_name(),city.getCity_code(),city.getProvince_id()});
//        db.close();
//    }
//
//    @Override
//    public List<City> getCityInfo(int id) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        List<City> list = new ArrayList<City>();
//        Cursor cursor = db.rawQuery("select * from City where id = ?",new String[]{String.valueOf(id)});
//        while (cursor.moveToNext()){
//            City city = new City();
//            city.setCity_id(cursor.getInt(cursor.getColumnIndex("city_id")));
//            city.setCity_name(cursor.getString(cursor.getColumnIndex("city_name")));
//            city.setCity_code(cursor.getString(cursor.getColumnIndex("city_code")));
//            city.setProvince_id(cursor.getInt(cursor.getColumnIndex("province_id")));
//            list.add(city);
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
//
//    @Override
//    public void insertCounty(County county) {
//    SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.execSQL("insert into County(county_id,county_name,county_code,city_id) values(?,?,?,?)",
//                new Object[]{county.getConty_id(),county.getCounty_name(),county.getCounty_code(),county.getCityu_id()});
//        db.close();
//    }
//
//    @Override
//    public List<County> getCountyInfo(int id) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        List<County> list = new ArrayList<County>();
//        Cursor cursor = db.rawQuery("select * from County where id = ?",new String[]{String.valueOf(id)});
//        while (cursor.moveToNext()){
//            County county = new County();
//            county.setConty_id(cursor.getInt(cursor.getColumnIndex("county_id")));
//            county.setCounty_name(cursor.getString(cursor.getColumnIndex("county_name")));
//            county.setCounty_code(cursor.getString(cursor.getColumnIndex("county_code")));
//            county.setCityu_id(cursor.getInt(cursor.getColumnIndex("city_id")));
//            list.add(county);
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }
//}
