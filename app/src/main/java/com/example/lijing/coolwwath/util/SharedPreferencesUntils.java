package com.example.lijing.coolwwath.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lijing on 15/6/6.
 */
//数据存储的一个工具类
public class SharedPreferencesUntils {
//    保存在手机里的数据文件名
    private static final String FILE_NAME="share_data";
//    根据不同的数据类型存储数据
    public static void setParam(Context context,String key,Object object){
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if("String".equals(type)){
            editor.putString(key, (String) object);
        }
        if("Integer".equals(type)){
            editor.putInt(key, (Integer) object);
        }
        if("Boolean".equals(type)){
            editor.putBoolean(key, (Boolean) object);
        }
        if("Float".equals(type)){
            editor.putFloat(key, (Float) object);
        }
        if("Long".equals(type)){
            editor.putLong(key, (Long) object);
        }
        editor.commit();
    }
    public static Object getParam(Context context , String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }

}
