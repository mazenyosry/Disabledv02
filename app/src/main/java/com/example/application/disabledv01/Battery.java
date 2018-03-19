package com.example.application.disabledv01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Battery extends AppCompatActivity {

    BatteryProgressView progress;
    EditText valueE;
    Button valuebt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        progress=  findViewById(R.id.progress);
        valueE = findViewById(R.id.valueET);
        valuebt = findViewById(R.id.valuebtn);
    }

    public void setvaluee(View v){
        int val;
        val = Integer.parseInt(String.valueOf(valueE.getText()));
        progress.setProgress(val);
    }
}