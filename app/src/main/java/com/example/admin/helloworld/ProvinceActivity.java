package com.example.admin.helloworld;

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
    private List<Integer> Ids = new ArrayList<Integer>();
    private String currentlevel = "province";
    private int Id = 0;
    private List<String> CountyData = new ArrayList<String>();
    private ListView listView;
    private List<String> ProvinceData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.listView = (ListView) findViewById(R.id.listview);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ProvinceData);
        listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击", position + " : " + ProvinceActivity.this.Ids.get(position) + ":" + ProvinceActivity.this.ProvinceData.get(position));
                Id = ProvinceActivity.this.Ids.get(position);
                currentlevel = "city";
                getData(adapter);
            }
        });
        getData(adapter);
    }

    private void getData(final ArrayAdapter<String> adapter) {
        String WeatherURL = currentlevel == "city"?"http://guolin.tech/api/china/" + Id : "http://guolin.tech/api/china";
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
        this.ProvinceData.clear();
        this.Ids.clear();
        try {
            jsonArray = new JSONArray(responseText);
            for (int i = 0; jsonArray.length() > i; i++) {
                JSONObject jsonObject = null;
                jsonObject = jsonArray.getJSONObject(i);
                this.ProvinceData.add(jsonObject.getString("name"));
                this.Ids.add(jsonObject.getInt("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
