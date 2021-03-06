 package com.official.user.legaldesire;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.official.user.legaldesire.fragments.LawyerRecycler;

 public class UserProfile extends AppCompatActivity {

    private Button b,findLawyer;

    private LocationManager locationManager;
    private LocationListener listener;
    ProgressDialog progressDialog;
    TextView welcome,number,mail;
Location mlocation;
SharedPreferences sharedPreferences;
Boolean bo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_profile);

        //t = (TextView) findViewById(R.id.textView);
        welcome=findViewById(R.id.welcmtxt);
        number=findViewById(R.id.phoneNumber);
        mail=findViewById(R.id.email);
        b = (Button) findViewById(R.id.SOS);
        findLawyer=findViewById(R.id.findLwyers);
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        Log.e("sharedname",String.valueOf( sharedPreferences.getBoolean("dataStored",bo))+"shared");

        welcome.setText("WELCOME "+sharedPreferences.getString("name",null).toUpperCase());
        mail.setText(sharedPreferences.getString("email",null));
        number.setText(sharedPreferences.getString("contact",null));


        findLawyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),LawyerRecycler.class);
                startActivity(intent);
            }
        });
        progressDialog=new ProgressDialog(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               mlocation=location;
                progressDialog.dismiss();

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
        configure_button();
        progressDialog.setMessage("fetching location");
        progressDialog.show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions( this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.SEND_SMS}
                        ,10);
            }

        }else{                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, listener);
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //noinspection MissingPermission


                //Log.e("location",mlocation.toString() );
                SmsManager smsManager = SmsManager.getDefault();
                StringBuffer smsBody = new StringBuffer();
                smsBody.append(" http://maps.google.com/maps?q=" );
                smsBody.append(mlocation.getLatitude());
                smsBody.append(",");
                smsBody.append(mlocation.getLongitude());
                smsManager.sendTextMessage("9398888408", null, smsBody.toString(), null, null);
                Log.e("sms",smsBody.toString() );


            }
        });
    }
}

