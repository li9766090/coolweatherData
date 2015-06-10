package com.example.lijing.coolwwath.util;

/**
 * Created by lijing on 15/6/9.
 */
// 用来回调服务器返回的数据结果
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
