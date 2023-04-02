package com.app.axzif.CardView.ui;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import com.app.axzif.Address.ui.OrderSummaryActivity;
import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Favourite.ui.CardListAdapter;
import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    TextView proceed_to_pay_btn;
    TextView item_total_amount;

    int amo_product;
    double amo_product_final;
    Loader loader;
    RelativeLayout main_rec;

    View noDataView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Getid();

    }

    private void Getid() {

        noDataView=findViewById(R.id.noDataView);


        main_rec=findViewById(R.id.main_rec);

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
        proceed_to_pay_btn =  findViewById(R.id.proceed_to_pay_btn);
        proceed_to_pay_btn.setOnClickListener(this);

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

           /* loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);*/

            LinearLayoutManager manager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(manager);
            mAdapter = new CardListAdapter(transactionsObjects,this,main_rec, CartActivity.this,noDataView );
            recycler_view.setAdapter(mAdapter);

            SkeletonScreen skeletonScreen = Skeleton.bind(recycler_view)
                    .load(R.layout.item_skeleton_news)
                    .color(R.color.back_bg)
                    .angle(0)
                    .show();



            UtilMethods.INSTANCE.Cartlist(this, main_rec, null,CartActivity.this,noDataView);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }

    }



    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("cartdetail")){

            finalAmo=0.0;
            finalamouny=0.0;
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

    CardListAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();
    double finalamouny;
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
                finalamouny=finalAmo+finalamouny;


            }

            item_total_amount.setText("\u20B9  "+finalamouny);
            mAdapter = new CardListAdapter(transactionsObjects,this,main_rec, CartActivity.this ,noDataView);
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


            Intent i=new Intent(this, OrderSummaryActivity.class);
            startActivity(i);

         //   startActivity(new Intent(this, PaymentActivity.class));
           // i.putExtra("itemtotalamount",""+item_total_amount.getText().toString().trim());

        }



    }

    public void ItemClick(String productPrice) {

          amo_product= Integer.parseInt(productPrice);

          amo_product_final= Double.parseDouble(amo_product+productPrice);


          Log.e("amo_product_final","amo_product_final  :   "+ amo_product_final);



    }
}
