package com.example.application.disabledv01;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Move extends AppCompatActivity {
    BluetoothSocket btSocket = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

    }

    public void dowen(View view) {
        sendSignal("1");
    }

    public void left(View view) {
        sendSignal("2");
    }

    public void wright(View view) {
        sendSignal("3");
    }

    public void up(View view) {
        sendSignal("4");
    }


    private void sendSignal ( String number ) {
        if ( btSocket != null ) {
            try {
                btSocket.getOutputStream().write(number.toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void msg (String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
