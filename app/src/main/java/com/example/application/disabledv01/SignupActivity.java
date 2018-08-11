package com.example.application.disabledv01;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    ConnectionDetector cd;
    String ServerURL = "http://hti-project.000webhostapp.com/model/signup.php" ;
    String TempName, TempEmail, Temppassword;
    private static final String TAG = "SignupActivity";
    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.rinput_password) EditText _rpasswordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;
    private ProgressDialog progress;
    boolean sighnup=false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        cd= new ConnectionDetector(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (cd.isConnected()) {

                    if (validate()) {
                        new MyAsyncTaskresource().execute("http://hti-project.000webhostapp.com/model/cemail.php?email=" + _emailText.getText().toString());
                    }
//
// signup();

//                    if (validate()) {
//
//                        GetData();
//
//                        InsertData(TempName, TempEmail, Temppassword);
//
//                    }
                }
                else {
                    Toast.makeText(SignupActivity.this,"Please connect to the internet,you're not connected",Toast.LENGTH_LONG).show();
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent nn1 = new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(nn1);
            }
        });
    }

    public void GetData(){

        TempName = _nameText.getText().toString();

        TempEmail = _emailText.getText().toString();

        Temppassword = _passwordText.getText().toString();

    }
    ////////////////////////Insert data//////////////////////////////////////

    public void InsertData(final String name, final String email, final String password){

         class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String NameHolder = name ;
                String EmailHolder = email ;
                String passwordHolder = password ;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("name", NameHolder));
                nameValuePairs.add(new BasicNameValuePair("email", EmailHolder));
                nameValuePairs.add(new BasicNameValuePair("password", passwordHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }


            @Override
            protected void onPostExecute(String result) {


                super.onPostExecute(result);



            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(name, email, password);
    }

    public void signup() {



            Log.d(TAG, "Signup");

            if (!validate()) {
                onSignupFailed();
                return;
            } else {

                _signupButton.setEnabled(false);
                progress = ProgressDialog.show(SignupActivity.this, "Signing up...", "Please Wait.");


                // TODO: Implement your own signup logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                onSignupSuccess();
                                // onSignupFailed();
                                progress.dismiss();
                            }
                        }, 3000);
            }

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Toast.makeText(SignupActivity.this, "You Have Signed up Successfully , Your Data Is Secured.", Toast.LENGTH_LONG).show();
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Sorry , there is an error on signing up .. please check the data you entered", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }
    //////////////////////////////////Validation////////////////////////

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String rpassword=_rpasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("Name must contain at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()  ) {
            _emailText.setError("Please,Enter a valid email address form");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (rpassword.isEmpty() || ! rpassword.equals(password)) {
            _rpasswordText.setError("Sorry,Passwords are not the same");
            valid = false;
        } else {
            _rpasswordText.setError(null);
        }


        return valid;
    }






    String result = "";
    @SuppressLint("StaticFieldLeak")
    public class MyAsyncTaskresource extends AsyncTask<String, String, String> {
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

                JSONArray jArray = new JSONArray(result);

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject json = jArray.getJSONObject(i);

                    s = s + "login info : " + json.getString("user_id")
                            + " " + json.getString("user_name");


                    break;

                }


                if (s.length() > 0) {



                    Toast.makeText(getApplicationContext(), "This email ia already used ", Toast.LENGTH_LONG).show();
                    _emailText.setError("Email exists");
                    sighnup = true;

                }



            }
            catch (Exception e) {

// TODO: handle exception

                Log.e("log_tag", "Error Parsing Data "+e.toString());

            }

            if ((!sighnup)) {

                if (validate()) {
                    signup();
                    GetData();
                    InsertData(TempName, TempEmail, Temppassword);

                }
            }
            sighnup=false;

//            else {
//                _emailText.setError("Email exists");
//
//            }
        }




    }




}