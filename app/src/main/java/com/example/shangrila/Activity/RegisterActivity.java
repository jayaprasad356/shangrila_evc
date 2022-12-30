package com.example.shangrila.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shangrila.R;
import com.example.shangrila.helper.ApiConfig;
import com.example.shangrila.helper.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button btnNext;
    EditText edEmail,edPassword,edBedrooms,edEVCcode;
    Spinner spinProperty;
    ImageView imgBack;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activity = RegisterActivity.this;
        edEmail =findViewById(R.id.edEmail);
        edPassword =findViewById(R.id.edPassword);
        edBedrooms =findViewById(R.id.edBedrooms);
        edEVCcode =findViewById(R.id.edEVCcode);
        spinProperty =findViewById(R.id.spinProperty);
        btnNext = findViewById(R.id.btnNext);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edEmail.getText().toString().trim().isEmpty()) {

                    Toast.makeText(RegisterActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else       if (edPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }else       if (spinProperty.getSelectedItem().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Propertu Type", Toast.LENGTH_SHORT).show();
                }else       if (edBedrooms.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Number of Bedrooms", Toast.LENGTH_SHORT).show();
                }else       if (edEVCcode.getText().toString().trim().isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter your EVC Code", Toast.LENGTH_SHORT).show();
                }else if (edEVCcode.getText().toString().trim().length() != 8){
                    Toast.makeText(RegisterActivity.this, "Enter your eight digit EVC Code", Toast.LENGTH_SHORT).show();
                }else {

                    signUp();



                }
            }
        });
    }

    private void signUp()
    {
        Intent intent = new Intent(activity, HomeActivity.class);
        startActivity(intent);
    }
}