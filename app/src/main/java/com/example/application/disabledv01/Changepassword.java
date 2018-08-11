package com.example.application.disabledv01;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Changepassword extends AppCompatActivity {

    EditText oldpass,newpass,renewpass;
    Button changePassword;
    UpdataData updataData=new UpdataData();
    ConnectionDetector cd;
    private ProgressDialog progress;
    public String email_;



    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        oldpass=findViewById(R.id.old_password);
        newpass=findViewById(R.id.new_password);
        renewpass=findViewById(R.id.rnewpassword);
        changePassword=findViewById(R.id.btn_Changepass);
        cd= new ConnectionDetector(this);


    }

    public void change(View view) {


        SharedPreferences sharedPreferences=getSharedPreferences("acs", Context.MODE_PRIVATE);
        String password_ =sharedPreferences.getString("password","1");
         email_ =sharedPreferences.getString("email","1");



        if (cd.isConnected()) {


            if (validate()) {

                if (oldpass.getText().toString().equals(password_)&&newpass.getText().toString().equals(renewpass.getText().toString()) ) {

                    if (newpass.getText().toString().equals(renewpass.getText().toString())) {
                        final ProgressDialog progress = new ProgressDialog(this);
                        progress.setTitle("Changing password");
                        progress.setMessage("Please wait ...");
                        progress.show();

                        // TODO: Implement your own authentication logic here.
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        // On complete call either onLoginSuccess or onLoginFailed
                                        updataData.execute("http://hti-project.000webhostapp.com/model/password.php?email=" + email_ + "&password=" + newpass.getText().toString());
                                        // onLoginFailed();
                                        Toast.makeText(getBaseContext(), "Password Changed ", Toast.LENGTH_SHORT).show();
                                        Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(in);
                                        finish();
                                        progress.dismiss();
                                    }
                                }, 5000);


                    }

                }
                else {
                    Toast.makeText(getBaseContext(), "Check youe data", Toast.LENGTH_LONG).show();

                }
            }
        }
        else {
            Toast.makeText(Changepassword.this,"Please connect to the internet,you're not connected",Toast.LENGTH_LONG).show();
        }

    }




    /////////////////////////////////////////Validation////////////////////////////
    public boolean validate() {
        boolean valid = true;

        String p1 = oldpass.getText().toString();
        String p2 = newpass.getText().toString();
        String p3 = renewpass.getText().toString();



        if (p1.isEmpty() || p1.length() < 4 || p1.length() > 10) {
            oldpass.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            oldpass.setError(null);
        }

        if (p2.isEmpty() || p2.length() < 4 || p2.length() > 10) {
            newpass.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            newpass.setError(null);
        }

        if (p3.isEmpty() || p3.length() < 4 || p3.length() > 10) {
            renewpass.setError("Password must be between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            renewpass.setError(null);
        }
        return valid;
    }

}
