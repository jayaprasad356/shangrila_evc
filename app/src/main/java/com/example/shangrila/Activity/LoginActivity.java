package com.example.shangrila.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shangrila.R;
import com.example.shangrila.helper.ApiConfig;
import com.example.shangrila.helper.Constant;
import com.example.shangrila.helper.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView tvSignup;
    EditText edEmail,edPassword;
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(LoginActivity.this);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        //debug button will be removed soon


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edEmail.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter your Email", Toast.LENGTH_SHORT).show();
                }else if (edPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }else{
                    Map<String,String> params = new HashMap<>();
                    params.put(Constant.EMAIL,edEmail.getText().toString().trim());
                    params.put(Constant.PASSWORD,edPassword.getText().toString().trim());
                    ApiConfig.RequestToVolley((result,response) -> {
                        if(result){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getBoolean(Constant.SUCCESS)) {
                                    JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                                    session.setBoolean("is_logged_in", true);
                                    session.setData(Constant.ID,jsonArray.getJSONObject(0).getString(Constant.ID));
                                    session.setData(Constant.WALLET,jsonArray.getJSONObject(0).getString(Constant.WALLET));
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    Toast.makeText(LoginActivity.this, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();

                                }
                        } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();
                        }
//                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
//                    startActivity(intent);
                },LoginActivity.this, Constant.LOGIN_URL, params,true);


            }
        }});
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}