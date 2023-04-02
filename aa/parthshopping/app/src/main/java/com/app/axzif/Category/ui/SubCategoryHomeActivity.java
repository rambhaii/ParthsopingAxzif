package com.app.axzif.Category.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.R;

public class SubCategoryHomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    String respose;
    String type;
    String name;
    String category_id="";
    String subCategory_id="";
    SubCategoryrHomeAdapter mAdapter;
    SubSubCategoryrHomeAdapter mAdapterType;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_home);

        respose=getIntent().getStringExtra("respose");
        type=getIntent().getStringExtra("type");
        name=getIntent().getStringExtra("name");
        category_id=getIntent().getStringExtra("category_id");
        subCategory_id=getIntent().getStringExtra("subcategory_id");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle(""+name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




        recyclerView=findViewById(R.id.recyclerView);




        if(type.equalsIgnoreCase("Sub")){

            dataparse();

        }else  if(type.equalsIgnoreCase("SubType")){

            dataparseType();


        }

    }

    private void dataparse() {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();
        Log.e("transactionsObjects","   transactionsObjects  :    "+ transactionsObjects.size() );
        if (transactionsObjects.size() > 0) {

            mAdapter = new SubCategoryrHomeAdapter(transactionsObjects, this,"sub");
            mLayoutManager = new LinearLayoutManager(this);
          //  recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.GONE);
        }




    }


    private void dataparseType() {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();
        // Log.e("transactionsObjects","   transactionsObjects  :    "+ transactionsObjects.size() );
        if (transactionsObjects.size() > 0) {

            mAdapterType = new SubSubCategoryrHomeAdapter(transactionsObjects, this,"subsub");
          //  mLayoutManager = new LinearLayoutManager(this);
          //  recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));


            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapterType);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.GONE);
        }




    }






}