package com.example.lijing.coolwwath.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.lijing.coolwwath.Mode.City;
import com.example.lijing.coolwwath.Mode.County;
import com.example.lijing.coolwwath.Mode.Province;
import com.example.lijing.coolwwath.db.CoolWeathDB;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by lijing on 15/6/9.
 */
//  用来解析服务器返回的数据
public class Utility {

    public synchronized static boolean handleProvinceResponse(CoolWeathDB coolWeathDB, String response) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvince_code(array[0]);
                    province.setProvince_name(array[1]);
                    coolWeathDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCityResponse(CoolWeathDB coolWeathDB, String response, int province_id) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCitys = response.split(",");
            if (allCitys != null && allCitys.length > 0) {
                for (String c : allCitys) {
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCity_code(array[0]);
                    city.setCity_name(array[1]);
                    city.setProvince_id(province_id);
                    coolWeathDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }


    public synchronized static boolean handleCountyResponse(CoolWeathDB coolWeathDB, String reponse, int city_id) {
        if (!TextUtils.isEmpty(reponse)) {
            String[] allCounty = reponse.split(",");
            if (allCounty != null && allCounty.length > 0) {
                for (String c : allCounty) {
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCounty_code(array[0]);
                    county.setCounty_name(array[1]);
                    county.setCityu_id(city_id);
                    coolWeathDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherinfo = jsonObject.getJSONObject("weatherinfo");
            Log.e("weatherinfo",weatherinfo+"");
            String city = weatherinfo.getString("city");
            String temp1 = weatherinfo.getString("temp1");
            String temp2 = weatherinfo.getString("temp2");
            String weather = weatherinfo.getString("weather");
            String ptime = weatherinfo.getString("ptime");
            String weatherCode = weatherinfo.getString("cityid");
            saveWeatherInfo(context, city, temp1, temp2, weather, ptime,weatherCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveWeatherInfo(Context context, String city, String temp1, String temp2, String weather, String ptime,String weatherCode) {
//        SharedPreferencesUntils.setParam(context, "String", city);
//        SharedPreferencesUntils.setParam(context, "String", temp1);
//        SharedPreferencesUntils.setParam(context, "String", temp2);
//        SharedPreferencesUntils.setParam(context, "String", weather);
//        SharedPreferencesUntils.setParam(context, "String", ptime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日",Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString("city_name",city);
        editor.putBoolean("city_selected",true);
        editor.putString("weather_code",weatherCode);
        editor.putString("publish_time",ptime);
        editor.putString("current_data",sdf.format(new Date()));
        editor.putString("weather_desp",weather);
        editor.putString("temp1",temp1);
        editor.putString("temp2",temp2);
        editor.commit();

    }


}
