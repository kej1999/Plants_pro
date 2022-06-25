package com.example.plants_pro;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class PresActivity extends Activity {

    String myJSON;
    JSONArray jsonArray = null;
    String timeStr;
    String[] diseaseName = {"Early_blight","Septoria_leaf","Bacterial_spot","Late_blight","Mosaic_virus","Yellow_virus",
            "Tomato_mold_leaf","spider_mites_leaf"};
    int[] diseaseOccured = {0,0,0,0,0,0,0,0};




    TextView resultText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);

        resultText = findViewById(R.id.resultText);


        Button btnReturn3 = (Button) findViewById(R.id.btnReturn3);
        btnReturn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getData("http://192.168.0.3/PHP_disease.php");
        Integer diseaseAmount = 0;
        for (int i = 0; i < diseaseOccured.length; i++) {
            diseaseAmount += diseaseOccured[i];
        }
        resultText.setText(resultText.getText()+diseaseAmount.toString());
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

            timeStr = row.getString("dtime");
            for (int i = 0; i < diseaseName.length; i++) {
                diseaseOccured[i] = Integer.parseInt(row.getString(diseaseName[i]));
            }

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }
}
