package com.example.lijing.coolwwath;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lijing.coolwwath.Mode.City;
import com.example.lijing.coolwwath.Mode.County;
import com.example.lijing.coolwwath.Mode.Province;
import com.example.lijing.coolwwath.db.CoolWeathDB;
import com.example.lijing.coolwwath.util.HttpCallbackListener;
import com.example.lijing.coolwwath.util.HttpUtil;
import com.example.lijing.coolwwath.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijing on 15/6/9.
 */
public class ChooseActivity extends Activity {
    public static final int LEVER_PROVINCE = 0;
    public static final int LEVER_CITY = 1;
    public static final int LEVER_COUNTY = 2;

    private ProgressDialog mProgressDialog ;
    private TextView mTextView;
    private ListView mListView;
    private ArrayAdapter<String> mAdapter;
    private CoolWeathDB coolWeathDB;
    private List<String> dataList = new ArrayList<String>();

    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selecteProvince;
    private City selecteCity;
    private int currentLevel;
    private boolean isFromWeatherActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_arer);
        isFromWeatherActivity = getIntent().getBooleanExtra("isFronWeatherAcyivity",false);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getBoolean("city_selected",false)&&!isFromWeatherActivity){
            Intent intent = new Intent(ChooseActivity.this,Weather.class);
            startActivity(intent);
            finish();
            return;
        }
        mListView = (ListView) findViewById(R.id.list_view);
        mTextView = (TextView) findViewById(R.id.title_text);
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,dataList);
        mListView.setAdapter(mAdapter);
        coolWeathDB = CoolWeathDB.getInstance(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVER_PROVINCE){
                    selecteProvince = provinceList.get(position);
                    queryCity();
                }else if(currentLevel==LEVER_CITY){
                    selecteCity = cityList.get(position);
                    queryCounty();
                }else if(currentLevel==LEVER_COUNTY){
                    String countyCode = countyList.get(position).getCounty_code();
                    Intent intent = new Intent(ChooseActivity.this,Weather.class);
                    intent.putExtra("county_code",countyCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
//        加载身份数据
        queryProvince();
    }

    private void queryCounty() {
        countyList = coolWeathDB.loadCounty(selecteCity.getCity_id());
        if(countyList.size()>0){
            dataList.clear();
            for(County county:countyList){
                dataList.add(county.getCounty_name());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mTextView.setText(selecteCity.getCity_name());
            currentLevel = LEVER_COUNTY;
        }else{
            queryFromServer(selecteCity.getCity_code(),"county");
        }
    }

    private void queryProvince() {
        provinceList = coolWeathDB.loadProvicne();
        if(provinceList.size()>0){
            dataList.clear();
            for(Province province:provinceList){
                dataList.add(province.getProvince_name());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mTextView.setText("中国");
            currentLevel = LEVER_PROVINCE;
        }else{
            queryFromServer(null,"provicne");
        }
    }

    private void queryCity(){
        cityList = coolWeathDB.loadCity(selecteProvince.getProvince_id());
        if(cityList.size()>0){
            dataList.clear();
            for(City city:cityList){
                dataList.add(city.getCity_name());
            }
            mAdapter.notifyDataSetChanged();
            mListView.setSelection(0);
            mTextView.setText(selecteProvince.getProvince_name());
            currentLevel = LEVER_CITY;
        }else {
            queryFromServer(selecteProvince.getProvince_code(),"city");
        }
    }

    private void queryFromServer(final String code,final String type) {
        String address;
        if(!TextUtils.isEmpty(code)){
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        }else{
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showprogressDialog();
        HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if("provicne".equals(type)){
                    result = Utility.handleProvinceResponse(coolWeathDB,response);
                }else if("city".equals(type)){
                    result = Utility.handleCityResponse(coolWeathDB,response,selecteProvince.getProvince_id());
                }else if("county".equals(type)){
                    result = Utility.handleCountyResponse(coolWeathDB,response,selecteCity.getCity_id());
                }
               if(result){
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           closeProgressDialog();
                           if("provicne".equals(type)){
                               queryProvince();
                           }else if("city".equals(type)){
                               queryCity();
                           }else if("county".equals(type)){
                               queryCounty();
                           }
                       }
                   });
               }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseActivity.this,"加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void closeProgressDialog() {
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }

    private void showprogressDialog() {
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("正在加载...");
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        mProgressDialog.show();
    }

    @Override
    public void onBackPressed() {
        if(currentLevel == LEVER_COUNTY){
            queryCity();
        }else if(currentLevel == LEVER_CITY){
            queryProvince();
        }else {
            if(isFromWeatherActivity){
                Intent intent = new Intent(ChooseActivity.this,Weather.class);
                startActivity(intent);
            }
            finish();
        }

    }
}
