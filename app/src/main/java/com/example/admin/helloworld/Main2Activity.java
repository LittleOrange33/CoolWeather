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
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {
    private String[] data = {
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
            "","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",
    };
    private int[] ids = {
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            0,0,0,0,0,0,0,
    };

    private List<String> data2 = new ArrayList<String>();
    private ListView listView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        this.textView = (TextView) findViewById(R.id.getmessage);
        this.listView = (ListView) findViewById(R.id.listview);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("点击",position + " : " + Main2Activity.this.ids[position] + Main2Activity.this.data[position]);
                Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                intent.putExtra("id",Main2Activity.this.ids[position]);
                startActivity(intent);
            }
        });
// String weatherId = "CN101020200";
//        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=e3f1bbd1c57e4560a8b22478df974f25";
          String CityUrl =  "http://guolin.tech/api/china";
          HttpUtil.sendOkHttpRequest(CityUrl, new Callback() {
            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {

                final String responseText = response.body().string();
               // String[] data= ToJson(responseText);
               // Main2Activity.this.data = result;
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
                          Main2Activity.this.data[i] = jsonObject.getString("name");
                          Main2Activity.this.ids[i] = jsonObject.getInt("id");
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
