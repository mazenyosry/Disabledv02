package com.example.application.disabledv01;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Move extends AppCompatActivity {

    ImageButton up , right , left , dowen ;
    String address = "20:16:09:12:02:09";
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public String test;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_move);


        up = (ImageButton)findViewById(R.id.imageButton);
        right = (ImageButton)findViewById(R.id.imageButton3);
        left = (ImageButton)findViewById(R.id.imageButton2);
        dowen = (ImageButton)findViewById(R.id.imageButton4);


        new ConnectBT().execute();

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendSignal("1");

                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        sendSignal("0");
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendSignal("4");

                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        sendSignal("0");
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendSignal("3");

                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        sendSignal("0");
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });


        dowen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendSignal("4");

                        // PRESSED
                        return true; // if you want to handle the touch event
                    case MotionEvent.ACTION_UP:
                        // RELEASED

                        sendSignal("0");
                        return true; // if you want to handle the touch event
                }
                return false;
            }
        });



    }






//    public void dowen(View view) {
//
//        sendSignal("1");
//        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();
//
//    }
//
//
//    public void left(View view) {
//        sendSignal("2");
//        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();
//
//    }
//
//    public void wright(View view) {
//        sendSignal("3");
//        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();
//
//    }
//
//
//    public void up(View view) {
//        sendSignal("4");
//        Toast.makeText(Move.this, "sent", Toast.LENGTH_LONG).show();
//
//    }



    private void sendSignal ( String number ) {
        if ( btSocket != null ) {
            try {
                btSocket.getOutputStream().write(number.toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }




    private void Disconnect () {
        if ( btSocket!=null ) {
            try {
                btSocket.close();
            } catch(IOException e) {
                msg("Error");
            }
        }

        finish();
    }


    @Override
    protected void onStop() {
        super.onStop();
        Disconnect();
    }

    private void msg (String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }


    private  class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;



        @Override
        protected  void onPreExecute () {

            progress = ProgressDialog.show(Move.this, "Connecting...", "Please Wait!!!");
        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
                if ( btSocket==null || !isBtConnected ) {
//                    SharedPreferences sharedPreferences=getSharedPreferences("device", Context.MODE_PRIVATE);
//                    address =sharedPreferences.getString("dd",null);

                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected");
                isBtConnected = true;
            }

            progress.dismiss();

        }
    }
}
