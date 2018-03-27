package com.example.application.disabledv01;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Timer;
import java.util.TimerTask;

public class Gif extends AppCompatActivity {

    ImageView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        loading = findViewById(R.id.loading);



        Glide.with(Gif.this)
                .load(R.drawable.disgif)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent s = new Intent(Gif.this,LoginActivity.class);
                                startActivity(s);      // this code will be executed after 2 seconds
                            }
                        }, 1000);

                        return false;
                    }
                })
                .into(loading);




    }

    @Override
    protected void onStop() {
        super.onStop();
        this.finish();
    }
}
