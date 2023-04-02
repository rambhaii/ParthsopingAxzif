package com.app.axzif.Splash.ui;

 import android.Manifest;
 import android.content.Intent;
 import android.content.IntentSender;
 import android.content.SharedPreferences;
 import android.content.pm.PackageManager;
 import android.os.Build;
 import android.os.Bundle;

 import androidx.annotation.NonNull;
 import androidx.annotation.Nullable;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;
 import androidx.core.content.ContextCompat;

 import android.view.View;
 import android.view.Window;
 import com.google.android.gms.common.ConnectionResult;
 import com.google.android.gms.common.GoogleApiAvailability;
 import com.google.android.gms.common.api.GoogleApiClient;
 import com.google.android.gms.common.api.PendingResult;
 import com.google.android.gms.common.api.ResultCallback;
 import com.google.android.gms.common.api.Status;
 import com.google.android.gms.location.LocationRequest;
 import com.google.android.gms.location.LocationServices;
 import com.google.android.gms.location.LocationSettingsRequest;
 import com.google.android.gms.location.LocationSettingsResult;
 import com.google.android.gms.location.LocationSettingsStatusCodes;
 import com.google.android.gms.location.places.Places;
 import com.google.android.material.snackbar.Snackbar;

 import com.app.axzif.Dashboard.ui.DashboardMain;
 import com.app.axzif.R;
 import com.app.axzif.Utils.ApplicationConstant;
 import com.app.axzif.Utils.GooglePlayStoreAppVersionNameLoader;
 import com.app.axzif.Utils.UtilMethods;

public class Splash extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String Email="";

    private static final String TAG = "HomeActivity";
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;

    private static String key="2";



    @Override
    protected void onPause() {
         super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.splash_screen);
        new GooglePlayStoreAppVersionNameLoader().execute();
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            UtilMethods.INSTANCE.Category(this,null);
            UtilMethods.INSTANCE.Banner(this,null);
            UtilMethods.INSTANCE.offer(this,null);
        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title), getResources().getString(R.string.network_error_message));
        }
        if (checkPlayServices()) {
            buildGoogleApiClient();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        }

    }

    private void select()
    {
        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
        Email = ""+balanceResponse;
        // Log.e("Email","  Email"+   Email +"    "+  Email.length() );
        if ( Email.equalsIgnoreCase("1")){
            Dashboardpage();
        }else{
            loginpage();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(Splash.this)
                .addOnConnectionFailedListener(Splash.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                          key = ""+myPrefs.getString(ApplicationConstant.INSTANCE.locationreposeval, null);
                        
                        if(key.equalsIgnoreCase("1")){
                            select();
                        }else{
                            // Toast.makeText(Splash.this, "permition allow ", // Toast.LENGTH_SHORT).show();
                        }


                       

                         break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {status.startResolutionForResult(Splash.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }


    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_REQUEST).show();


            } else {
                finish();
            }
            return false;
        }
        return true;
    }


    public void loginpage() {

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    startLogin();
                }
            }
        };
        timerThread.start();
    }

     public void Dashboardpage() {

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    DashboardLogin();
                 }
            }
        };
        timerThread.start();
    }

    public void DashboardLogin() {
        Intent intent = new Intent(Splash.this, DashboardMain.class);
        startActivity(intent);
        finish();
    }

    public void startLogin() {
         Intent intent = new Intent(Splash.this, DashboardMain.class);
       //  Intent intent = new Intent(Splash.this, LoginActivity.class);
       // Intent intent = new Intent(Splash.this, DashboardMain.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ASK_MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean FINE_LOCATIONPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean COARSE_LOCATIONPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (FINE_LOCATIONPermission && COARSE_LOCATIONPermission) {

                        // // Toast.makeText(Splash.this, "SUCCESS  Done ", // // Toast.LENGTH_SHORT).show();
                        UtilMethods.INSTANCE.locationreposeval(this,"1");


                        select();




                    } else {

                        Snackbar.make(this.findViewById(android.R.id.content),
                                "Please Grant Permissions to start service",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                                    }
                                }).show();
                    }
                }
                break;
        }
    }


}
