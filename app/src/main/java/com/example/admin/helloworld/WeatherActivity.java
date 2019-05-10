package com.example.admin.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    private TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        final String weatherIds = getIntent().getStringExtra("WeatherIds");
        this.textview = (TextView) findViewById(R.id.getweather);
        String weatherUrl = "http://guolin.tech/api/weather?cityid="+weatherIds + "&key=e3f1bbd1c57e4560a8b22478df974f25";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textview.setText(responseText);
                    }
                });
//
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
