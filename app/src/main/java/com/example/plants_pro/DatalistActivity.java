package com.example.plants_pro;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DatalistActivity extends Activity {
    DatePickerDialog dialog;
    TextView txtView;

    int cMonth;
    int cDay;

    String data;

    String myJSON;
    String date;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_TIME = "time";
    private static final String TAG_TEMP = "temp";
    private static final String TAG_HUMI = "humi";
    private static final String TAG_SOIL = "soil_humi";

    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_list);

        txtView = (TextView) findViewById(R.id.txtView);
        Button btnReturn4 = (Button) findViewById(R.id.btnReturn4);
        Button btnDate = (Button) findViewById(R.id.btnDate);

        Button btnList = (Button) findViewById(R.id.btnList);

        Calendar cal = Calendar.getInstance();

        txtView.setText(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE));
        //DB연동
        list = (ListView) findViewById(R.id.date_List);
        data = (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);

        getData("http://192.168.0.3/PHP_connection.php");

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pYear = cal.get(Calendar.YEAR);
                int pMonth = cal.get(Calendar.MONTH);
                int pDay = cal.get(Calendar.DAY_OF_MONTH);

                dialog = new DatePickerDialog(DatalistActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        cMonth = month;
                        cDay = day;
                        txtView.setText(year + "-" + month + "-" + day);
                    }
                }, pYear, pMonth, pDay);

                dialog.show();
            }
        });

        btnReturn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = cMonth + "-" + cDay;
                getData("http://192.168.0.3/PHP_connection.php");
            }
        });

    }

    protected void showList() {
        try {
            personList = new ArrayList<HashMap<String, String>>();
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {

                JSONObject c = peoples.getJSONObject(i);

                String time = c.getString(TAG_TIME);
                String temp = c.getString(TAG_TEMP);
                String humi = c.getString(TAG_HUMI);
                String soil = c.getString(TAG_SOIL);

                HashMap<String, String> persons = new HashMap<String, String>();

                if(time.contains(data)){
                    persons.put(TAG_TIME, time);
                    persons.put(TAG_TEMP, temp);
                    persons.put(TAG_HUMI, humi);
                    persons.put(TAG_SOIL, soil);
                    personList.add(persons);
                }


            }

            ListAdapter adapter = new SimpleAdapter(
                    DatalistActivity.this, personList, R.layout.table,
                    new String[]{TAG_TIME, TAG_TEMP, TAG_HUMI, TAG_SOIL},
                    new int[]{R.id.times, R.id.temps, R.id.humis, R.id.soils}
            );
            list.setAdapter(adapter);

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
