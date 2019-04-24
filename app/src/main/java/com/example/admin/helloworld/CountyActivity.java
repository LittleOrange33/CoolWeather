package com.example.admin.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class CountyActivity extends AppCompatActivity {

    private String[] CountryData = {
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };
    private int[] CountryIds = {
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
    };
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        this.textView = (TextView) findViewById(R.id.getcity);

        int ProvinceId = getIntent().getIntExtra("ProvinceId",0) ;
        int CityId = getIntent().getIntExtra("CityId",0) ;

        String CountyUrl =  "http://guolin.tech/api/china" + ProvinceId + "/" + CityId;

        HttpUtil.sendOkHttpRequest(CountyUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String responseText = response.body().string();

                ToJson(responseText);
                //System.out.print(data);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                    }
                });
//                textView.setText(responseText);
            }

            private void ToJson(String responseText) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(responseText);
                    //         String[] result = new String[jsonArray.length()];
                    for (int i = 0; jsonArray.length() > i; i++) {
                        JSONObject jsonObject = null;
                        jsonObject = jsonArray.getJSONObject(i);
                        CountyActivity.this.CountryData[i] = jsonObject.getString("name");
                        CountyActivity.this.CountryIds[i] = jsonObject.getInt("id");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}

