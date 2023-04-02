package com.app.axzif.Products.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import pl.droidsonroids.gif.GifImageView;

public class ProductcartsubActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recyclerView;
    Loader loader;
    String respose="";
    GifImageView error_im;

    ProductAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faverate);

        Getid();

    }

    private void Getid() {

        error_im=findViewById(R.id.error_im);


        respose = getIntent().getExtras().getString("respose");

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Product");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if(respose.equalsIgnoreCase("blank")){

            error_im.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }else{

            dataParse(respose);

        }


    }

    public void dataParse(String respose) {/*

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getProducts();

        if (transactionsObjects.size() > 0) {

            mAdapter = new ProductAdapter(transactionsObjects,this,"both");
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            recyclerView.setVisibility(View.GONE);
        }
*/

    }

    @Override
    public void onClick(View view) {


    }

}
