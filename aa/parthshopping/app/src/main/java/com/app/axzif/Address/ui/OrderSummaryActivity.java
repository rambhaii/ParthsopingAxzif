package com.app.axzif.Address.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Favourite.ui.CardListAdapter;
import com.app.axzif.Payment.ui.PaymentActivity;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_view;
    Button proceed_to_pay_btn;

    TextView item_total_amount;
    int amo_product;
    double amo_product_final;
    Loader loader;
    RelativeLayout main_rec;
    TextView edit_address;
    RadioButton rating;
    double finalamouny;
    TextView name,addressType,address,phone;

    String address_id="",paymenttype;

    View noDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);


        noDataView=findViewById(R.id.noDataView);
        name=findViewById(R.id.name);
        addressType=findViewById(R.id.addressType);
        address=findViewById(R.id.address);
        phone=findViewById(R.id.phone);
        rating=findViewById(R.id.rating);


        Getid();



    }

    private void Getid() {

        main_rec=findViewById(R.id.main_rec);
        edit_address=findViewById(R.id.edit_address);
        edit_address.setOnClickListener(this);

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        item_total_amount=findViewById(R.id.item_total_amount);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Cart View");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        proceed_to_pay_btn =  findViewById(R.id.proceed_payment_btn);
        proceed_to_pay_btn.setOnClickListener(this);

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.Cartlist(this, main_rec, loader, OrderSummaryActivity.this,noDataView);
           // UtilMethods.INSTANCE.GetUserAddress(this,  loader,this);


        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }

        dataparseAddd();



    }

    Category operatorAddrr;



    @SuppressLint("ResourceAsColor")
    public void dataparseAddd() {

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String hitAddress = myPreferences.getString(ApplicationConstant.INSTANCE.hitAddress, "");

        Log.e("hitAddress","As :  "+hitAddress);


        if(!hitAddress.equalsIgnoreCase("")){

            Gson gson = new Gson();
            operatorAddrr = gson.fromJson(hitAddress, Category.class);
            name.setText(" "+operatorAddrr.getName());
            addressType.setBackgroundColor(R.color.colorBackground);
            addressType.setText(" "+operatorAddrr.getAddressType());
            address.setText(" "+operatorAddrr.getAddress());
            phone.setText(" +91- "+operatorAddrr.getMobile());
            rating.setVisibility(View.VISIBLE);
            address_id=""+operatorAddrr.getId();
        }else {

            name.setVisibility(View.GONE);
            addressType.setVisibility(View.GONE);
            address.setVisibility(View.GONE);
            phone.setVisibility(View.GONE);
            rating.setVisibility(View.GONE);

        }





    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("cartdetail")){

            finalAmo=0.0;
            finalamouny=0.0;

            dataparse(fragmentActivityMessage.getMessage());


        }

        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("GetUserAddress")){

            dataparseAddd();

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        dataparseAddd();

       // startActivity(getIntent());


    }

    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();

        finish();

    }



    CardListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    double finalAmo;

    double num;

    public void dataparse(String respose) {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {

            for(int i=0;i<transactionsObjects.size();i++) {


                try{

                    num = transactionsObjects.get(i).getProductPrice();

                } catch(NumberFormatException ex){

                }


                double productprice =  num * Integer.parseInt(transactionsObjects.get(i).getQuantity())    ;
                int discount = Integer.parseInt(transactionsObjects.get(i).getDiscount());
                double co=  0.01;
                double div=discount * co;
                double muldiv=productprice * div ;
                finalAmo=productprice-muldiv;
                finalamouny=(finalAmo+finalamouny);

                //Toast.makeText(this, ""+finalamouny, Toast.LENGTH_SHORT).show();
            }

            item_total_amount.setText("\u20B9  "+finalamouny);
            mAdapter = new CardListAdapter(transactionsObjects,this,main_rec, OrderSummaryActivity.this,noDataView );
            mLayoutManager = new LinearLayoutManager(this);
            //  recyclerView.setLayoutManager(mLayoutManager);
            recycler_view.setLayoutManager(new GridLayoutManager(this,1));
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);

            recycler_view.setVisibility(View.VISIBLE);
        } else {
            recycler_view.setVisibility(View.GONE);
        }

    }




    @Override
    public void onClick(View view) {


        if(view==proceed_to_pay_btn){

           if(!address_id.equalsIgnoreCase("")){

                         Intent i = new Intent(this, PaymentActivity.class);
                         i.putExtra("amount", finalamouny+"");
                         i.putExtra("paymenttype",paymenttype);
                         i.putExtra("address",address_id);
                         startActivity(i);


//               if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
//
//                   loader.show();
//                   loader.setCancelable(false);
//                   loader.setCanceledOnTouchOutside(false);
//
//                   UtilMethods.INSTANCE.SaveOrder(this, address_id, loader,this);
//
//               } else {
//                   UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
//                           getResources().getString(R.string.network_error_message));
//               }
               
           }else {

               Toast.makeText(this, "Select Address", Toast.LENGTH_SHORT).show();
               
           }

           



        }



      if(view==edit_address){
           Intent i=new Intent(this, OrderAllViewActivity.class);
            startActivity(i);
      }



    }



    public void ItemClick(String productPrice) {

        amo_product= Integer.parseInt(productPrice);
        amo_product_final= Double.parseDouble(amo_product+productPrice);
        Log.e("amo_product_final","amo_product_final  :   "+ amo_product_final);

    }



}
