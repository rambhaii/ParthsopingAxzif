package com.app.axzif.Dashboard.ui;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.rampo.updatechecker.UpdateChecker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
 import android.widget.TextView;
 import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import com.app.axzif.BuildConfig;
import com.app.axzif.CardView.ui.CartActivity;
import com.app.axzif.CardView.ui.WishListActivity;
import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Category.ui.CategoryrAdapter;
import com.app.axzif.Category.ui.CategoryrAdapterhome;
import com.app.axzif.Location.dto.GetLocation;
import com.app.axzif.Location.dto.GlobalData;
import com.app.axzif.Login.dto.LoginResponse;
import com.app.axzif.Login.ui.LoginActivity;
import com.app.axzif.MultiLang.BaseActivity;
import com.app.axzif.Order.ui.OrderActivity;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;
import com.app.axzif.Utils.GooglePlayStoreAppVersionNameLoader;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class DashboardMain extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {
    Category operatorAddrr;
    TextView  userName,mobile,email;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FrameLayout main_container;
    DrawerLayout drawerLayout;
    private static long back_pressed;
    public static int countBackstack = 0;
    private static final int TIME_DELAY = 2000;
    RelativeLayout call,favarate,card,editlocation,reference;
    TextView location,AddressLine;
    RecyclerView recyclerView,recycler_category;
    TextView ic_logout,Profile,support,Share,orderhistory,bt_login;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int AUTOCOMPLETE_REQUEST_CODE = 1;
    Loader loader;
    String version="";
    String versionName="";
    int versionCode;
    Location gps_loc;
    Location network_loc;
    Location final_loc;

    String userCountry, userAddress;

    CategoryrAdapter mAdapter;
    CategoryrAdapterhome mAdapternew;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    TextView tv_version;
    TextView text_count,text_count_wish;
    RelativeLayout Search;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        GetId();
        version= GooglePlayStoreAppVersionNameLoader.newVersion;

        getVersionInfo();
        PopUpdate();
        if (checkPlayServices())
        {
            buildGoogleApiClient();
        }
        getLocation();



        Places.initialize(getApplicationContext(),ApplicationConstant.INSTANCE.key);
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields( Arrays.asList( Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS,Place.Field.TYPES,Place.Field.ADDRESS));
    }

    private void GetId(){
        text_count=findViewById(R.id.text_count);
        text_count_wish=findViewById(R.id.text_count_wish);
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        editlocation=findViewById(R.id.editlocation);
        tv_version=findViewById(R.id.tv_version);
        card=findViewById(R.id.card);
        call=findViewById(R.id.call);
        favarate=findViewById(R.id.favarate);
        reference=findViewById(R.id.reference);
        location=findViewById(R.id.location);
        AddressLine=findViewById(R.id.AddressLine);
        editlocation.setOnClickListener(this);
        card.setOnClickListener(this);
        call.setOnClickListener(this);
        favarate.setOnClickListener(this);
        reference.setOnClickListener(this);
        location.setOnClickListener(this);
        AddressLine.setOnClickListener(this);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        main_container = (FrameLayout) findViewById(R.id.main_container);
        userName = (TextView) findViewById(R.id.userName);
        mobile = (TextView) findViewById(R.id.nameber);
        email = (TextView) findViewById(R.id.email);

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        LoginResponse balanceCheckResponse = new Gson().fromJson(response, LoginResponse.class);













        String Email="";

        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
        Email = ""+balanceResponse;

        if ( Email.equalsIgnoreCase("1")){

            UtilMethods.INSTANCE.CartlistCount(this,text_count);
            UtilMethods.INSTANCE.GetWishlistProductCount(this,text_count_wish);

        }else{

        }

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recycler_view);
       // recycler_category=findViewById(R.id.recycler_category);

       ic_logout=findViewById(R.id.ic_logout);
        bt_login=findViewById(R.id.bt_login);

        Profile=findViewById(R.id.Profile);
        support=findViewById(R.id.support);
        Share=findViewById(R.id.Share);
        orderhistory=findViewById(R.id.orderhistory);

        ic_logout.setOnClickListener(this);
        bt_login.setOnClickListener(this);

        Profile.setOnClickListener(this);
        support.setOnClickListener(this);
        Share.setOnClickListener(this);
        orderhistory.setOnClickListener(this);
        String balanceResponse1 = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
        String  Email1 = ""+balanceResponse1;


        if ( Email1.equalsIgnoreCase("1"))
        {
            ic_logout.setVisibility(View.VISIBLE);
            bt_login.setVisibility(View.GONE);
        }
        else
        {
            ic_logout.setVisibility(View.GONE);
            bt_login.setVisibility(View.VISIBLE);

        }




        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(Color.WHITE);

        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FF4444\">" + "" + "</font>")));

        changeFragment(new ServiceFragment());

        dataParse();
     //   dataparseAddd();

    }

    public void dataparseAddd() {

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String hitAddress = myPreferences.getString(ApplicationConstant.INSTANCE.addhitAddress, "");

        Log.e("hitAddress","As :  "+hitAddress);


        if(!hitAddress.equalsIgnoreCase(""))
        {


            Gson gson = new Gson();
            operatorAddrr = gson.fromJson(hitAddress, Category.class);
           /* name.setText(" "+operatorAddrr.getName());
            addressType.setText(" "+operatorAddrr.getAddressType());
            address.setText(" "+operatorAddrr.getAddress());
            phone.setText(" +91- "+operatorAddrr.getMobile());
            rating.setVisibility(View.VISIBLE);
            address_id=""+operatorAddrr.getId();*/
           // AddressLine.setText(operatorAddrr.getCity()+" :"+operatorAddrr.getZip());
        }
        else
        {/* name.setVisibility(View.GONE);
            addressType.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            rating.setVisibility(View.GONE);*/
            if (checkPlayServices())
            {
                buildGoogleApiClient();
            }

        }
    }

    public void dataParse() {

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Categary, null);

        Gson gson = new Gson();
        transactions = gson.fromJson(response, CategoryRespose.class);
        transactionsObjects = transactions.getData();

       /* if (transactionsObjects.size() > 0)
        {

            mAdapter = new CategoryrAdapter(transactionsObjects, this,"main");
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.GONE);
        }*/

     //   dataParsehome();
    }



    public void dataParsehome() {

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Categary, null);

        Gson gson = new Gson();
        transactions = gson.fromJson(response, CategoryRespose.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {
            mAdapternew = new CategoryrAdapterhome(transactionsObjects, this,"main");
            mLayoutManager = new LinearLayoutManager(this);
            recycler_category.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recycler_category.setItemAnimator(new DefaultItemAnimator());
            recycler_category.setAdapter(mAdapternew);
            recycler_category.setVisibility(View.VISIBLE);
        } else {
            recycler_category.setVisibility(View.GONE);
        }
    }

    public void OpenUpdateDialog() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_available_pop, null);

        TextView tvLater = (TextView) view.findViewById(R.id.tv_later);
        TextView tvOk=(TextView)view.findViewById(R.id.tv_ok);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMarket(DashboardMain.this);
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private static void goToMarket(Context mContext) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + mContext.getPackageName())));
    }

    private  void PopUpdate(){

        // Log.e("version","    versionName    "+versionName +"  version    "+version );

        if(version!=null && !version.equalsIgnoreCase("")){


            if(!versionName.equalsIgnoreCase(version)){

                OpenUpdateDialog();

            }

        }
    }

    private void getVersionInfo() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

            tv_version.setText(" Version  :  "+ BuildConfig.VERSION_NAME);

            // Log.e("versionnn","   versionName   "+versionName+"   versionCode  "+  versionCode+"   version  "+  version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
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

                            getLocation();

                      Log.d("kjscnxcgv","dsnmcv");

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(DashboardMain.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
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
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

         switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        // All required changes were successfully made

                            getLocation();


                        break;

                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;


                    default:
                        break;
                }
                break;
        }

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE)
        {
            if (resultCode == RESULT_OK)
            {
                Place place = Autocomplete.getPlaceFromIntent(data);

                location.setText(""+ place.getName());
               AddressLine.setText(""+place.getAddress());



            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
               // Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.

            }
        }
    }


    public void getLocation()
    {

      /*  try
        {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (mLastLocation == null)
        {
            mLocationRequest = new LocationRequest();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            GlobalData.latitude = mLastLocation.getLatitude();
            GlobalData.longitude = mLastLocation.getLongitude();


             // Log.e("GlobalData.latitude3", "" + GlobalData.latitude);
            // Log.e("GlobalData.longitude3 ", "" + GlobalData.longitude);

           GetLOcation();
        }*/
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            return ;}
        try {
            gps_loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            network_loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (gps_loc != null) {
            final_loc = gps_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else if (network_loc != null) {
            final_loc = network_loc;
            latitude = final_loc.getLatitude();
            longitude = final_loc.getLongitude();
        }
        else {
            latitude = 0.0;
            longitude = 0.0;
        }
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0)
            {   userCountry = addresses.get(0).getLocality();
                //String userCountry1 = addresses.get(0).getSubLocality();

                userAddress = addresses.get(0).getAddressLine(0);

                AddressLine.setText(addresses.get(0).getSubLocality()+" "+addresses.get(0).getAdminArea()+": "+addresses.get(0).getPostalCode());
            }
            else
            {   userCountry = "Unknown";
                //msg.setText(userCountry);
            }}
        catch (Exception e) {
            e.printStackTrace();}




    }
    private void GetLOcation() {
        Log.d("djmnxcv","vxcv");
         GetLocation Location = new GetLocation(this);
          latitude = Location.getLatitude();
         longitude = Location.getLongitude();


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getCountryName();
        String countryName = addresses.get(0).getLocality();

        location.setText(""+countryName);
        AddressLine.setText(""+cityName);
        Log.d("sdfjdfh","sdfvjxcvn"+cityName);


    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        {
            if(id==R.id.home){

                changeFragment(new ServiceFragment());


            }else {

                changeFragment(new ServiceFragment());

            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment targetFragment)
    {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (countBackstack > 0) {
            countBackstack = 0;
//            fm.beginTransaction().replace(R.id.main_container, new ServiceFragment()).commit();

        }else if (back_pressed + TIME_DELAY > System.currentTimeMillis())
        {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private void HitApi() {

        getVersionInfo();
        PopUpdate();

      //  UtilMethods.INSTANCE.CartlistCount(this,text_count);


        if (UtilMethods.INSTANCE.isNetworkAvialable(this))
        {

            UtilMethods.INSTANCE.Category(this,null);
           // UtilMethods.INSTANCE.offer(this,null);

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.products(this, loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }

    }

    @Override
    public void onClick(View view) {


       if(view==reference){


            HitApi();}
     /*  if (view==AddressLine)
            {
                String Email="";

                SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
                Email = ""+balanceResponse;


                if ( Email.equalsIgnoreCase("1"))
                {

                    UtilMethods.INSTANCE.updateAddress(DashboardMain.this,AddressLine,DashboardMain.this);

                }else{

                    Intent intent = new Intent(DashboardMain.this, LoginActivity.class);
                    startActivity(intent);

                }








            }*/




      if(view==favarate){

          String Email="";

          SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
          String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
          Email = ""+balanceResponse;

          // Log.e("Email","  Email"+   Email +"    "+  Email.length() );

          if ( Email.equalsIgnoreCase("1")){

              startActivity(new Intent(this, WishListActivity.class));



          }else{

              Intent intent = new Intent(DashboardMain.this, LoginActivity.class);
              startActivity(intent);

          }


           // new FavouriteFragment().show(getSupportFragmentManager(),"Dialog");


        }

       if(view==card){

           String Email="";

           SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
           String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
           Email = ""+balanceResponse;

           // Log.e("Email","  Email"+   Email +"    "+  Email.length() );

           if ( Email.equalsIgnoreCase("1"))
           {
               startActivity(new Intent(this, CartActivity.class));
           }
           else
           {
               Intent intent = new Intent(DashboardMain.this, LoginActivity.class);
               startActivity(intent);
           }
           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);

       }


       if(view==Profile){



           String Email="";

           SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
           String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
           Email = ""+balanceResponse;


           if ( Email.equalsIgnoreCase("1"))
           {

               startActivity(new Intent(this, ProfileDetail.class));


           }
           else
           {

               Intent intent = new Intent(DashboardMain.this, LoginActivity.class);
               startActivity(intent);

           }


           DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);


       }

     if(view==support){

         startActivity(new Intent(this, SupportActivity.class));

         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);

     }

     if(view==Share)
     {
         Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
         shareIntent.setType("text/plain");
         shareIntent.putExtra(Intent.EXTRA_SUBJECT,"parthshopping");
         String app_url = "https://play.google.com/store/apps/details?id=co.wl.XXXXX";
         shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
         startActivity(Intent.createChooser(shareIntent, "Share via"));
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);

     }

     if(view==orderhistory){

         String Email = "";
         SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
         String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
         Email = "" + balanceResponse;
         if (Email.equalsIgnoreCase("1"))
         {
             startActivity(new Intent(this, OrderActivity.class));
         } else {

             Intent intent = new Intent(this, LoginActivity.class);
             startActivity(intent);

         }
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);
     }
  if (view==bt_login)
  {
      Intent intent = new Intent(this, LoginActivity.class);
      startActivity(intent);
  }


     if(view==ic_logout)
     {
         final SweetAlertDialog alertDialog = new SweetAlertDialog(DashboardMain.this);
         alertDialog.setTitle("Alert!");
         alertDialog.setContentText("Do you want to logout?");
         alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
             @Override
             public void onClick(SweetAlertDialog sweetAlertDialog) {
                 alertDialog.dismiss();
             }
         });

         alertDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
             @Override
             public void onClick(SweetAlertDialog sweetAlertDialog)
             {

                 UtilMethods.INSTANCE.logout(DashboardMain.this);
                 UtilMethods.INSTANCE.SetAddress_id(DashboardMain.this, "" ,"");



             }
         });
         alertDialog.show();
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);
     }


      if(view==location){
      }
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

    public void ItemClick(String id,String type) {

        // Log.e("Catsubproductid","Category  :  "+ id);


        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

             UtilMethods.INSTANCE.Catsubproduct(this, id,type,loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onResume() {

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String Email="";
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
        Email = ""+balanceResponse;

        if ( Email.equalsIgnoreCase("1"))
        {   UtilMethods.INSTANCE.CartlistCount(this,text_count);
            UtilMethods.INSTANCE.GetWishlistProductCount(this,text_count_wish);
        }
        else
        {


        }

        HitApi();
        super.onResume();

    }

    @Override
    public void onPause() {
         super.onPause();
    }




}
