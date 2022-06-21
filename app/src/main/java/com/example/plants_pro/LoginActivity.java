package com.example.plants_pro;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {
    private FirebaseAuth mAuth;
    private EditText et_id, et_pass;
    private Button btn_login, btn_return;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        et_id = (EditText)findViewById(R.id.edt_Id);
        et_pass = (EditText)findViewById(R.id.edt_Password);
        btn_login = (Button)findViewById(R.id.btnOkLogin);
        btn_return = (Button)findViewById(R.id.btnReturn1);

        mAuth = FirebaseAuth.getInstance();

        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String email = et_id.getText().toString().trim();
               String pwd = et_pass.getText().toString().trim();
               mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(
                       LoginActivity.this, new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {
                               if(task.isSuccessful()){
                                   Intent intent = new Intent(LoginActivity.this, PlantActivity.class);
                                   startActivity(intent);
                               }else{
                                   Toast.makeText(LoginActivity.this,"이메일과 비밀번호를 다시 확인해주세요",Toast.LENGTH_SHORT).show();
                               }
                           }
                       });

            }
        });


    }
}
