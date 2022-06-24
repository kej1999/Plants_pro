package com.example.plants_pro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PresActivity extends Activity {
    TextView testText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);

        testText = findViewById(R.id.txtTest);
        Button btnReturn3 = (Button) findViewById(R.id.btnReturn3);
        //getData("http://192.168.0.7/PHP_disease.php");
        btnReturn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

//    protected void showList() {
//        try {
//            //personList = new ArrayList<HashMap<String, String>>();
////            JSONObject jsonObj = new JSONObject(myJSON);
////            //peoples = jsonObj.getJSONArray(TAG_RESULTS);
////            MaxPeople = jsonObj.getJSONArray(TAG_RESULTS);
////
////            JSONObject a = MaxPeople.getJSONObject(0);
////            String ti = a.getString(TAG_TIME);
////            String t = a.getString(TAG_TEMP);
////            String h = a.getString(TAG_HUMI);
////            String s = a.getString(TAG_SOIL);
////            txtTime.setText(ti);
////            txtTemp.setText(t);
////            txtHumi.setText(h);
////            txtSoil.setText(s);
//
//
////            for (int i = 0; i < peoples.length(); i++) {
////                JSONObject c = peoples.getJSONObject(i);
////                String id = c.getString(TAG_TIME);
////                String name = c.getString(TAG_TEMP);
////                String address = c.getString(TAG_HUMI);
////                String soil = c.getString(TAG_SOIL);
////
////                HashMap<String, String> persons = new HashMap<String, String>();
////                if(id.contains(data)){
////                    persons.put(TAG_TIME, id);
////                    persons.put(TAG_TEMP, name);
////                    persons.put(TAG_HUMI, address);
////                    persons.put(TAG_SOIL,soil);
////                    personList.add(persons);
////                }
////
////            }
////
////            ListAdapter adapter = new SimpleAdapter(
////                    ContentActivity.this, personList, R.layout.list_item,
////                    new String[]{TAG_TIME, TAG_TEMP, TAG_HUMI,TAG_SOIL},
////                    new int[]{R.id.Time, R.id.Temp, R.id.Humi,R.id.Soil}
////            );
////            list.setAdapter(adapter);
////
////        } catch (JSONException e) {
////            e.printStackTrace();
////        }
////    }
//
////    public void getData(String url) {
////        class GetDataJSON extends AsyncTask<String, Void, String> {
////
////            @Override
////            protected String doInBackground(String... params) {
////
////                String uri = params[0];
////
////                BufferedReader bufferedReader = null;
////                try {
////                    URL url = new URL(uri);
////                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
////                    StringBuilder sb = new StringBuilder();
////
////                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
////
////                    String json;
////                    while ((json = bufferedReader.readLine()) != null) {
////                        sb.append(json + "\n");
////                    }
////
////                    return sb.toString().trim();
////
////                } catch (Exception e) {
////                    return null;
////                }
////
////
////            }
////
////            @Override
////            protected void onPostExecute(String result) {
////                myJSON = result;
////                showList();
////            }
////        }
////        GetDataJSON g = new GetDataJSON();
////        g.execute(url);
////    }
//        }

}
