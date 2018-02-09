package com.example.application.disabledv01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.rmiri.buttonloading.ButtonLoading;

public class Gender extends AppCompatActivity {

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
                //...
            }

            @Override
            public void onStart() {
                //...
            }

            @Override
            public void onFinish() {
                //...
            }
        });
  

    }
}
