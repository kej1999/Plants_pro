package com.example.plants_pro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PresActivity extends Activity {

    String myJSON;
    JSONArray jsonArray = null;

    TextView testText;
    TextView textText2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);

        testText = findViewById(R.id.txtTest);
        textText2 = findViewById(R.id.txtTest2);


        Button btnReturn3 = (Button) findViewById(R.id.btnReturn3);
        btnReturn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getData("http://192.168.0.20/PHP_disease.php");

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
                textText2.setText(myJSON);
                getJSON();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    public void getJSON(){

        try{
            JSONObject jsonObject = new JSONObject(myJSON);
            jsonArray = jsonObject.getJSONArray("result");

            JSONObject row = jsonArray.getJSONObject(0);

            testText.setText(row.getString("dtime"));



        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }
}
