package com.example.lijing.coolwwath.db;

import com.example.lijing.coolwwath.Mode.City;
import com.example.lijing.coolwwath.Mode.County;
import com.example.lijing.coolwwath.Mode.Province;

import java.util.List;

/**
 * Created by lijing on 15/6/8.
 */
//数据库的访问接口
public interface CoolDAO {
//    省份数据的插入与读取
    public void insertProvince(Province province);
    public List<Province> getProvinceInfo(int id);
//    城市数据的插入与读取
    public void insertCity(City city);
    public List<City> getCityInfo(int id);
//    县城数据的插入与读取
    public void insertCounty(County county);
    public List<County> getCountyInfo(int id);


}
