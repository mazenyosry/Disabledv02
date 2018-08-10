package com.example.application.disabledv01;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Move extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

     Spinner spinner;
    ImageButton up , right , left , dowen ;
    String address = "20:16:09:12:02:09";
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public String test;





    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_move);


        up = findViewById(R.id.imageButton);
        right =findViewById(R.id.imageButton3);
        left =findViewById(R.id.imageButton2);
        dowen =findViewById(R.id.imageButton4);
        spinner =findViewById(R.id.speedSet);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Speeds, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        new ConnectBT().execute();

        up.setOnTouchListener(new OnTouchListener() {
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


        left.setOnTouchListener(new OnTouchListener() {
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


        right.setOnTouchListener(new OnTouchListener() {
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


        dowen.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendSignal("2");

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




    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                sendSignal("6");
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                sendSignal("7");
                break;
            case 3:
                // Whatever you want to happen when the thrid item gets selected
                sendSignal("8");
                break;
            case 4:
                // Whatever you want to happen when the thrid item gets selected
                sendSignal("9");
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    private void sendSignal ( String number ) {
        if ( btSocket != null ) {
            try {
                btSocket.getOutputStream().write(number.getBytes());
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


    @SuppressLint("StaticFieldLeak")
    private  class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;



        @Override
        protected  void onPreExecute () {

            progress = ProgressDialog.show(Move.this, "Connecting to the device...", "Please Wait!");

        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
                if ( btSocket==null || !isBtConnected ) {


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
                msg("Please,turn on Bluetooth on Your Mobile and Power On The Chair");
                finish();
            } else {
                msg("Connected,Let's Move.");
                isBtConnected = true;
            }

            progress.dismiss();

        }
    }
}
