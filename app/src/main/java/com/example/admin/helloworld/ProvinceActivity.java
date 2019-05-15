package com.example.admin.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;

public class ProvinceActivity extends AppCompatActivity {
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String COUNTY = "county";

    private String currentlevel = PROVINCE;
    private int AreaIdList = 0;
    private int ProvinceId = 0;
    private int CityId = 0;

    private List<Integer> Ids = new ArrayList<Integer>();
    private List<String> WeatherIdList = new ArrayList<String>();
    private List<String> AreaNameList = new ArrayList<>();

    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.listview = (ListView) findViewById(R.id.listview);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AreaNameList);
        listview.setAdapter(adapter);
        this.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击", position + " : " + ProvinceActivity.this.Ids.get(position) + ":" + ProvinceActivity.this.AreaNameList.get(position));

                if(currentlevel == PROVINCE) {
                    currentlevel = CITY;
                    AreaIdList = ProvinceActivity.this.Ids.get(position);
                    ProvinceId = AreaIdList;
                }

                else if(currentlevel == CITY) {
                    currentlevel = COUNTY;
                    AreaIdList = ProvinceActivity.this.Ids.get(position);
                    CityId = AreaIdList;
                }

                else if(currentlevel == COUNTY) {
                    Intent intent = new Intent(ProvinceActivity.this,WeatherActivity.class);
                    intent.putExtra("WeatherIdList", WeatherIdList.get(position));
                    startActivity(intent);
                }
                getData(adapter);
            }
        });
        getData(adapter);
    }

    private void getData(final ArrayAdapter<String> adapter) {
        String WeatherURL = currentlevel == PROVINCE ?"http://guolin.tech/api/china/": (currentlevel == CITY ?"http://guolin.tech/api/china/" + ProvinceId:"http://guolin.tech/api/china/" + ProvinceId + "/" + CityId);
        HttpUtil.sendOkHttpRequest(WeatherURL, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String responseText = response.body().string();

                ToJson(responseText);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void ToJson(String responseText) {
        JSONArray jsonArray = null;
        this.AreaNameList.clear();
        this.Ids.clear();
        this.WeatherIdList.clear();
        try {
            jsonArray = new JSONArray(responseText);
            for (int i = 0; jsonArray.length() > i; i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.AreaNameList.add(jsonObject.getString("name"));
                this.Ids.add(jsonObject.getInt("id"));
                if(jsonObject.has("weather_id"))
                    this.WeatherIdList.add(jsonObject.getString("weather_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
