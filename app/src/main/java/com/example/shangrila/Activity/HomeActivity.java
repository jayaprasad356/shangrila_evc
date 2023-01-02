package com.example.shangrila.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shangrila.R;
import com.example.shangrila.helper.ApiConfig;
import com.example.shangrila.helper.Constant;
import com.example.shangrila.helper.Session;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    Button btnCalculate;
    TextView tvBalance,tvSelectDate;
    Session session;
    Activity activity;
    Button recharge;
    EditText edEVCcode;
    Dialog dialog = null;
    EditText Electricityday,Electricitynight,Gas;
    ImageView imgLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        activity = HomeActivity.this;
        tvSelectDate =findViewById(R.id.tvSelectDate);
        tvBalance =findViewById(R.id.tvBalance);
        btnCalculate =findViewById(R.id.btnCalculate);
        imgLogout =findViewById(R.id.imgLogout);
        session = new Session(activity);
        tvBalance.setText("Wallet Balance = "+session.getData(Constant.WALLET));
        final Calendar myCalendar = Calendar.getInstance();
        Button rechargeWallet = findViewById(R.id.rechargewallet);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(tvSelectDate,myCalendar);
        };

        tvSelectDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(HomeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        dialog = new Dialog(this);
        dialog.setContentView(R.layout.evc_code_layout);

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        recharge = dialog.findViewById(R.id.recharge);
        edEVCcode = dialog.findViewById(R.id.edEVCcode);
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rechargeApi();
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser(activity);
            }
        });

        rechargeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                Electricityday=findViewById(R.id.electricity);
                Electricitynight=findViewById(R.id.electricitynight);
                Gas=findViewById(R.id.gas);
                if (tvSelectDate.getText().toString().trim().equals("SELECT DATE")){
                    Toast.makeText(HomeActivity.this, "Enter Submission Date", Toast.LENGTH_SHORT).show();
                }else {
                    calculateApi();

                }
            }
        });

    }

    private void calculateApi()
    {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.EMR_DAY,Electricityday.getText().toString().trim());
        params.put(Constant.EMR_NIGHT,Electricitynight.getText().toString().trim());
        params.put(Constant.GMR,Gas.getText().toString().trim());
        params.put(Constant.DATE,tvSelectDate.getText().toString().trim());
        ApiConfig.RequestToVolley((result,response) -> {
            Log.d("CALCULATE_RESPONSE",response);
            if(result){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        showBottomSheetDialog(jsonObject.getString(Constant.TOTAL_AMOUNT));
                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(activity, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();
            }
        },activity, Constant.CALCULATE_BILL_URL, params,true);


    }

    private void rechargeApi()
    {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.EVC_CODE,edEVCcode.getText().toString().trim());
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if(result){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        dialog.dismiss();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.ID,jsonArray.getJSONObject(0).getString(Constant.ID));
                        session.setData(Constant.WALLET,jsonArray.getJSONObject(0).getString(Constant.WALLET));
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(activity, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();
            }
        },activity, Constant.ADD_RECHARGE_URL, params,true);

    }

    private void showBottomSheetDialog(String amount) {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);


        Button btnPay = bottomSheetDialog.findViewById(R.id.btnPay);
        TextView tvAmount = bottomSheetDialog.findViewById(R.id.tvAmount);
        tvAmount.setText("Total Amount = ₹"+amount);



        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payApi(amount);
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();
    }

    private void payApi(String amount)
    {
        Map<String,String> params = new HashMap<>();
        params.put(Constant.USER_ID,session.getData(Constant.ID));
        params.put(Constant.EMR_DAY,Electricityday.getText().toString().trim());
        params.put(Constant.EMR_NIGHT,Electricitynight.getText().toString().trim());
        params.put(Constant.GMR,Gas.getText().toString().trim());
        params.put(Constant.DATE,tvSelectDate.getText().toString().trim());
        params.put(Constant.TOTAL,amount);
        ApiConfig.RequestToVolley((result, response) -> {
            if(result){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean(Constant.SUCCESS)) {
                        dialog.dismiss();
                        JSONArray jsonArray = jsonObject.getJSONArray(Constant.DATA);
                        session.setData(Constant.ID,jsonArray.getJSONObject(0).getString(Constant.ID));
                        session.setData(Constant.WALLET,jsonArray.getJSONObject(0).getString(Constant.WALLET));
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(activity, ""+String.valueOf(jsonObject.getString(Constant.MESSAGE)), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(activity, String.valueOf(response) +String.valueOf(result), Toast.LENGTH_SHORT).show();
            }
        },activity, Constant.PAYBILL_URL, params,true);

    }

    private void updateLabel(TextView edittext,Calendar myCalendar) {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}