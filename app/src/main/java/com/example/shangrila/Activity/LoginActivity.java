package com.example.shangrila.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shangrila.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvSignup;
    EditText edEmail,edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edEmail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else if (edPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(intent);
                }


            }
        });
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}