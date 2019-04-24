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

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Callback;
import okhttp3.Response;

public class ProvinceActivity extends AppCompatActivity {
    private String[] ProvinceData = {
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };

    private int[] ProvinceIds ={
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
        int id = intent.getIntExtra("id",0);
        Log.i("接受id ","" + id);
        this.textView = (TextView) findViewById(R.id.getweather);
        this.button = (Button) findViewById(R.id.button);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProvinceActivity.this,CityActivity.class));
            }
        });

        this.listView = findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,ProvinceData);
        listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击",position + " : " + ProvinceActivity.this.ProvinceIds[position] + ProvinceActivity.this.ProvinceData[position]);
                Intent intent = new Intent(ProvinceActivity.this,ProvinceActivity.class);
                intent.putExtra("id",ProvinceIds[position]);
                startActivity(intent);
            }
        });


        String weatherId = "CN101020200";
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=e3f1bbd1c57e4560a8b22478df974f25";

        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(responseText);
                }
                });
//                textView.setText(responseText);
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                   e.printStackTrace();
            }
        });
    }
}
