package com.example.lijing.coolwwath.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lijing on 15/6/8.
 */
// 开始从网络获取相关数据
public class HttpUtil {
        public static void sendHttpRequest(final String uri, final HttpCallbackListener listener){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection conn = null;
                    InputStream is = null;
                    try {
                        URL url = new URL(uri);
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        conn.setReadTimeout(5000);
                        is = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        StringBuilder builder = new StringBuilder();
                        String line = null;
                        while((line = br.readLine()) !=null){
                            builder.append(line);
                        }
//                        Log.e("builder",builder+"");
                        if(listener!=null){
                            listener.onFinish(builder.toString());
                        }
                    } catch (Exception e) {
                        if(listener!=null){
                            listener.onError(e);
                        }
                    }finally {
                        try {
                            is.close();
                            conn.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

}
