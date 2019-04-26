package com.example.admin.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class CountyActivity extends AppCompatActivity {

    private String[] CountyDatas = {
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };
    private int[] CountyIds = {
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
    };
    private String[] WeatherIds = {
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };

    private TextView textView;
    private ListView listview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        this.textView = (TextView) findViewById(R.id.getcity);
        this.listview = (ListView) findViewById(R.id.listview);
        final int ProvinceIds = getIntent().getIntExtra("ProvinceIds",0) ;
        final int CityIds = getIntent().getIntExtra("CityIds",0) ;



        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,CountyDatas);
        listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击",position + " : " + CountyActivity.this.CountyIds[position] + CountyActivity.this.CountyDatas[position]);
                Intent intent = new Intent(CountyActivity.this,WeatherActivity.class);
                intent.putExtra("WeatherIds",WeatherIds[position]);
                startActivity(intent);
                //startActivity(new Intent(ProvinceActivity.this,CountyActivity.class));
            }
        });
        String CountyUrl =  "http://guolin.tech/api/china/" + ProvinceIds + "/" + CityIds;

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
                        adapter.notifyDataSetChanged();
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
                        CountyActivity.this.CountyDatas[i] = jsonObject.getString("name");
                        CountyActivity.this.CountyIds[i] = jsonObject.getInt("id");
                        CountyActivity.this.WeatherIds[i]= jsonObject.getString("weather_id");
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

