package com.app.axzif.Address.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.axzif.Location.dto.Root;
import com.app.axzif.Login.dto.LoginResponse;
import com.app.axzif.R;
import com.app.axzif.Utils.ApiClient;
import com.app.axzif.Utils.ApiClient1;
import com.app.axzif.Utils.ApplicationConstant;
import com.app.axzif.Utils.EndPointInterface;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillingDetailsActivity extends AppCompatActivity implements View.OnClickListener , AdapterView.OnItemSelectedListener{

    String itemtotalamount;
    TextView tv_SUBMIT;
    Loader loader;
    String address_type="Home";
    Spinner spinner_address_type;

    String[] spinner_arrary = { "Home", "Work"};



    AutoCompleteTextView name , Address1,country ,PostcodeZIP,CityTown,State,EnterLandmark,Phone,AlternateMobileNo,AdditionalInformation     ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_details);

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);


        spinner_address_type=findViewById(R.id.spinner_address_type);

        name=findViewById(R.id.name);
        Address1=findViewById(R.id.Address1);
        country=findViewById(R.id.country);
        PostcodeZIP=findViewById(R.id.PostcodeZIP);
        CityTown=findViewById(R.id.CityTown);
        State=findViewById(R.id.State);
        EnterLandmark=findViewById(R.id.EnterLandmark);
        Phone=findViewById(R.id.Phone);
        AlternateMobileNo=findViewById(R.id.AlternateMobileNo);
        AdditionalInformation=findViewById(R.id.AdditionalInformation);


        spinner_address_type.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(BillingDetailsActivity.this,android.R.layout.simple_spinner_item,spinner_arrary);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner_address_type.setAdapter(aa);

        tv_SUBMIT=findViewById(R.id.tv_SUBMIT);
        tv_SUBMIT.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Billing Details");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        itemtotalamount=getIntent().getStringExtra("itemtotalamount");

       //   city,    state,    country,    zip,  landmark,    address,    mobile,    alt_mobile,    name,    additional_info,    address_type

        //   UtilMethods.INSTANCE.SaveAddress(this,city,    state,    country,    zip,  landmark,    address,    mobile,    alt_mobile,    name,    additional_info,    address_type, null);


    }

    public int validateForm()
    {

        int flag = 0;


        if (name.getText() != null && name.getText().toString().trim().length() > 0) {



        } else {
            name.setError("Enter  Name ");
            name.requestFocus();
            flag++;
        }

        if (Address1.getText() != null && Address1.getText().toString().trim().length() > 0) {


        } else {
            Address1.setError("Enter Address");
            Address1.requestFocus();
            flag++;
        }

        if (country.getText() != null && country.getText().toString().trim().length() > 0) {


        } else {
            country.setError("Enter Country Name");
            country.requestFocus();
            flag++;
        }


        if (PostcodeZIP.getText() != null && PostcodeZIP.getText().toString().trim().length() > 0) {


        } else {
            PostcodeZIP.setError("Enter PinCode");
            PostcodeZIP.requestFocus();
            flag++;
        }

        if (CityTown.getText() != null && CityTown.getText().toString().trim().length() > 0) {


        } else {
            CityTown.setError("Enter City");
            CityTown.requestFocus();
            flag++;
        }



        if (State.getText() != null && State.getText().toString().trim().length() > 0) {


        } else {
            State.setError("Enter Name");
            State.requestFocus();
            flag++;
        }

        // AutoCompleteTextView ,EnterLandmark,Phone,AlternateMobileNo,AdditionalInformation     ;


        if (EnterLandmark.getText() != null && EnterLandmark.getText().toString().trim().length() > 0) {


        } else {
            EnterLandmark.setError("Enter Landmark");
            EnterLandmark.requestFocus();
            flag++;
        }


        if (Phone.getText() != null && Phone.getText().toString().trim().length()==10) {


        } else {
            Phone.setError("Enter Phone Number");
            Phone.requestFocus();
            flag++;
        }



        if (AlternateMobileNo.getText() != null && AlternateMobileNo.getText().toString().trim().length() > 0) {


        } else {
           // AlternateMobileNo.setError("Select Date Birth");
            AlternateMobileNo.setError("Alternate MobileNo");
            AlternateMobileNo.requestFocus();
            flag++;
        }

        if (AdditionalInformation.getText() != null && AdditionalInformation.getText().toString().trim().length() > 0) {


        } else {
            AdditionalInformation.setError("Enter Additional Information");
            AdditionalInformation.requestFocus();
            flag++;
        }




        return flag;
    }


    @Override
    public void onClick(View view) {

        if(view==tv_SUBMIT){

            if (validateForm() == 0)
            {

                if (UtilMethods.INSTANCE.isNetworkAvialable(this))
                {










                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.SaveAddress(this,CityTown.getText().toString().trim(),    State.getText().toString().trim(),
                          country.getText().toString().trim(),    PostcodeZIP.getText().toString().trim(), EnterLandmark.getText().toString().trim(),
                          Address1.getText().toString().trim(),    Phone.getText().toString().trim(),    AlternateMobileNo.getText().toString().trim(),
                          name.getText().toString().trim(),    AdditionalInformation.getText().toString().trim(),
                          address_type, loader,BillingDetailsActivity.this,0);
            } else

            {
                UtilMethods.INSTANCE.NetworkError(this, this.getResources().getString(R.string.network_error_title),
                        this.getResources().getString(R.string.network_error_message));
            }


        }else {

        }

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

        address_type=spinner_arrary[position]+"";

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}