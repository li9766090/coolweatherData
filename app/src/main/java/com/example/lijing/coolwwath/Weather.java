package com.example.lijing.coolwwath;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lijing.coolwwath.service.AutoUpdateService;
import com.example.lijing.coolwwath.util.HttpCallbackListener;
import com.example.lijing.coolwwath.util.HttpUtil;
import com.example.lijing.coolwwath.util.Utility;

/**
 * Created by lijing on 15/6/9.
 */
public class Weather extends Activity implements View.OnClickListener {
    private LinearLayout weatherLinearLayout;
    private TextView cityName,publishTime,currentData,weatherInfoText,temp1Text,temp2Text;
    private Button home,refreshInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_laypupt);
//        初始化控件
        intiView();
        home.setOnClickListener(this);
        refreshInfo.setOnClickListener(this);
        String countyCode = getIntent().getStringExtra("county_code");
//        Log.e("countyCode",countyCode+"");
        if(!TextUtils.isEmpty(countyCode)){
            publishTime.setText("同步中...");
            cityName.setVisibility(View.INVISIBLE);
            weatherLinearLayout.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else{
            showWeather();
        }

    }

    private void queryWeatherCode(String countyCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
        queryFromServer(address,"countyCode");
    }

    private void queryFromServer(final String address, final String type) {
        HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if("countyCode".equals(type)){
                    if(!TextUtils.isEmpty(response)){
                        String[] array = response.split("\\|");
                        if(array!=null&&array.length==2){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                    }
                }else if("weatherCode".equals(type)){
                    Log.e("response", response + "");

                    Utility.handleWeatherResponse(Weather.this,response);
                    runOnUiThread(
                            new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        publishTime.setText("同步失败...");
                    }
                });
            }
        });
    }

    private void showWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        cityName.setText(prefs.getString("city_name",""));
        publishTime.setText("今天" + prefs.getString("publish_time","") + "发布");
        currentData.setText(prefs.getString("current_data",""));
        weatherInfoText.setText(prefs.getString("weather_desp",""));
        temp1Text.setText(prefs.getString("temp1",""));
        temp2Text.setText(prefs.getString("temp2",""));
        weatherLinearLayout.setVisibility(View.VISIBLE);
        cityName.setVisibility(View.VISIBLE);
        Intent intent = new Intent(Weather.this, AutoUpdateService.class);
        startService(intent);
    }

    private void queryWeatherInfo(String weatherCode) {
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        queryFromServer(address,"weatherCode");
    }

    private void intiView() {
        weatherLinearLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
        cityName = (TextView) findViewById(R.id.city_name);
        publishTime = (TextView) findViewById(R.id.release_time);
        currentData = (TextView) findViewById(R.id.current_date);
        weatherInfoText = (TextView) findViewById(R.id.current_weather);
        temp1Text = (TextView) findViewById(R.id.temp1);
        temp2Text = (TextView) findViewById(R.id.temp2);
        home = (Button) findViewById(R.id.home);
        refreshInfo = (Button) findViewById(R.id.refresh_weather);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home:
                Intent intent = new Intent(Weather.this,ChooseActivity.class);
                intent.putExtra("isFronWeatherAcyivity",true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                publishTime.setText("同步中");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                String weatherCode = preferences.getString("weather_code","");
                if(weatherCode!=null){
                    queryWeatherInfo(weatherCode);
                }
                break;
            default:
                break;
        }

    }

}
