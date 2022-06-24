package com.example.plants_pro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button btnLogin = (Button) findViewById(R.id.btnLogin);
//        Button btnRegister = (Button) findViewById(R.id.btnRegister);
          Button btnMain = (Button) findViewById(R.id.btnMain);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PlantActivity.class);
                startActivity(intent);
            }
        });
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }


}