package com.example.shangrila.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shangrila.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class HomeActivity extends AppCompatActivity {
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                EditText Submitdate=findViewById(R.id.submissiondate);
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
}