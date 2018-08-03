package com.example.application.disabledv01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences=getSharedPreferences("acs", Context.MODE_PRIVATE);

        String test =sharedPreferences.getString("a","1");
        if(test.equals("1")) {
            Intent s = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(s);
            finish();
        }
        if (test.equals("2"))
        {
            Intent s = new Intent(MainActivity.this, Dashboard.class);
            startActivity(s);
            finish();
        }


    }


}
