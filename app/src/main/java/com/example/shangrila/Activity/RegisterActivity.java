package com.example.shangrila.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shangrila.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Email=findViewById(R.id.email);
                EditText Password=findViewById(R.id.password);
                EditText Properutype=findViewById(R.id.propertutype);
                EditText Numberof=findViewById(R.id.numberof);
                EditText Evc=findViewById(R.id.evc);
                if (Email.getText().toString().trim().isEmpty()) {

                    Toast.makeText(RegisterActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else       if (Password.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }else       if (Properutype.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Propertu Type", Toast.LENGTH_SHORT).show();
                }else       if (Numberof.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Number of Bedrooms", Toast.LENGTH_SHORT).show();
                }else       if (Evc.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter your EVC", Toast.LENGTH_SHORT).show();
                }else {


                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}