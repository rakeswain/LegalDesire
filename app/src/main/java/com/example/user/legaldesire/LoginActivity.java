package com.example.user.legaldesire;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.user.legaldesire.Fragments.Dialog;

public class LoginActivity extends AppCompatActivity {

    private Button usrBtn,lawyerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usrBtn = findViewById(R.id.usrBtn);
        lawyerBtn = findViewById(R.id.lawyerBtn);
        usrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAlreadyLoggedin("user");
               // Toast.makeText(getApplicationContext(),"User Authentication Pending",Toast.LENGTH_SHORT).show();
               // Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
               // intent.putExtra("user_type","user");
               // startActivity(intent);
            }
        });
        lawyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAlreadyLoggedin("lawyer");
              //  Intent intent = new Intent(LoginActivity.this,RegistrationActivity.class);
              //  intent.putExtra("user_type","lawyer");
              //  startActivity(intent);
            }
        });
    }

    private void checkAlreadyLoggedin(String type) {
        Dialog dialog=new Dialog();
        dialog.type = type;
        dialog.show(getSupportFragmentManager(),"login dialog");
    }
}
