package com.app.axzif.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.axzif.AddLocation.AddAddressActivity;
import com.app.axzif.Address.ui.BillingDetailsActivity;
import com.app.axzif.Address.ui.BillingDetailsEditActivity;
import com.app.axzif.Address.ui.OrderAllViewActivity;
import com.app.axzif.Filter.FilterActivity;
import com.app.axzif.Location.dto.GetLocation;
import com.app.axzif.Location.dto.GlobalData;
import com.app.axzif.MapsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import com.app.axzif.Address.ui.OrderSummaryActivity;
import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Category.ui.SubCategoryHomeActivity;
import com.app.axzif.Category.ui.SubCategoryrAdapter;
import com.app.axzif.Category.ui.SubSubCategoryrAdapter;
import com.app.axzif.Dashboard.dto.Item_Detail_Product;
import com.app.axzif.Dashboard.dto.ProductResponse;
import com.app.axzif.Dashboard.ui.DashboardMain;
import com.app.axzif.Dashboard.ui.ProductListActivity;
import com.app.axzif.Login.dto.DataLogin;
import com.app.axzif.Login.dto.LoginResponse;
import com.app.axzif.Products.ui.ProductcartsubActivity;
import com.app.axzif.R;
import com.app.axzif.Register.ui.RegisteredActivity;
import com.app.axzif.Splash.ui.Splash;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Header;
import retrofit2.http.Part;

import static android.content.Context.MODE_PRIVATE;

public enum UtilMethods {

    INSTANCE;
    public boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }



    public void saveselectdata(Context context, ArrayList<String> id)
    {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.id, String.valueOf(id));
        editor.commit();
    }
    public void saveSelectedColor(Context context,  ArrayList<String> id)
    {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.colorid, String.valueOf(id));
        editor.commit();
    }



    public void GetBrandByCtgSubctg(final Context context,String subcategory_id,   final Loader loader  ) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;
        Log.d("getBranvvdByCtg",      subcategory_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetBrandByCtgSubctg(APIkey,subcategory_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("getBrandByCtgres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().size()>0) {


                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "getBrandByCtg");
                                GlobalBus.getBus().post(activityActivityMessage);



                            } else {


                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    Toast.makeText(context, "Some technical issues!", Toast.LENGTH_SHORT).show();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetColor(final Context context,final String subcategory_id , final Loader loader  ) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("getBrandByCtg",    " , user_id  :   "  + "subcategory_id");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetColor(APIkey,subcategory_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("getBrandByCtgres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().size()>0) {


                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "getcolor");
                                GlobalBus.getBus().post(activityActivityMessage);



                            } else {


                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {
                            }
                    }
                    Toast.makeText(context, "Some technical issues!", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void GetUserAddress(final Context context,   final Loader loader  )
    {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("GetUserAddress",    " , user_id  :   "  + user_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetUserAddress(APIkey,user_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("GetUserAddressres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {
                            Log.d("dsfhghgjghj",""+ new Gson().toJson(response.body()).toString());

                            if (response.body().getData().size()>0)
                            {


                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "GetUserAddress");
                                GlobalBus.getBus().post(activityActivityMessage);
                            }
                            else
                            {


                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public String getRecentLogin(Context context) {
        SharedPreferences myPrefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String key = myPrefs.getString(ApplicationConstant.INSTANCE.regRecentLoginPref, null);
        return key;
    }

    public void setRecentLogin(Context context, String key)
    {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.regRecentLoginPref,key);
        editor.commit();
    }

    public void Successfulnew(final Context context, final String message, final int i, final Activity activity) {



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.poprespose, null);


        Button okButton = (Button) view.findViewById(R.id.okButton);
        TextView msg = (TextView) view.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        msg.setText(""+message);



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (i==1){

                    activity.finish();
                    context.startActivity(new Intent(context, DashboardMain.class));
                    dialog.dismiss();

                } else   if (i==2){

                    activity.finish();
                    context.startActivity(new Intent(context, DashboardMain.class));
                    dialog.dismiss();

                }else   if (i==3)
                {

                    activity.finish();
                     dialog.dismiss();

                }  else {

                    dialog.dismiss();

                }


            }
        });



        dialog.show();



     }


    @SuppressLint("MissingInflatedId")
     public void updateAddress(final Context context, TextView AddressLine,Activity activity)
    {

        Loader loader;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_address, null);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
       Button okButton = (Button) view.findViewById(R.id.okButton);
       Button Cancel = (Button) view.findViewById(R.id.Cancel);
       //EditText msg = (EditText) view.findViewById(R.id.msg);
        TextView resposestatus = (TextView) view.findViewById(R.id.resposestatus);
        loader = new Loader(context, android.R.style.Theme_Translucent_NoTitleBar);
        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resposestatus.setText("Select Address");
   //     msg.setText(""+message);
        final RadioGroup radioGroup;

        radioGroup = view.findViewById(R.id.groupradio);

        dialog.show();
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });



        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1)
                {
                    RadioButton selectedRadioButton;
                    selectedRadioButton = view.findViewById(selectedRadioButtonId);
                    String paymentType = selectedRadioButton.getText().toString();
                    if (paymentType.equalsIgnoreCase("Choose current location"))
                    {
                       // context.startActivity(new Intent(context, MapsActivity.class));
                        GetLocation getLocation;
                        Location gps_loc = null;
                        Location network_loc=null;
                        Location final_loc;
                        double longitude;
                        double latitude;
                        String userCountry, userAddress;
                        @SuppressLint("ServiceCast") LocationManager locationManager
                                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
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
                        ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 1);
                        try {
                            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            if (addresses != null && addresses.size() > 0)
                            {   userCountry = addresses.get(0).getLocality();
                                //String userCountry1 = addresses.get(0).getSubLocality();

                                userAddress = addresses.get(0).getAddressLine(0);
                                 AddressLine.setText(addresses.get(0).getSubLocality()+" :"+addresses.get(0).getPostalCode());
                               Log.d("dskjvbuig",addresses.get(0).getSubLocality()+" :"+addresses.get(0).getPostalCode());
                                loader.show();
                                loader.setCancelable(false);
                                loader.setCanceledOnTouchOutside(false);
                                UtilMethods.INSTANCE.SaveCurrentAddress(context,
                                        addresses.get(0).getSubLocality(),
                                        addresses.get(0).getLocality(),
                                        addresses.get(0).getCountryName(),
                                        addresses.get(0).getPostalCode(),
                                        addresses.get(0).getAddressLine(0),
                                        addresses.get(0).getAddressLine(0),
                                        addresses.get(0).getPhone(),

                                      "",
                                       "",
                                        "",
                                        "", loader, activity, 1);




                               }
                            else
                            {   userCountry = "Unknown";
                                //msg.setText(userCountry);
                            }}
                        catch (Exception e) {
                            e.printStackTrace();}



                        dialog.dismiss();
                    }
                    else
                    {
                     context.startActivity(new Intent(context, AddAddressActivity.class));
                        //UtilsMethod.INSTANCE.payment(BookingReview.this,cartId,"cash","ascdsadf","cash", String.valueOf(actualprice),"asdfds","sdvsasd");
                        dialog.dismiss();
                    }

                }

            }
        });


    }





















    public void Error(final Context context,final String message, final  int i) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.poprespose, null);


        Button okButton = (Button) view.findViewById(R.id.okButton);
        TextView msg = (TextView) view.findViewById(R.id.msg);
        TextView   resposestatus = (TextView) view.findViewById(R.id.resposestatus);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        msg.setText(""+message);
        resposestatus.setText("Failed");


        resposestatus.setTextColor(context.getResources().getColor(R.color.red));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

      //  dialog.show();

    }

    public void NetworkError(final Context context, String title, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCustomImage(AppCompatResources.getDrawable(context,R.drawable.ic_connection_lost_24dp))
                .show();
    }

    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void locationreposeval(Context context, String locationreposeval ) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
         editor.putString(ApplicationConstant.INSTANCE.locationreposeval, locationreposeval);
        editor.commit();

    }

    public void secureLogin(final Context context, final String mobile , final Loader loader, final Activity activity) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

          Log.e("secureLogin",    " , APIkey : " + APIkey + " , mobile : " + mobile);


        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.secureLogin( APIkey ,mobile);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                     Log.e("secureLoginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getStatus().equalsIgnoreCase("1") ||
                                    response.body().getData().getStatus().equalsIgnoreCase("2")  ) {



                           //   Toast.makeText(context, "Otp has been send on  "+mobile, Toast.LENGTH_SHORT).show();
                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("otpdetail","" +  response.body().getData().getOtp().toString());
                                GlobalBus.getBus().post(activityActivityMessage);


                              /*  UtilMethods.INSTANCE.setLoginrespose(context, new Gson().toJson(response.body()).toString(),"1");

                                Intent i=new Intent(context, DashboardMain.class);
                                context.startActivity(i);

                                activity.finish();
*/


                            } else {

                                UtilMethods.INSTANCE.Error(context,response.body().getData().getMessage()+"",0);


                            }


                        } catch (Exception ex) {

                            UtilMethods.INSTANCE.Error(context, ""+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {
                            }}UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);}});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void CountProductReview(final Context context, final String productid , final Loader loader , final RatingBar rating) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("secureLogin",    " , APIkey : " + APIkey + " , mobile : " + productid);


        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.CountProductReview( APIkey ,productid);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                     Log.e("secureLoginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getCnt()!=null)
                            {
                                rating.setRating(Float.parseFloat("1.5"));
                                rating.setMax(5);
                                rating.setRating(Float.parseFloat(""+response.body().getData().getCnt()));

                                Log.d("dkjsghj",""+Float.parseFloat(""+response.body().getData().getCnt()));

                            } else {

                                UtilMethods.INSTANCE.Error(context,response.body().getData().getMessage()+"",0);

                            }


                        } catch (Exception ex) {

                            UtilMethods.INSTANCE.Error(context, ""+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }
                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ProductRating(final Context context, final String productid , final RatingBar rating)
    {

        String APIkey=ApplicationConstant.INSTANCE.apikey;
        Log.e("dkjf",    " , APIkey : " + APIkey + " , mobile : " + productid);


        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.ProductReviewRating( APIkey ,productid);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response)
                {

                    Log.e("yutuety", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null)
                    {  try {

                        if (response.body().getData().getRetingAvg()!=null)
                            {
                                rating.setRating(Float.parseFloat("1.5"));
                                rating.setMax(5);
                                rating.setRating(Float.parseFloat(""+response.body().getData().getRetingAvg()));

                                LayerDrawable stars = (LayerDrawable) rating.getProgressDrawable();
                                stars.getDrawable(2).setColorFilter(Color.parseColor("#e6b044"), PorterDuff.Mode.SRC_ATOP);



                            } else {

                                UtilMethods.INSTANCE.Error(context,response.body().getData().getMessage()+"",0);

                            }


                        } catch (Exception ex) {

                            UtilMethods.INSTANCE.Error(context, ""+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t)
                {

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void verifyOtp(final Context context, final String phone ,String otpdeta, final Loader loader,
                          final Activity loginActivity) {
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("login",       " , email : "+ phone  +  " , password : "+ phone);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.verify(APIkey,phone,otpdeta);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                  if(response.body().getData().getName().equals(""))
                                  {

                                      Intent intent = new Intent(context, RegisteredActivity.class);
                                      intent.putExtra("number", "" + phone);
                                      intent.putExtra("id", "" + response.body().getData().getUserId() );
                                      context.startActivity(intent);
                                      loginActivity.finish();
                                  }
                                  else {
                                      UtilMethods.INSTANCE.setLoginrespose(context, new Gson().toJson(response.body().getData()).toString(), "1");
                                      Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show();
                                      loginActivity.finish();
                                      Log.d("skdg",new Gson().toJson(response.body().getData()).toString());
                                      //UtilMethods.INSTANCE.Successfulnew(context,""+response.body().getData().getMessage(), 3,loginActivity);
                                  }
//
//



                            }else {

                                // UtilMethods.INSTANCE.Failed(context,""+response.body().getMessage(),0);

                                UtilMethods.INSTANCE.Error(context," "+response.body().getData().getMessage(),0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context,"Ex : "+ex.getMessage(),0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"T :"+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Category(final Context context, final Loader loader) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;




        // Log.e("Category",    " , Email : " );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.Category(APIkey);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("Categoryres", "is : " + new Gson().toJson(response.body()).toString());
                 //   Toast.makeText(context, ""+new Gson().toJson(response.body()), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {





                            UtilMethods.INSTANCE.setCategaryRespose(context, new Gson().toJson(response.body()).toString());





                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Banner(final Context context, final Loader loader) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;




        // Log.e("Category",    " , Email : " );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.Banner(APIkey);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("Categoryresw", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {





                            UtilMethods.INSTANCE.setSlider(context, new Gson().toJson(response.body()).toString());





                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SubSubCategoryrAdapter mAdapterSub;
    SubCategoryrAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();


  public void GetSubcategory(final Context context, final String GetSubcategory, final Loader loader, final RecyclerView recyclerView,
                             final ImageView downlayout) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetSubcategory(APIkey,GetSubcategory);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    // Log.e("Categoryres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().size()>0){

                               // downlayout.setVisibility(View.VISIBLE);

                                String responsedd = ""+new Gson().toJson(response.body()).toString();
                                Gson gson = new Gson();
                                transactions = gson.fromJson(responsedd, CategoryRespose.class);
                                transactionsObjects = transactions.getData();
                                // Log.e("transactionsObjects","   transactionsObjects  :    "+ transactionsObjects.size() );
                                if (transactionsObjects.size() > 0) {

                                    mAdapter = new SubCategoryrAdapter(transactionsObjects, context,"sub");
                                    mLayoutManager = new LinearLayoutManager(context);
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(mAdapter);
                                    recyclerView.setVisibility(View.VISIBLE);

                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                }




                            }else {

                                ((DashboardMain) context).ItemClick(GetSubcategory,"cat");

                              //  downlayout.setVisibility(View.GONE);



                            }





                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




  public void GetSubcategoryHome(final Context context, final String GetSubcategory, final String name, final Loader loader ) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetSubcategory(APIkey,GetSubcategory);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("Categoryres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().size()>0){
                                Intent intent = new Intent(context, SubCategoryHomeActivity.class);
                                intent.putExtra("respose","" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("type","Sub");
                                intent.putExtra("name",""+name);
                                intent.putExtra("CategoryId",""+GetSubcategory);
                                context.startActivity(intent);
                            }else {

                                ((DashboardMain) context).ItemClick(GetSubcategory,"cat");





                            }





                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






   public void GetSubSubcategory(final Context context, final String Subcategory_id,String category_id, final Loader loader, final RecyclerView recyclerView, final ImageView downlayout) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetSubSubcategory(APIkey,category_id,Subcategory_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    // Log.e("Categoryres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().size()>0){

                                downlayout.setVisibility(View.VISIBLE);

                                String responsedd = ""+new Gson().toJson(response.body()).toString();
                                Gson gson = new Gson();
                                transactions = gson.fromJson(responsedd, CategoryRespose.class);
                                transactionsObjects = transactions.getData();
                                // Log.e("transactionsObjects","   transactionsObjects  :    "+ transactionsObjects.size() );
                                if (transactionsObjects.size() > 0) {

                                    mAdapterSub = new SubSubCategoryrAdapter(transactionsObjects, context,"subsub");
                                    mLayoutManager = new LinearLayoutManager(context);
                                    recyclerView.setLayoutManager(mLayoutManager);
                                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                                    recyclerView.setAdapter(mAdapterSub);
                                    recyclerView.setVisibility(View.VISIBLE);

                                } else {
                                    recyclerView.setVisibility(View.GONE);
                                }




                            }else {

                                ((DashboardMain) context).ItemClick(Subcategory_id,"cat");

                                downlayout.setVisibility(View.GONE);



                            }





                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 public void GetSubSubcategoryHome(final Context context, final String Subcategory_id, String category_id, final String name, final Loader loader ) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetSubSubcategory(APIkey,category_id,Subcategory_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    // Log.e("Categoryres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().size()>0){

                                Intent intent = new Intent(context, SubCategoryHomeActivity.class);
                                intent.putExtra("respose","" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("type","SubType");
                                intent.putExtra("name",""+name);
                                intent.putExtra("subCategory",""+Subcategory_id);

                                context.startActivity(intent);



                            }else {

                                ((DashboardMain) context).ItemClick(Subcategory_id,"cat");




                            }





                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



 public void GetProductList(final Context context, final String category_id,final String Subcategory_id,final String subcategorytype_id, final Loader loader ) {
         Log.d("dkjfmnbn",category_id);
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetProductList(APIkey,category_id,Subcategory_id,subcategorytype_id);
        //   Call<CategoryRespose> call = git.GetProductList(APIkey,category_id,Subcategory_id,subcategorytype_id);
            call.enqueue(new Callback<CategoryRespose>()
            {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response)
                {

                     Log.d("Categodfhgryres", "is : " + "djfhfd");


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().size()>0)
                            {

                                Intent intent = new Intent(context, ProductListActivity.class);
                                intent.putExtra("respose","" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("subCategoryId",Subcategory_id );
                                intent.putExtra("categoryId",category_id );
                                intent.putExtra("type","1");

                                context.startActivity(intent);

                            }else {

                                Toast.makeText(context, "No Product", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t)
                {
                    Log.d("dkfghdfherw",t.getMessage());
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




public void GetProductListByBrandNdColor(Activity context, final String category_id,final String Subcategory_id,
                                        ArrayList<String> brand_id, ArrayList<String> color_id, final Loader loader ,final String  max_price,final String min_price) {
    UtilMethods.INSTANCE.saveselectdata(context,brand_id);
      UtilMethods.INSTANCE.saveSelectedColor(context,color_id);
      String APIkey=ApplicationConstant.INSTANCE.apikey;
      Log.d("dhgfjhg",category_id+"    "+new Gson().toJson(brand_id)+"    " +new Gson().toJson(color_id));
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
             Call<CategoryRespose> call = git.GetProductListByBrandNdColor(APIkey,category_id,Subcategory_id,new Gson().toJson(brand_id),0,0,new Gson().toJson(color_id));
             call.enqueue(new Callback<CategoryRespose>()
             {
                 @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.d("Categoryres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null)
                    {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().size()>0)
                            {
                                Intent intent = new Intent(context, ProductListActivity.class);
                                intent.putExtra("respose","" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("subCategoryId",Subcategory_id);
                                intent.putExtra("categoryId",category_id );
                                intent.putExtra("type","2");
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                  context.startActivity(intent);
                                  context.finish();
                            }else
                            {

                                Toast.makeText(context, "No Product", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {


                              UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t)
                {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void GetProductListByBrandNdColorDash(final Activity context, final String category_id,final String Subcategory_id,
                                             String brand_id,String color_id, final Loader loader ,final String  max_price,final String min_price) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;
        Log.d( "bkjbvkjsbdvk",max_price+"   "+min_price+"   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetProductListByBrandNdColor(APIkey,category_id,"",null,Integer.parseInt(min_price),Integer.parseInt(max_price),null);
            call.enqueue(new Callback<CategoryRespose>() {
                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {
                    if (response != null)
                    {  if (loader != null)
                    {  if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().size()>0){
                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "filterProduct");
                                GlobalBus.getBus().post(activityActivityMessage);
                            }else {
                                Toast.makeText(context, "No Product", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {


                            //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void signup( Context context,  String name,  String email,
                        String password,  Loader loader,
                        Activity registerActivity ,String id, String address1,String address2,String state,String country,String city,String zip,
                       String mobile,String image  )
    {
      Log.d("dbckxmnbcvjdgv",id);

        try{
            File file = new File(image);
            Log.e("profileimage", "" + file);
            MultipartBody.Part fileToUpload1;
            if (image.equalsIgnoreCase("1"))
            {
                fileToUpload1 = MultipartBody.Part.createFormData("files", "");

            } else {
                RequestBody requestBody1 = RequestBody.create(MediaType.parse("*image/*"), file);
                fileToUpload1 = MultipartBody.Part.createFormData("files", file.getName(), requestBody1);
            }

            RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), id);
            RequestBody first_name = RequestBody.create(MediaType.parse("text/plain"), name);
            RequestBody email_id = RequestBody.create(MediaType.parse("text/plain"), email);
            RequestBody pass = RequestBody.create(MediaType.parse("text/plain"), password);

            RequestBody contact = RequestBody.create(MediaType.parse("text/plain"),mobile);
            RequestBody country_name = RequestBody.create(MediaType.parse("text/plain"), country);
            RequestBody city_name = RequestBody.create(MediaType.parse("text/plain"), city);
            RequestBody state_name = RequestBody.create(MediaType.parse("text/plain"), state);
            RequestBody address1_name = RequestBody.create(MediaType.parse("text/plain"), address1);
            RequestBody address2_name = RequestBody.create(MediaType.parse("text/plain"), address2);
            RequestBody z_zipcode = RequestBody.create(MediaType.parse("text/plain"), zip);
            String APIkey=ApplicationConstant.INSTANCE.apikey;
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.signup(APIkey,user_id,first_name,email_id,pass,address1_name,
                    address2_name,state_name,country_name,city_name,z_zipcode,contact,fileToUpload1);
            call.enqueue(new Callback<LoginResponse>()
            {
                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response)
                {
                       Log.d("dfjghkdjfgfhkjdgh",response.body().getData().getStatus());

                            if (response != null)
                            {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1"))
                            {
                                UtilMethods.INSTANCE.setLoginrespose(context, new Gson().toJson(response.body().getData()).toString(), "1");
                                UtilMethods.INSTANCE.Successfulnew(context,""+response.body().getData().getMessage(), 2,registerActivity);

                                    Log.d("jdfgbmdbk", new Gson().toJson(response.body().getData()).toString());


                            }
                            else {

                                UtilMethods.INSTANCE.Error(context,response.body().getData().getMessage(),0);

                            }

                        } catch (Exception ex)
                        {

                            UtilMethods.INSTANCE.Error(context,"Exists"+ex.getMessage(),0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t)
                {


                    Log.d("dsjhgfjdmbfvd",t.getMessage());
                    if (loader != null)
                    {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,t.getMessage(),0);



                }
            });

        } catch (Exception e)
        {
           Log.d("djbmnxcvbvb",e.getMessage());
            e.printStackTrace();
        }
    }



    public void offer(final Context context, final Loader loader) {

        // Log.e("offer",    " , Email : " );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.offer();
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    // Log.e("offerreds", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                           /* if (response.body().getData().getStatus().equalsIgnoreCase("true")) {


                                UtilMethods.INSTANCE.setSlider(context, new Gson().toJson(response.body()).toString());

                                FragmentActivityMessage fragmentActivityMessage =
                                        new FragmentActivityMessage("VideoResponse", "");
                                GlobalBus.getBus().post(fragmentActivityMessage);


                            } else {




                            }*/


                        } catch (Exception ex) {


                          //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void products(final Context context, final Loader loader) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;


        // Log.e("products",    " , Email : " );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.products(APIkey);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().size()>0) {
                                Log.d( "sjbcbh ",new Gson().toJson(response.body()).toString());

                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "products_detail");
                                GlobalBus.getBus().post(activityActivityMessage);


                            } else {




                            }


                        } catch (Exception ex) {


                          //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void offerTypeProduct(final Context context, final Loader loader,final String type,final int start,final int next) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;


       Log.e("products",  type+"\n"+start+"\n"+next );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ProductResponse> call = git.getOfferTypeProduct(APIkey,next,start,type);
            call.enqueue(new Callback<ProductResponse>() {

                @Override
                public void onResponse(Call<ProductResponse> call, final retrofit2.Response<ProductResponse> response) {
                    if (response != null)
                    {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().size()>0) {
                                Log.d( "sjbcbh ",new Gson().toJson(response.body()).toString());

                                FragmentActivityMessage activityActivityMessage = new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), type);
                                GlobalBus.getBus().post(activityActivityMessage);


                            } else {




                            }


                        } catch (Exception ex) {


                          //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



 public void liveSearchByName(final Context context,String product, final Loader loader) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;


         Log.e("dvsdvdssdgds",    " , Email : " +product );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.liveSearchByName(APIkey,product,0,100);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response)
                {
                    Log.d("shdfjsdfr45","sdhfgjf");

                     Log.e("productres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                       Log.d("yrweyrre","sdfdfgdg"+response.body().getData().size());
                            if (response.body().getData().size()>=0)
                            {

                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "products_detail");
                                GlobalBus.getBus().post(activityActivityMessage);

                            } else {
                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + " ", "products_detail");
                                GlobalBus.getBus().post(activityActivityMessage);
                            }
                        } catch (Exception ex) {

                          //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);

                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                //    UtilMethods.INSTANCE.Error(context,""+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllProduct(final Context context, String start, String next, final Loader loader,final ProgressBar progressBar) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.getAllProduct(APIkey,start,next);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {


                            if (response.body().getData().size()>0) {
                                progressBar.setVisibility(View.GONE);

                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "allProduct");
                                GlobalBus.getBus().post(activityActivityMessage);


                            } else {

                                Toast.makeText(context, "No More Products", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);

                            }


                        } catch (Exception ex) {


                          //  UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void GetProductImg(final Context context, String product_id, final Loader loader, final ImageView image) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;
        Log.e("parthshoppingimage",    " , Email : " + product_id );



        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetProductImg(APIkey,product_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                     Log.e("parthshoppingimageres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {
                            FragmentActivityMessage activityActivityMessage =
                                    new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "productimg");
                            GlobalBus.getBus().post(activityActivityMessage);
                            RequestOptions requestOptions = new RequestOptions();
                            requestOptions.placeholder(R.drawable.noimage);
                            requestOptions.error(R.drawable.noimage);
                           Log.d("kdfjdkfgj",ApplicationConstant.INSTANCE.baseUrl+"/uploads/product/"+response.body().getData().get(0).getProduct_img());
                            Glide.with(context)
                                    .load( ApplicationConstant.INSTANCE.baseUrl+"/uploads/product/"+response.body().getData().get(0).getProduct_img())
                                    .apply(requestOptions)
                                    .into(image);


                        } catch (Exception ex) {



                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void singleproduct(final Context context, final String idss, final Loader loader, final Category operator, final String stock) {

        String APIkey=ApplicationConstant.INSTANCE.apikey;


          Log.e("singleproduct",    " , Email  :   "  + idss);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.singleproduct(APIkey,idss);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response)
                {



                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {


                            if (response.body().getData()!=null)
                            {
                                Log.d("dkjghjdgh",new Gson().toJson(response.body()).toString());
                                Intent intent = new Intent(context, Item_Detail_Product.class);
                                intent.putExtra("respose","" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("operator",idss);
                                intent.putExtra("stock",stock);
                                context.startActivity(intent);
                            } else {


                              /*  Intent intent = new Intent(context, ProductcartsubActivity.class);
                                intent.putExtra("respose","blank" );
                                context.startActivity(intent);*/

                            }


                        } catch (Exception ex) {

                            UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);

                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void similarproduct(final Context context, final String idss, final Loader loader) {
         String APIkey=ApplicationConstant.INSTANCE.apikey;
        Log.e("singleproduct",    " , Email  :   "  + idss);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<ProductResponse> call = git.similarproduct(APIkey,idss);
            call.enqueue(new Callback<ProductResponse>() {

                @Override
                public void onResponse(Call<ProductResponse> call, final retrofit2.Response<ProductResponse> response) {

                    Log.e("singleproductres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {


                            if (response.body().getData()!=null) {
                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "similarProduct");
                                GlobalBus.getBus().post(activityActivityMessage);
                            } else {

                            }


                        } catch (Exception ex) {

                            UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);

                        }

                    }
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addcart(final Context context, String product_id, String qty, final Loader loader, final int flag) {//flag 1==add to cart and 2==Buy Product

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";

        String APIkey=ApplicationConstant.INSTANCE.apikey;



        Log.e("addcart",    " , user_id : " + user_id + " , product_id : " + product_id+ " , qty : " + qty);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.addcart(APIkey, product_id ,user_id,qty);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.d("dsvdsvdsvdsv", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {
                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                if(flag==2)
                             {
                                 Intent i = new Intent(context, OrderSummaryActivity.class);
                                 context.startActivity(i);
                             }
                             else {
                              Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                             }
                               // Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();



                            } else {
                                if(flag==2)
                                {
                                    Intent i = new Intent(context, OrderSummaryActivity.class);
                                    context.startActivity(i);
                                }else {
                                    Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                                }




                            }


                        } catch (Exception ex) {

                            // Log.e("secureLoginerror", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context, "Wrong Credentials User.",0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   public void SaveOrder(final Context context, String address_id, final Loader loader, final Activity activity ,final String paymentMode,final String transaction_id,final String transaction_status,final String transaction_res) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";

        String APIkey=ApplicationConstant.INSTANCE.apikey;



        Log.e("addcart",    " , user_id : " + user_id +"  address_id  "+address_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.SaveOrder(APIkey,user_id,address_id,paymentMode,transaction_id,transaction_status);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    Log.d("dfbdfbfdngfngf", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                               // Log.d( "chbjhvbjkvbjhd: ",response.body().getData().getOrder_id());
                                if(paymentMode=="1")
                                {
                                    UtilMethods.INSTANCE.UpdatePaymentStatus(context,null,activity,response.body().getData().getOrder_id(),transaction_id,transaction_res);
                                }
                                else {
                                    UtilMethods.INSTANCE.Successfulnew(context,"Your "+ response.body().getData().getMessage(),1,activity);
                                }
                            } else {
                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {

                            // Log.e("secureLoginerror", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context, "Wrong Credentials User.",0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }


                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public void UpdatePaymentStatus(final Context context,final Loader loader, final Activity activity ,final String order_id,final String transaction_id,final String transaction_response) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.UpdateTransactionStatus(APIkey,order_id,transaction_id,transaction_response);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {
                    Log.d("xcvxvxcvxcvcxvcx", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {


                                UtilMethods.INSTANCE.Successfulnew(context,""+ response.body().getData().getMessage(),1,activity);




                            } else {

                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();




                            }


                        } catch (Exception ex) {

                            // Log.e("secureLoginerror", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context, "Wrong Credentials User.",0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }


                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   public void AddToWishlist(final Context context, final String product_id, final Loader loader, final ImageView do_wish_image ) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";

        String APIkey=ApplicationConstant.INSTANCE.apikey;



        Log.e("addcart",    " , user_id : " + user_id + " , product_id : " + product_id+ " , qty : " + product_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.AddToWishlist(APIkey,user_id, product_id);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("addcartres", "is : " + new Gson().toJson(response.body().getData().getStatus()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if(response.body().getData().getStatus().equalsIgnoreCase("1")){


                             //   Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                            }else {

                                Loader loader;

                                if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {

                                    loader = new Loader(context,android.R.style.Theme_Translucent_NoTitleBar);

                                    loader.show();
                                    loader.setCancelable(false);
                                    loader.setCanceledOnTouchOutside(false);

                                     do_wish_image.setColorFilter(context.getResources().getColor(R.color.white));

                                    UtilMethods.INSTANCE.RemoveWishList(context, product_id+"",  loader);

                                } else {
                                    UtilMethods.INSTANCE.NetworkError(context, context.getResources().getString(R.string.network_error_title),
                                            context.getResources().getString(R.string.network_error_message));
                                }



                            }





                        } catch (Exception ex) {


                            UtilMethods.INSTANCE.Error(context, "Exception :  ex   "+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context, " onFailure "+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   public void UpdateCartQuantity(final Context context, String product_id, String qty, final Loader loader, final RelativeLayout main_rec, final Activity activity, final View noDataView ) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;



        Log.e("UpdateCartQuantity",    " , user_id : " + user_id + " , product_id : " + product_id+ " , qty : " + qty);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.UpdateCartQuantity(APIkey,user_id, product_id ,qty);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("UpdateCartQuantityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {



                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                UtilMethods.INSTANCE.Cartlist(context, main_rec, loader,activity,noDataView);



                            } else {

                                 Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception ex) {

                            // Log.e("secureLoginerror", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context, "Wrong Credentials User.",0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void SaveCurrentAddress(final Context context, String city, String  state, String  country, String  zip, String landmark, String  address,
                            String  mobile, String  alt_mobile, String  name, String  additional_info, String  address_type, final Loader loader , final Activity activity,int status) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("UpdateCartQuantity",    " , user_id : " + user_id + " , product_id : " + " , qty : " );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.SaveAddress(APIkey,user_id,city,state,country,zip,address,landmark,mobile,alt_mobile,name,
                    additional_info,address_type);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("UpdateCartQuantityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1"))
                            {

                                Category category=new Category();
                                  category.setCity(city);
                                category.setZip(zip);
                                String st= new Gson().toJson(category).toString();

                                UtilMethods.INSTANCE.addAddress_id(context,"",st);
                                   Toast.makeText(context, "Address add successful ", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {


                            UtilMethods.INSTANCE.Error(context, " "+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,""+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void SaveAddress(final Context context, String city, String  state, String  country, String  zip, String landmark, String  address,
                           String  mobile, String  alt_mobile, String  name, String  additional_info, String  address_type, final Loader loader , final Activity activity,int status) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("UpdateCartQuantity",    " , user_id : " + user_id + " , product_id : " + " , qty : " );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.SaveAddress(APIkey,user_id,city,state,country,zip,address,landmark,mobile,alt_mobile,name,
                    additional_info,address_type);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("UpdateCartQuantityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1"))
                            {
                                UtilMethods.INSTANCE.Successfulnew(context,""+response.body().getData().getMessage(),3,activity);
                                if (status==1)
                                {

                                    UtilMethods.INSTANCE.addAddress_id(context,"", new Gson().toJson(response.body().getData()).toString());
                                    Log.d("jhfgdjfh", "is : " + new Gson().toJson(response.body().getData()).toString());
                                }

                               }
                            else
                            {
                                Log.d("dfgj","jhfsdger2sdfg22");

                                 Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {


                            UtilMethods.INSTANCE.Error(context, " "+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,""+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 

  public void UpdateAddress(final Context context, String addressId, String city, String  state, String  country,
                            String  zip, String landmark, String  address, String  mobile,
                            String  alt_mobile, String  name, String  additional_info,
                            String  address_type, final Loader loader , int status,Activity activity) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("UpdateCartQuantity",    " , user_id : " + user_id + " , product_id : " + " , qty : " );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.UpdateAddress(APIkey,""+user_id,addressId,""+city,""+state,""+country,""+zip,""+address,""+landmark,""+mobile,""+alt_mobile,""+ additional_info,""+address_type,name);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.d("hekkklii","" + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1"))
                            {
                                UtilMethods.INSTANCE.GetUserAddress(context,  loader);

                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                activity.finish();
                            } else {

                                 Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception ex) {


                            UtilMethods.INSTANCE.Error(context, " "+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,""+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



  public void DeleteCartProduct(final Context context, String product_id , final Loader loader , final RelativeLayout main_rec ,
                                final Activity activity, final View noDataView) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";


        String APIkey=ApplicationConstant.INSTANCE.apikey;



        Log.e("UpdateCartQuantity",    " , user_id : " + user_id + " , product_id : " + product_id+ " , qty : " + "qty");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.DeleteCartProduct(APIkey,user_id, product_id );
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("UpdateCartQuantityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {



                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                UtilMethods.INSTANCE.Cartlist(context, main_rec, loader,activity,noDataView);


                            } else {

                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();


                            }


                        } catch (Exception ex) {

                            // Log.e("secureLoginerror", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context, "Wrong Credentials User.",0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DeleteWishList(final Context context, String product_id , final Loader loader , final RelativeLayout main_rec , final Activity activity) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";


        String APIkey=ApplicationConstant.INSTANCE.apikey;



        Log.e("UpdateCartQuantity",    " , user_id : " + user_id + " , product_id : " + product_id+ " , qty : " + "qty");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.DeleteWishList(APIkey,user_id, product_id );
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("UpdateCartQuantityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {



                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                UtilMethods.INSTANCE.GetWishlistProduct(context, loader,main_rec,activity);


                            } else {

                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();


                            }


                        } catch (Exception ex) {

                            // Log.e("secureLoginerror", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context, ""+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,""+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


   public void RemoveWishList(final Context context, String product_id , final Loader loader ) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";


        String APIkey=ApplicationConstant.INSTANCE.apikey;



        Log.e("UpdateCartQuantity",    " , user_id : " + user_id + " , product_id : " + product_id+ " , qty : " + "qty");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.DeleteWishList(APIkey,user_id, product_id );
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("UpdateCartQuantityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {



                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();


                            }


                        } catch (Exception ex) {

                            // Log.e("secureLoginerror", ex.getMessage());

                            UtilMethods.INSTANCE.Error(context, ""+ex.getMessage(),0);


                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,""+t.getMessage(),0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Cartlist(final Context context, final RelativeLayout main_rec, final Loader loader , final Activity activity, final View noDataView) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("CatsubproductApi",    " , user_id  :   "  + user_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.cartlist(APIkey,user_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("CatsubproductApires", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().size()>0) {


                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "cartdetail");
                                GlobalBus.getBus().post(activityActivityMessage);




                            } else {
                                noDataView.setVisibility(View.VISIBLE);
                                //activity.finish();
                                main_rec.setVisibility(View.GONE);

                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetOrderList(final Context context, final RelativeLayout main_rec, final Loader loader ,
                             final Activity activity, final View noDataView, final RecyclerView recycler_view ) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("CatsubproductApi",    " , user_id  :   "  + user_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetOrderList(APIkey,user_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("CatsubproductApires2", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().size()>0) {


                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "GetOrderList");
                                GlobalBus.getBus().post(activityActivityMessage);

                                noDataView.setVisibility(View.GONE);



                            } else {


                                recycler_view.setVisibility(View.GONE);
                                noDataView.setVisibility(View.VISIBLE);

                             //   activity.finish();
                                //Toast.makeText(context, "No Item", Toast.LENGTH_SHORT).show();



                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


     public void GetWishlistProduct(final Context context, final Loader loader , final RelativeLayout main_rec, final Activity activity) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("CatsubproductApi",    " , user_id  :   "  + user_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetWishlistProduct(APIkey,user_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("CatsubproductApires", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                            if (response.body().getData().size()>0) {


                                UtilMethods.INSTANCE.setWishList(context,new Gson().toJson(response.body()).toString());


                                FragmentActivityMessage activityActivityMessage =
                                        new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "wishlist");
                                GlobalBus.getBus().post(activityActivityMessage);




                            } else {

                                activity.finish();

                                Toast.makeText(context, "No Item", Toast.LENGTH_SHORT).show();
                                main_rec.setVisibility(View.GONE);
                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



  public void DeleteUserAddress(final Context context,String id, final Loader loader)
  {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("DeleteUserAddress",    " , user_id  :   "  + user_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.DeleteUserAddress(APIkey,user_id,id);
            call.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, final retrofit2.Response<LoginResponse> response) {

                    Log.e("DeleteUserAddressres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null)
                    {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {


                            Toast.makeText(context, ""+response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                            FragmentActivityMessage activityActivityMessage = new FragmentActivityMessage(""  , "hitaddress");
                            GlobalBus.getBus().post(activityActivityMessage);
                       //     UtilMethods.INSTANCE.SetAddress_id(context, id+"" ,"");
                        } catch (Exception ex)
                        {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    public void GetWishlistProductCount(final Context context , final TextView text_count ) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("CatsubproductApi",    " , user_id  :   "  + user_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.GetWishlistProduct(APIkey,user_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("CatsubproductApires", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {



                        try {
                            UtilMethods.INSTANCE.setWishList(context,new Gson().toJson(response.body()).toString());


                            if (response.body().getData().size()>0) {

                                text_count.setVisibility(View.VISIBLE);
                                text_count.setText(""+response.body().getData().size());



                            } else {

                                text_count.setText("0");


                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {


                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public void CartlistCount(final Context context , final TextView text_count ) {

        SharedPreferences myPreferences =  context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
        String user_id=balanceCheckResponse.getUserId()+"";
        String APIkey=ApplicationConstant.INSTANCE.apikey;

        Log.e("CatsubproductApi",    " , user_id  :   "  + user_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.cartlist(APIkey,user_id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    Log.e("CatsubproductApires", "is : " + new Gson().toJson(response.body()).toString());
                  //  Toast.makeText(context, ""+ new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();
                    if (response != null) {



                        try {


                            if (response.body().getData().size()>0) {

                                text_count.setVisibility(View.VISIBLE);

                                text_count.setText(""+response.body().getData().size());



                            } else {

                                text_count.setText("0");


                            }


                        } catch (Exception ex) {




                            //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {


                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





































    public void Catsubproduct(final Context context,String id,String type, final Loader loader) {

        // Log.e("CatsubproductApi",    " , Email  :   "  + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<CategoryRespose> call = git.catsubproduct(type,id);
            call.enqueue(new Callback<CategoryRespose>() {

                @Override
                public void onResponse(Call<CategoryRespose> call, final retrofit2.Response<CategoryRespose> response) {

                    // Log.e("Catsubproductres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {


                           /* if (response.body().getStatus().equalsIgnoreCase("true")) {

                                Intent intent = new Intent(context, ProductcartsubActivity.class);
                                intent.putExtra("respose","" + new Gson().toJson(response.body()).toString());
                                context.startActivity(intent);



                            } else {


                                Intent intent = new Intent(context, ProductcartsubActivity.class);
                                intent.putExtra("respose","blank" );
                                context.startActivity(intent);

                            }*/


                        } catch (Exception ex) {

                            Intent intent = new Intent(context, ProductcartsubActivity.class);
                            intent.putExtra("respose","blank" );
                            context.startActivity(intent);


                        //   UtilMethods.INSTANCE.Error(context, "Wrong Credentials User." +ex.getMessage(),0);
                        }

                    }
                }

                @Override
                public void onFailure(Call<CategoryRespose> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }

                    UtilMethods.INSTANCE.Error(context,"Some technical issues!",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public void setLoginrespose(Context context, String Loginrespose ,String one ) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.Loginrespose, Loginrespose);
        editor.putString(ApplicationConstant.INSTANCE.one, one);
        editor.commit();
  }




    public void setWishList(Context context, String setWishList)
    {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.setWishList, setWishList);
        editor.commit();


    }

  public void SetAddress_id(Context context, String address_id ,String hitAddress )
  {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.address_id, address_id);
        editor.putString(ApplicationConstant.INSTANCE.hitAddress, hitAddress);
        editor.commit();

    }
    public void addAddress_id(Context context, String address_id ,String hitAddress )
    {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.addaddress_id, address_id);
        editor.putString(ApplicationConstant.INSTANCE.addhitAddress, hitAddress);
        editor.commit();

    }

    public void setCategaryRespose(Context context, String Categary) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.Categary, Categary);
         editor.commit();

    }

    public void logout(Context context)
    {
        UtilMethods.INSTANCE.setLoginrespose(context, "", "");
        Intent startIntent = new Intent(context, Splash.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startIntent);

    }

    public void setSlider(Context context, String slider) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.sliderPref, slider);
        editor.commit();

        FragmentActivityMessage fragmentActivityMessage =
                new FragmentActivityMessage("VideoResponse", "");
        GlobalBus.getBus().post(fragmentActivityMessage);

    }
    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

}