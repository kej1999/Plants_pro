package com.example.plants_pro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class PlantActivity extends Activity {

    final String[] words = new String[] {"토마토", "파프리카", "감자", "고추"};
    Button btnPlus;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant);


        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnPlus = (Button) findViewById(R.id.btnPlus);
        de.hdodenhof.circleimageview.CircleImageView pltPic = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.pltPic);


        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListClick(view);
            }
        });

        pltPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ContentActivity.class);
                startActivity(intent);
            }
        });


    }

    public void ListClick(View view) {
        new AlertDialog.Builder(this).setTitle("선택").setItems(words, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(PlantActivity.this, "words : " + words[which], Toast.LENGTH_LONG).show();
            }
        }).setNeutralButton("닫기", null).setPositiveButton("확인", null).show();
    }

}
