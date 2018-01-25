package com.cardslider.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


/**
 * Android Splash页秒开方案
 * CSDN 博客==》http://blog.csdn.net/yanzhenjie1003/article/details/52201896
 *
 * @author Kevin  2017/12/28 10:56
 */
public class StartSplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, SplashActivity.class));
        finish();
    }
}
