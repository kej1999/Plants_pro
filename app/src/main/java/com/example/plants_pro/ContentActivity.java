package com.example.plants_pro;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


public class ContentActivity extends Activity {
    TextView txtView;
    DatePickerDialog dialog;
    TextView txtTime;
    TextView txtTemp;
    TextView txtHumi;
    TextView txtSoil;
    TextView disease;

    //DB연동
    String myJSON;
    String date;

    private static final String TAG_RESULTS = "result";
    private static String TAG_TIME = "time";
    private static final String TAG_TEMP = "temp";
    private static final String TAG_HUMI = "humi";
    private static final String TAG_SOIL = "soil_humi";

    JSONArray peoples = null;
    JSONArray MaxPeople = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    int cMonth;
    int cDay;

    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_info);

        Button btnDetail = (Button)findViewById(R.id.btnDetail);
        Button btnRe = (Button) findViewById(R.id.btnReturn2);
        Button btnCamera = (Button) findViewById(R.id.btnCamera);
        txtTime = (TextView) findViewById(R.id.time);
        txtTemp = (TextView) findViewById(R.id.temp);
        txtHumi = (TextView) findViewById(R.id.humi);
        txtSoil = (TextView) findViewById(R.id.soil);
        Button btnRefresh = (Button) findViewById(R.id.btnRefresh);
        Button btnGuide = (Button) findViewById(R.id.btnGuide);
        disease = (TextView) findViewById(R.id.disease);


//        Calendar cal = Calendar.getInstance();
//
//        txtView.setText(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE));
        //DB연동
        //data = (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
        getData("http://192.168.0.3/PHP_connection.php");


        btnRe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        txtView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pYear = cal.get(Calendar.YEAR);
//                int pMonth = cal.get(Calendar.MONTH);
//                int pDay = cal.get(Calendar.DAY_OF_MONTH);
//
//                dialog = new DatePickerDialog(ContentActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                        month = month+1;
//                        cMonth = month;
//                        cDay = day;
//                        txtView.setText(year + "-" + month + "-" + day);
//                    }
//                },pYear,pMonth,pDay);
//
//                dialog.show();
//            }
//
//        });

//        btnDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                data = cMonth + "-" + cDay;
//                getData("http://192.168.0.3/PHP_connection.php");
//            }
//        });

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = getIntent();
                    finish();
                    overridePendingTransition(0,0);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
//        btnCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivity(intent);
//            }
//        });

        btnGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PresActivity.class);
                startActivity(intent);
            }
        });


        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DatalistActivity.class);
                startActivity(intent);
            }
        });


    }

    protected void showList() {
        try {
            //personList = new ArrayList<HashMap<String, String>>();
            JSONObject jsonObj = new JSONObject(myJSON);
            //peoples = jsonObj.getJSONArray(TAG_RESULTS);
            MaxPeople = jsonObj.getJSONArray(TAG_RESULTS);

            JSONObject a = MaxPeople.getJSONObject(0);
            String ti = a.getString(TAG_TIME);
            String t = a.getString(TAG_TEMP);
            String h = a.getString(TAG_HUMI);
            String s = a.getString(TAG_SOIL);
            txtTime.setText(ti);
            txtTemp.setText(t);
            txtHumi.setText(h);
            txtSoil.setText(s);



//            for (int i = 0; i < peoples.length(); i++) {
//                JSONObject c = peoples.getJSONObject(i);
//                String id = c.getString(TAG_TIME);
//                String name = c.getString(TAG_TEMP);
//                String address = c.getString(TAG_HUMI);
//                String soil = c.getString(TAG_SOIL);
//
//                HashMap<String, String> persons = new HashMap<String, String>();
//                if(id.contains(data)){
//                    persons.put(TAG_TIME, id);
//                    persons.put(TAG_TEMP, name);
//                    persons.put(TAG_HUMI, address);
//                    persons.put(TAG_SOIL,soil);
//                    personList.add(persons);
//                }
//
//            }
//
//            ListAdapter adapter = new SimpleAdapter(
//                    ContentActivity.this, personList, R.layout.list_item,
//                    new String[]{TAG_TIME, TAG_TEMP, TAG_HUMI,TAG_SOIL},
//                    new int[]{R.id.Time, R.id.Temp, R.id.Humi,R.id.Soil}
//            );
//            list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

}
