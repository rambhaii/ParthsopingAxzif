package com.app.axzif.AddLocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.axzif.Address.ui.BillingDetailsActivity;
import com.app.axzif.Address.ui.OrderAddressViewListAdapter;
import com.app.axzif.Address.ui.OrderAllViewActivity;
import com.app.axzif.Address.ui.OrderSummaryActivity;
import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Dashboard.ui.DashboardMain;
import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class AddAddressActivity extends AppCompatActivity  implements View.OnClickListener {
    TextView add_address,deliver_here;
    Loader loader;
    RecyclerView recycler_view;

    String address_id;
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Address View");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddAddressActivity.this, DashboardMain.class);
                startActivity(i);
                finish();

            }
        });


        recycler_view=findViewById(R.id.recycler_view);


        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        add_address=findViewById(R.id.add_address);
        add_address.setOnClickListener(this);

        deliver_here=findViewById(R.id.deliver_here);
        deliver_here.setOnClickListener(this);


        if (UtilMethods.INSTANCE.isNetworkAvialable(AddAddressActivity.this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.GetUserAddress(AddAddressActivity.this,  loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(AddAddressActivity.this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Loader loader;

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        if (UtilMethods.INSTANCE.isNetworkAvialable(AddAddressActivity.this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.GetUserAddress(AddAddressActivity.this,  loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(AddAddressActivity.this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }


    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("GetUserAddress")){

            dataparse(fragmentActivityMessage.getMessage());

        }

        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("hitaddress")){

            if (UtilMethods.INSTANCE.isNetworkAvialable(AddAddressActivity.this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.GetUserAddress(AddAddressActivity.this,  loader);

            } else {
                UtilMethods.INSTANCE.NetworkError(AddAddressActivity.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }

        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    AddAddressAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    public void dataparse(String respose) {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {

            address_id=transactionsObjects.get(0).getId()+"";
            String hitAdd=""+  new Gson().toJson(transactionsObjects.get(0)).toString();
            UtilMethods.INSTANCE.SetAddress_id(this, address_id+"" ,hitAdd);


            mAdapter = new AddAddressAdapter(transactionsObjects,this);
            mLayoutManager = new LinearLayoutManager(this);
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

        if(view==add_address){

            Intent i=new Intent(this, BillingDetailsActivity.class);
            startActivity(i);

        }


        if(view==deliver_here)
        {

            Intent i=new Intent(this, DashboardMain.class);
            startActivity(i);
            finish();

        }

    }

    public void ItemClick(String id ,Category hitAddress) {

        address_id=id+"";
        String hitAdd=""+  new Gson().toJson(hitAddress).toString();

        UtilMethods.INSTANCE.addAddress_id(this, address_id+"" ,hitAdd);

    }

}