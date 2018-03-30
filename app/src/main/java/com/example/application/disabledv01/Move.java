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
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move);

    }

    public void dowen(View view) {
        sendSignal("1");
        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();

    }

    public void left(View view) {
        sendSignal("2");
        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();

    }

    public void wright(View view) {
        sendSignal("3");
        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();

    }

    public void up(View view) {
        sendSignal("4");
        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();

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
