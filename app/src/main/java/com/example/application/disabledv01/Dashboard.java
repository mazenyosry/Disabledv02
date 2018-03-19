package com.example.application.disabledv01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void battery(View v){

        Intent ba = new Intent(this,Battery.class);
        startActivity(ba);

    }

    public void callus ( View v){

        Intent ca = new Intent(this,Callus.class);
        startActivity(ca);

    }
}
