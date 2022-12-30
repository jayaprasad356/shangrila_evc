package com.example.shangrila.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shangrila.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        EditText Submitdate=findViewById(R.id.submissiondate);
        final Calendar myCalendar = Calendar.getInstance();
        Button rechargeWallet = findViewById(R.id.rechargewallet);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(Submitdate,myCalendar);
            }

        };

        Submitdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(HomeActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.evc_code_layout);

        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button recharge = dialog.findViewById(R.id.recharge);
        recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        rechargeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {


                EditText Electricityday=findViewById(R.id.electricity);
                EditText Electricitynight=findViewById(R.id.electricitynight);
                EditText Gas=findViewById(R.id.gas);
                if (Submitdate.getText().toString().trim().isEmpty()){
                    Toast.makeText(HomeActivity.this, "Enter Submission Date", Toast.LENGTH_SHORT).show();
                }else       if (Electricityday.getText().toString().trim().isEmpty()){
                    Toast.makeText(HomeActivity.this, "Enter Electricity Meter Reading - Day", Toast.LENGTH_SHORT).show();
                }else       if (Electricitynight.getText().toString().trim().isEmpty()){
                    Toast.makeText(HomeActivity.this, "Enter Electricity Meter Reading - Night", Toast.LENGTH_SHORT).show();
                }else       if (Gas.getText().toString().trim().isEmpty()){
                    Toast.makeText(HomeActivity.this, "Enter Gas Meter Reading", Toast.LENGTH_SHORT).show();
                }else {
                    showBottomSheetDialog();
                }
            }
        });

    }

    private void showBottomSheetDialog() {


        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog);


        Button btnRecharge = bottomSheetDialog.findViewById(R.id.btnRecharge);

        btnRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });


        bottomSheetDialog.show();
    }

    private void updateLabel(EditText edittext,Calendar myCalendar) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}