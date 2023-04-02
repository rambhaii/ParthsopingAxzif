package com.app.axzif.Payment.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultListener {

    TextView continuebt;
    Handler handler;
    String amount_toPay="";
    String address_id;
    RadioButton cashondelivery,paymentgateway;
    String i="1";
    int amount;
    String j="Cash On Delivery";
    Loader loader;
    double total ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent i=getIntent();
        amount_toPay=i.getStringExtra("amount");
        address_id=i.getStringExtra("address");
       // getSupportActionBar().setTitle("Pay amount :"+amount_toPay);
      // Toast.makeText(this, ""+amount_toPay, Toast.LENGTH_SHORT).show();
        amount = Math.round(Float.parseFloat(amount_toPay) * 100);
       getId();

    }

    private void getId() {
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        paymentgateway=findViewById(R.id.paymentgateway);
        cashondelivery=findViewById(R.id.cashondelivery);

        paymentgateway.setOnClickListener(this);
        cashondelivery.setOnClickListener(this);

        handler = new Handler();


        continuebt=findViewById(R.id.continuebt);
        continuebt.setOnClickListener(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Pay Amount : "+"\u20B9  "+amount_toPay);
        toolbar.setTitleTextColor(Color.WHITE);
        Checkout.preload(getApplicationContext());

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    private void showAddItemDialog(final Context c,String j)
    {
         AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Confirmation..!")
                .setMessage("Do U want to continue shopping? with "+""+j)
                 .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    if(i.equals("1"))
                    {
                        if (UtilMethods.INSTANCE.isNetworkAvialable(PaymentActivity.this)) {
                            Log.d("jdbkcbjksdvk",address_id);
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            UtilMethods.INSTANCE.SaveOrder(PaymentActivity.this, address_id, loader,PaymentActivity.this,"0","","0","");

                        } else {
                            UtilMethods.INSTANCE.NetworkError(PaymentActivity.this, getResources().getString(R.string.network_error_title),
                                    getResources().getString(R.string.network_error_message));
                        }
                       // Toast.makeText(PaymentActivity.this, "COD not available your location", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        startPayment();
                    }
                     }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();


                    }
                })
                .create();
        dialog.show();
    }


    @Override
    public void onClick(View view) {

        if(view==paymentgateway){

            i="2";
            j="Online Payment";

        }

  if(view==cashondelivery){


            i="1";
      j="Cash On Delivery";


        }


        if(view==continuebt)
        {
            showAddItemDialog(PaymentActivity.this,j);
        }
    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_Ujd8385mQjXqFH");
        checkout.setImage(R.drawable.rnd_logo);
        final Activity activity = this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "AXZIF SHOPPING");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("theme.color", "#0a7e13");
            options.put("currency", "INR");
            options.put("amount",amount);//pass amount in currency subunits  300*100
            options.put("prefill.email", "jitendra@gmail.com");
            options.put("prefill.contact", "7860342556");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);
            checkout.open(activity, options);

        } catch (Exception e) {
            // Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            Log.d("jdbkcbjksdvk",address_id);
            Toast.makeText(this, "Payment successfull", Toast.LENGTH_SHORT).show();
                   loader.show();
                   loader.setCancelable(false);
                   loader.setCanceledOnTouchOutside(false);
                   UtilMethods.INSTANCE.SaveOrder(this, address_id, loader,this,"1",s,"1",s);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();

    }
}


