package com.example.admin.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Response;

public class CityActivity extends AppCompatActivity {
    private String[] CityDatas = {
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };

    private int[] CityIds ={
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
    };

    private TextView textView;
    private Button button;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        final int ProvinceIds = getIntent().getIntExtra("ProvinceIds",0);
        //int id = intent.getIntExtra("ProvinceIds",0);
        Log.i("接受id ","" + ProvinceIds);
        this.textView = (TextView) findViewById(R.id.getweather);
        this.button = (Button) findViewById(R.id.button);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CityActivity.this,ProvinceActivity.class));
            }
        });

        this.listView = findViewById(R.id.listview);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,CityDatas);
        listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击",position + " : " + CityActivity.this.CityIds[position] + CityActivity.this.CityDatas[position]);
                Intent intent = new Intent(CityActivity.this,CountyActivity.class);
                intent.putExtra("CityIds",CityIds[position]);
                intent.putExtra("ProvinceIds",ProvinceIds);
                startActivity(intent);
            }
        });


        //String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=e3f1bbd1c57e4560a8b22478df974f25";
        String CityUrl = "http://guolin.tech/api/china/"+ProvinceIds;

        HttpUtil.sendOkHttpRequest(CityUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                ToJson(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                        adapter.notifyDataSetChanged();

                    }
                });
            }


            private void ToJson(String responseText) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(responseText);
                    //         String[] result = new String[jsonArray.length()];
                    for (int i = 0; jsonArray.length() > i; i++) {
                        JSONObject jsonObject = null;
                        jsonObject = jsonArray.getJSONObject(i);
                        CityActivity.this.CityDatas[i] = jsonObject.getString("name");
                        CityActivity.this.CityIds[i] = jsonObject.getInt("id");
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
