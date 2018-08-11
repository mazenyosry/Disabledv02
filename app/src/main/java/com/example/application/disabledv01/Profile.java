package com.example.application.disabledv01;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Profile extends AppCompatActivity {


    ImageView imageView;
    TextView email_tx, name_tx, Id_tx, Location_tx;
    LocationManager lm;
    Location location;
    Criteria criteria;
    private int PICK_IMAGE_REQUEST = 1;
    LocationManager locationManager;
    double longitude, latitude;
    boolean gps_enabled = false;
    public static final String UPLOAD_KEY = "image";
    private String PREFS_NAME = "image";
    private Context mContext;
    ConnectionDetector cd;
    private Bitmap bitmap;
    private Uri filePath;
    UpdataData updataData=new UpdataData();
    String email_;
    String password_;
    String city;
    String state;
    String stlocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.profile_image);

        SharedPreferences sharedPreferences=getSharedPreferences("acs", Context.MODE_PRIVATE);
        email_ =sharedPreferences.getString("email","1");
         password_ =sharedPreferences.getString("password","1");


        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
         criteria = new Criteria();


        Location_tx = findViewById(R.id.profile_Location);
        cd= new ConnectionDetector(this);


        if (cd.isConnected()) {

        }
        else {
            Toast.makeText(Profile.this,"Please connect to the internet,you're not connected",Toast.LENGTH_LONG).show();
        }







        new MyAsyncTaskresources().execute("http://hti-project.000webhostapp.com/model/login.php?email="+email_+"&password="+password_);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //  public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //  int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

//*******************************Get longitute and latutute****************************************
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        if (gps_enabled ) {

            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, locationListener);
           location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();


            //**************************get current loction****************************************
            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            } catch (IOException e) {
                e.printStackTrace();
            }

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
             city = addresses.get(0).getLocality();
             state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            List<String> elephantList = Arrays.asList(address.split(","));

            Location_tx.setText(city + "," + state);

            stlocation=city.toString()+","+state.toString();


        } else if (!gps_enabled) {
            Toast.makeText(getApplicationContext(), "please enable location ", Toast.LENGTH_LONG).show();

        }


    }


    public void setImg(View view) {


        try {
//            mContext = this;
//            String path = getPreference(mContext, "imagePath");
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void editPassword(View view) {

        Intent changepASS= new Intent(this,Changepassword.class);
        startActivity(changepASS);
    }



    /////////////////////////////////////////Upload Data///////////////////////////////////////////
    String result = "";
    @SuppressLint("StaticFieldLeak")
    public class MyAsyncTaskresources extends AsyncTask<String, String, String> {
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

        protected void onPostExecute(String  result2) {
            try {

                String s = "";
                String l = "";
                String photo="";





                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject json = jArray.getJSONObject(i);

                    s = json.getString("user_name");
                    l = json.getString("user_email");
                    photo=json.getString("user_img");
                    name_tx = (TextView) findViewById(R.id.profile_NAme);
                    email_tx = (TextView) findViewById(R.id.profile_Email);
                    name_tx.setText(s);
                    email_tx.setText(l);
                    updataData.execute("http://hti-project.000webhostapp.com/model/location.php?email="+email_+"&location="+stlocation.toString().replace(" ",","));

                    Glide.with(Profile.this)
                            .asBitmap()
                            .load(photo)
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                                    imageView.setImageBitmap(resource);
                                }
                            });







                    break;

                }





            }
            catch (Exception e) {

// TODO: handle exception

                Log.e("log_tag", "Error Parsing Data "+e.toString());

            }


        }




    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                uploadImage();
                new MyAsyncTaskresources().execute("http://hti-project.000webhostapp.com/model/login.php?email="+email_+"&password="+password_);


            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Profile.this, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {

                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);

                HashMap<String, String> data = new HashMap<>();

                data.put(UPLOAD_KEY, uploadImage);
                String result = rh.sendPostRequest("https://hti-project.000webhostapp.com/model/update.php?email="+email_, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
    }


//    String getPreference(Context c, String key) {
//        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
//        settings = c.getSharedPreferences(PREFS_NAME, 0);
//        String value = settings.getString(key, "");
//        return value;
//    }



}




