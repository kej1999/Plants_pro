package com.example.plants_pro;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends Activity {
    private FirebaseAuth mAuth;
    private EditText edtID,edtPassword,edtChkPW;
    private Button btnNew;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();

        Button btnReturn2 = (Button) findViewById(R.id.btnReturn2);
        btnNew = (Button) findViewById(R.id.btnNew);
        edtID = findViewById(R.id.edtID);
        edtPassword = findViewById(R.id.edtPassword);
        edtChkPW = findViewById(R.id.edtChkPW);

        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnNew:
                        signUp();
                        break;
                }
            }
        });

        btnReturn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void signUp(){
        String id=((EditText)findViewById(R.id.edtID)).getText().toString();
        String password=((EditText)findViewById(R.id.edtPassword)).getText().toString();
        String passwordCheck=((EditText)findViewById(R.id.edtChkPW)).getText().toString();

        if(id.length()>0 && password.length()>0 && passwordCheck.length()>0){
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(id, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다." ,Toast.LENGTH_SHORT).show();
                                } else {
                                    if(task.getException().toString() !=null){
                                        Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다." ,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
            else{
                Toast.makeText(RegisterActivity.this, "비밀번호가 일치하지 않습니다." ,Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(RegisterActivity.this, "아아디와 비밀번호를 확인해주세요." ,Toast.LENGTH_SHORT).show();
        }
    }
}
