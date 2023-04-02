package com.app.axzif.Order.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    TextView proceed_to_pay_btn;
    TextView item_total_amount;

    int amo_product;
    double amo_product_final;
    Loader loader;
    RelativeLayout main_rec;
    LinearLayout checkoutlayout;
    private View noDataView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Getid();

    }

    private void Getid() {

        noDataView=findViewById(R.id.noDataView);
        checkoutlayout=findViewById(R.id.checkoutlayout);
        checkoutlayout.setVisibility(View.GONE);

        main_rec=findViewById(R.id.main_rec);

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        item_total_amount=findViewById(R.id.item_total_amount);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Order View");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        proceed_to_pay_btn =  findViewById(R.id.proceed_to_pay_btn);
        proceed_to_pay_btn.setOnClickListener(this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(manager);
        mAdapter = new OrderListAdapter(transactionsObjects, this,main_rec, OrderActivity.this);
        recycler_view.setAdapter(mAdapter);

        SkeletonScreen skeletonScreen = Skeleton.bind(recycler_view)
                .load(R.layout.item_skeleton_news)
                .color(R.color.back_bg)
                .angle(0)
                .show();

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

           /* loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);*/

            UtilMethods.INSTANCE.GetOrderList(this, main_rec, null, OrderActivity.this,noDataView,recycler_view);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }

    }



    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("GetOrderList")){

            dataparse(fragmentActivityMessage.getMessage());

        }



    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    OrderListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();


    public void dataparse(String respose) {
        Log.d("kjfdkjfg",respose);
        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();


        if (transactionsObjects.size() > 0) {

            mAdapter = new OrderListAdapter(transactionsObjects,this,main_rec, OrderActivity.this );
            mLayoutManager = new LinearLayoutManager(this);
             recycler_view.setLayoutManager(new GridLayoutManager(this,1));
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            recycler_view.setVisibility(View.VISIBLE);
        } else{
            recycler_view.setVisibility(View.GONE);
        }


    }




    @Override
    public void onClick(View view) {






    }

 }
