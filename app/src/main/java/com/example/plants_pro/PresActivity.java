package com.example.plants_pro;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

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

public class PresActivity extends AppCompatActivity {
    ViewPager pager;

    String []test = new String[8];
    String myJSON;
    JSONArray jsonArray = null;
    //데이터베이스에서 받아온 시간
    String timeStr;
    String[] diseaseName = {"Early_blight","Septoria_leaf","Bacterial_spot","Late_blight","Mosaic_virus","Yellow_virus",
            "Tomato_mold_leaf","spider_mites_leaf"};
    int[] diseaseOccurred = {0,0,0,0,0,0,0,0};
    int diseaseNum = 0;

    TextView resultText;
    TextView[] diseaseText = new TextView[8];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription);


        getData("http://192.168.0.3/PHP_disease.php");

        resultText = findViewById(R.id.resultText);
        for (int i = 0; i < diseaseText.length; i++) {
            Integer integer = i;
            diseaseText[i] = findViewById(getResources().getIdentifier("txtDisease" + i, "id", this.getPackageName()));
        }

        Button btnReturn3 = (Button) findViewById(R.id.btnReturn3);
        Button btnDis = (Button) findViewById(R.id.btnDis);

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
            }
        });

        btnReturn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        //////페이저 생성 , 페이지 추가
        pager = findViewById(R.id.pager);
        //페이지 갯수
        pager.setOffscreenPageLimit(diseaseNum);

        //페이저 어댑터 클래스
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        EarlyBlight fragment1 = new EarlyBlight();
        SeptoriaLeaf fragment2 = new SeptoriaLeaf();
        BacterialSpot fragment3 = new BacterialSpot();
        LateBlight fragment4 = new LateBlight();
        MosaicVirus fragment5 = new MosaicVirus();
        YellowLeaf fragment6 = new YellowLeaf();
        LeafMold fragment7 = new LeafMold();
        TwoSpotted fragment8 = new TwoSpotted();



        if(diseaseOccurred[0] == 1) {
            adapter.addItem(fragment1);
        }
        if(diseaseOccurred[1] == 1){
            adapter.addItem(fragment2);
        }
        if(diseaseOccurred[2] == 1){
            adapter.addItem(fragment3);
        }
        if(diseaseOccurred[3] == 1){
            adapter.addItem(fragment4);
        }
        if(diseaseOccurred[4] == 1){
            adapter.addItem(fragment5);
        }
        if(diseaseOccurred[5] == 1){
            adapter.addItem(fragment6);
        }
        if(diseaseOccurred[6] == 1){
            adapter.addItem(fragment7);
        }
        if(diseaseOccurred[7] == 1){
            adapter.addItem(fragment8);
        }

        pager.setAdapter(adapter);
        //////////////////////////////////
        super.onStart();
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
                this.diseaseOccurred[i] = Integer.parseInt(row.getString(diseaseName[i]));
                this.diseaseNum += this.diseaseOccurred[i];
            }
            resultText.setText(resultText.getText()+new Integer(this.diseaseNum).toString());

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }
}
