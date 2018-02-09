package com.example.application.disabledv01;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import io.rmiri.buttonloading.ButtonLoading;

public class Sex extends AppCompatActivity {

    ButtonLoading buttonmale, buttonfemale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        buttonfemale=findViewById(R.id.btnfemale);
        buttonfemale.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {

            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }
        });
        buttonmale=findViewById(R.id.btnmale);
        buttonmale.setOnButtonLoadingListener(new ButtonLoading.OnButtonLoadingListener() {
            @Override
            public void onClick() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonmale.setProgress(false);
                    }
                }, 700);
            }

            @Override
            public void onStart() {
                //...

            }

            @Override
            public void onFinish() {
                //...
                Intent s = new Intent(Sex.this,MainActivity.class);
                startActivity(s);
            }
        });
  

    }
}
