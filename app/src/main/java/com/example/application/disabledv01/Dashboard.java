package com.example.application.disabledv01;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    BluetoothAdapter mBTAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mBTAdapter = BluetoothAdapter.getDefaultAdapter(); // get a handle on the bluetooth radio



        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



    }

    public void battery(View v){




        if(!mBTAdapter.isEnabled()) {
            Toast.makeText(getBaseContext(), "Bluetooth not on", Toast.LENGTH_SHORT).show();

            return;
        }

        else {

            Intent ba = new Intent(this,Battery.class);
            startActivity(ba);

        }

    }

    public void callus ( View v){

        Intent ca = new Intent(this,About.class);
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
        editor.apply();
        Intent LogOut = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(LogOut);
        finish();


    }
    public void help (View v){
        Intent z= new Intent(this,Help.class);
        startActivity(z);


    }
    public void profile (View v){
        Intent prof= new Intent(this,Profile.class);
        startActivity(prof);

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
