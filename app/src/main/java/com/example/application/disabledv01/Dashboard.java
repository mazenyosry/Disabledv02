package com.example.application.disabledv01;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.CALL_PHONE},1);


        ActivityCompat.requestPermissions(Dashboard.this, new String[]{Manifest.permission.INTERNET},1);
    }

    public void battery(View v){

        Intent ba = new Intent(this,Battery.class);
        startActivity(ba);

    }

    public void callus ( View v){

        Intent ca = new Intent(this,Callus.class);
        startActivity(ca);

    }

    public void move (View v){
        Intent mo= new Intent(this,Move.class);
        startActivity(mo);
    }

    public void signout (View v){
        SharedPreferences sharedPreferences=getSharedPreferences("acs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("a","1");
        editor.commit();
        Intent LogOut = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(LogOut);
        finish();

    }


}
