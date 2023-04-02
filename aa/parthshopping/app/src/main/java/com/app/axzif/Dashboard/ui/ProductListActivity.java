package com.app.axzif.Dashboard.ui;

import androidx.annotation.NonNull;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.slider.RangeSlider;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Filter.FilterActivity;
import com.app.axzif.Products.ui.ProductAdapterFull;
import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.UtilMethods;

public class ProductListActivity extends AppCompatActivity implements View.OnClickListener {

    String respose;
    String type;
    String subCategoryId="",category_id="";
    RecyclerView recyclerView;
    int max_price=10000;
    int min_price=0;
    LinearLayout filterView,SortView,filterSortView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        filterSortView=findViewById(R.id.filterSortView);
        filterView=findViewById(R.id.filterView);
        SortView=findViewById(R.id.sortView);
        recyclerView=findViewById(R.id.recyclerView);

        filterView.setOnClickListener(this);
        SortView.setOnClickListener(this);

        respose=getIntent().getStringExtra("respose");
        type=getIntent().getStringExtra("type");
        subCategoryId=getIntent().getStringExtra("subCategoryId");
        category_id=getIntent().getStringExtra("categoryId");

        if(type.equalsIgnoreCase("2")){
            filterSortView.setVisibility(View.GONE);
            filterSortView.setVisibility(View.VISIBLE);
        }else {
            filterSortView.setVisibility(View.VISIBLE);
        }
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Product List");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        dataParse(respose);
    }

    ProductAdapterFull mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    public void dataParse(String respose) {
        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();
        if (transactionsObjects.size() > 0) {
            mAdapter = new ProductAdapterFull(transactionsObjects, this,"main");
            mLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view) {
        if(view==filterView){

            startActivity(new Intent(ProductListActivity.this, FilterActivity.class).putExtra("subCategoryId",subCategoryId).putExtra("category_id",category_id));


        }


        if(view==SortView){

            showSortingDialog();


        }

    }

    void showSortingDialog() {
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(this);

        View sheetView = getLayoutInflater().inflate(R.layout.dialog_sorting, null);
         RangeSlider slder=sheetView.findViewById(R.id.discreteRangeSlider);
         slder.setValues(Float.parseFloat(min_price+""),Float.parseFloat(max_price+""));

        TextView text_action_apply=sheetView.findViewById(R.id.text_action_apply);
        TextView text_action_cancel=sheetView.findViewById(R.id.text_action_cancel);
        mBottomSheetDialog.setContentView(sheetView);
        slder.addOnChangeListener(new RangeSlider.OnChangeListener()
        {
            @SuppressLint("RestrictedApi")
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                min_price=slider.getValues().get(0).intValue();
                max_price=slider.getValues().get(1).intValue();
            }
        });
        text_action_apply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                UtilMethods.INSTANCE.GetProductListByBrandNdColorDash(ProductListActivity.this,category_id+"",subCategoryId+"","","",null,max_price+"",min_price+"");
                mBottomSheetDialog.dismiss();
            }
        });
        text_action_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        mBottomSheetDialog.show();

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {

        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("filterProduct")) {
            dataParse("" + fragmentActivityMessage.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this))
        {
            GlobalBus.getBus().register(this);
        }
    }
}