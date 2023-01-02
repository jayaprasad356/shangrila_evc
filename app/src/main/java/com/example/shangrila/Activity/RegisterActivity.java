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

    Button btnSignup;
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
        btnSignup = findViewById(R.id.btnSignup);
        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
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
        Map<String,String> params = new HashMap<>();
        params.put(Constant.EMAIL,edEmail.getText().toString().trim());
        params.put(Constant.PASSWORD,edPassword.getText().toString().trim());
        params.put(Constant.PROPERTY_TYPE,spinProperty.getSelectedItem().toString().trim());
        params.put(Constant.BEDROOMS_COUNT, edBedrooms.getText().toString().trim());
        params.put(Constant.EVC_CODE, edEVCcode.getText().toString().trim());
        ApiConfig.RequestToVolley((result,response) -> {
            Log.d("SIGN_UP_RES",response);
            if(result){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        Intent intent = new Intent(activity,HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        },activity, Constant.SIGNUP_URL, params,true);


    }
}