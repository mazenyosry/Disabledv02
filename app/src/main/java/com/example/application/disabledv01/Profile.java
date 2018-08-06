package com.example.application.disabledv01;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Profile extends AppCompatActivity {

    String ServerURL = "http://hti-project.000webhostapp.com/model/signup.php";

    ImageView imageView;
    TextView Email_tx, Name_tx, Id_tx, Location_tx;
    LocationManager lm;
    Location location;
    double longitude, latitude;
    boolean gps_enabled = false;
    private int RESULT_LOAD_IMAGE = 123;
    private String PREFS_NAME = "image";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Location_tx = (TextView) findViewById(R.id.profile_Location);

        try {
            mContext = this;
            imageView = findViewById(R.id.profile_image);
            String path = getPreference(mContext, "imagePath");

            if (path == null || path.length() == 0 || path.equalsIgnoreCase("")) {
            ;

            } else {
                imageView.setImageBitmap(getScaledBitmap(path, 800, 800));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        SharedPreferences sharedPreferences=getSharedPreferences("acs", Context.MODE_PRIVATE);
        String email_ =sharedPreferences.getString("email","1");
        String password_ =sharedPreferences.getString("password","1");





        new MyAsyncTaskresources().execute("http://hti-project.000webhostapp.com/model/login.php?email="+email_+"&password="+password_);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
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


        if (gps_enabled) {
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
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
            List<String> elephantList = Arrays.asList(address.split(","));

            Location_tx.setText(city + "," + state);


        } else if (!gps_enabled) {
            Toast.makeText(getApplicationContext(), "please enable location ", Toast.LENGTH_LONG).show();

        }


    }


    public void setImg(View view) {


        try {
            mContext = this;
            imageView = findViewById(R.id.profile_image);
            String path = getPreference(mContext, "imagePath");

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }




    //*****************************Get json data*******************
    String result = "";

    public void editPassword(View view) {

        ;
    }


    public class MyAsyncTaskresources extends AsyncTask<String, String, String> {
        public String s = "";
        public String d = "";
        public String f = "";

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {


            InputStream isr = null;

            try {
                String URL = params[0];
                java.net.URL url = new URL(URL);
                URLConnection urlConnection = url.openConnection();
                isr = new BufferedInputStream(urlConnection.getInputStream());

            } catch (Exception e) {

                Log.e("log_tag", "Error in http connection " + e.toString());


            }

//convert response to string

            try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(isr, "iso-8859-1"), 8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                isr.close();

                result = sb.toString();

            } catch (Exception e) {

                Log.e("log_tag", "Error  converting result " + e.toString());

            }

//parse json data


            return null;
        }

        protected void onPostExecute(String result2) {
            try {


                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject json = jArray.getJSONObject(i);

                    s = json.getString("user_img");
                    d = json.getString("user_name");
                    f = json.getString("user_email");


                    break;
                }

                if (s.length() > 0) {

                    TextView txtname = (TextView) findViewById(R.id.profile_NAme);
                    TextView txtemail = (TextView) findViewById(R.id.profile_Email);
                    txtname.setText(d);
                    txtemail.setText(f);


                } else {
                    Toast.makeText(getApplicationContext(), "user name or password isnot correct", Toast.LENGTH_LONG).show();

                }


            } catch (Exception e) {

// TODO: handle exception

                Log.e("log_tag", "Error Parsing Data " + e.toString());

            }
        }


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                setPreference(mContext, picturePath, "imagePath");
                imageView
                        .setImageBitmap(getScaledBitmap(picturePath, 800, 800));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private Bitmap getScaledBitmap(String picturePath, int width, int height) {
        BitmapFactory.Options sizeOptions = null;
        try {
            sizeOptions = new BitmapFactory.Options();
            sizeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(picturePath, sizeOptions);

            int inSampleSize = calculateInSampleSize(sizeOptions, width, height);

            sizeOptions.inJustDecodeBounds = false;
            sizeOptions.inSampleSize = inSampleSize;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return BitmapFactory.decodeFile(picturePath, sizeOptions);
    }

    private int calculateInSampleSize(BitmapFactory.Options options,
                                      int reqWidth, int reqHeight) {
        int inSampleSize = 0;
        try {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                // Calculate ratios of height and width to requested height and
                // width
                final int heightRatio = Math.round((float) height
                        / (float) reqHeight);
                final int widthRatio = Math.round((float) width
                        / (float) reqWidth);

                // Choose the smallest ratio as inSampleSize value, this will
                // guarantee
                // a final image with both dimensions larger than or equal to
                // the
                // requested height and width.
                inSampleSize = heightRatio < widthRatio ? heightRatio
                        : widthRatio;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return inSampleSize;
    }

    boolean setPreference(Context c, String value, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    String getPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        String value = settings.getString(key, "");
        return value;
    }



}




