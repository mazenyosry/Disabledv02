package com.example.application.disabledv01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Mahmo on 8/10/2018.
 */
@SuppressLint("StaticFieldLeak")

public class UpdataData extends AsyncTask<String, String, String> {
    String result = "";

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected String  doInBackground(String... params) {


        InputStream isr = null;

        try{
            String URL=params[0];
            java.net.URL url = new URL( URL);
            URLConnection urlConnection = url.openConnection();
            isr  = new BufferedInputStream(urlConnection.getInputStream());

        }

        catch(Exception e){

            Log.e("log_tag", "Error in http connection " + e.toString());



        }

//convert response to string

        try{

            BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);

            StringBuilder sb = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null) {

                sb.append(line + "\n");

            }

            isr.close();

            result=sb.toString();

        }

        catch(Exception e){

            Log.e("log_tag", "Error  converting result " + e.toString());

        }

//parse json data


        return null;
    }




}
