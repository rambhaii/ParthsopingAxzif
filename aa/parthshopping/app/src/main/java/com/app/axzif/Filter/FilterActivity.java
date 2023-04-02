package com.app.axzif.Filter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.axzif.Dashboard.ui.ProductListActivity;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView Brand,color;
    RecyclerView filterCategorySubListRecyclerView,brandRecView;
     String Respose;
    String Resposegetcolor;
    String BrandId;
    String ColourId;
    TextView text_action_apply,text_action_clear,selectAll;
    Loader loader;
    String subCategoryId="",category_id="";
    private ProgressBar progressBar;
    Category data;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> colorList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Getid();
    }

    private void Getid()
    {
        progressBar =  findViewById(R.id.progressBar_cyclic);
        text_action_apply=findViewById(R.id.text_action_apply);
        selectAll=findViewById(R.id.selectAll);
        text_action_clear=findViewById(R.id.text_action_clear);
        text_action_apply.setOnClickListener(this);
        text_action_clear.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        filterCategorySubListRecyclerView=findViewById(R.id.filterCategorySubListRecyclerView);
        Brand=findViewById(R.id.Brand);
        brandRecView=findViewById(R.id.brandRecView);
        color=findViewById(R.id.color);
        subCategoryId=getIntent().getStringExtra("subCategoryId");
        category_id=getIntent().getStringExtra("category_id");
       // Toast.makeText(this, ""+subCategoryId, Toast.LENGTH_SHORT).show();
        Brand.setOnClickListener(this);
        color.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Filter");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        HitAPi();}

    private void HitAPi() {
        if (UtilMethods.INSTANCE.isNetworkAvialable( this))
        {

            Log.d("djgdfgh",category_id);
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.GetBrandByCtgSubctg( this,category_id,  loader);
            UtilMethods.INSTANCE.GetColor( this, category_id,null);
        }
        else
        {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }
    }

    @Override
    public void onClick(View view)
    {
        if(view==text_action_apply)
        {

            for (String id:list)
            {
                Log.d("dkfhgjdfh",id);

            }



            if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.GetProductListByBrandNdColor(FilterActivity.this, category_id,"",
                        list,colorList, loader,"","");

              // UtilMethods.INSTANCE.saveselectdata(FilterActivity.this,data);


            } else {
                UtilMethods.INSTANCE.NetworkError(this, this.getResources().getString(R.string.network_error_title),
                        this.getResources().getString(R.string.network_error_message));
            }

        }
        if (view==text_action_clear)
        {

            UtilMethods.INSTANCE.saveselectdata(FilterActivity.this,null);
            UtilMethods.INSTANCE.saveSelectedColor(FilterActivity.this,null);
            Timer t1 = new Timer(false);
            t1.schedule(new TimerTask()
            {
                @Override
                public void run()
                {
                    runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            progressBar.setVisibility(View.VISIBLE);
                            brandRecView.setVisibility(View.GONE);
                        }
                    });
                }
            }, 10000);
              progressBar.setVisibility(View.GONE);
              brandRecView.setVisibility(View.VISIBLE);
          /*  startActivity(new Intent(FilterActivity.this, FilterActivity.class)
                    .putExtra("subCategoryId",subCategoryId).putExtra("category_id",category_id));
*/
            Intent next = new Intent(FilterActivity.this, FilterActivity.class).putExtra("subCategoryId",subCategoryId).putExtra("category_id",category_id);

            startActivity(next);
            finish();

            finish();


            Toast.makeText(this, "Clear all", Toast.LENGTH_SHORT).show();
        }
        if(view==Brand)
        {
            Brand.setBackgroundColor(Color.parseColor("#ffffff"));
            color.setBackgroundColor(Color.parseColor("#F4F4F4"));
            brandRecView.setVisibility(View.VISIBLE);
            filterCategorySubListRecyclerView.setVisibility(View.GONE);
        }


        if(view==color)
        {

            brandRecView.setVisibility(View.GONE);
            filterCategorySubListRecyclerView.setVisibility(View.VISIBLE);
            color.setBackgroundColor(Color.parseColor("#ffffff"));
            Brand.setBackgroundColor(Color.parseColor("#F4F4F4"));

          }

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage)
    {
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("getBrandByCtg"))
        {
            dataparse(""+fragmentActivityMessage.getMessage());
        }

        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("getcolor")){

            dataparseGetColour(fragmentActivityMessage.getMessage());
        }





    }

    @Override
    public void onStart()
    {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    BrandListAdapter mAdapter;
    ColourListAdapter mAdapterColour;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    public void dataparse(String respose)
    {
        Log.d("djfhdfjgh",respose);

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {
            mAdapter = new BrandListAdapter(transactionsObjects,this);
            mLayoutManager = new LinearLayoutManager(this);
            brandRecView.setLayoutManager(new GridLayoutManager(this,1));
            brandRecView.setItemAnimator(new DefaultItemAnimator());
            brandRecView.setAdapter(mAdapter);

        } else {

        }
    }


    public void ItemClick(String id, Category data,String type)
    {
        data=data;

        if(type.equalsIgnoreCase("Brand"))
        {
            if (!data.isSelected)
            {
                Log.d("fhgjhg",id);
                list.add(id);
            }
            else
            {
                list.remove(id);
                Log.d("fhgjhg",id+"n b");
                }

        }
        else if(type.equalsIgnoreCase("Brand1"))
        {
            Log.d("fhgjhg",id);
            list.add(id);
        }
        else if (type.equalsIgnoreCase("colour"))
        {

            if (!data.isSelected)
            {
                colorList.add(id);
            } else
            {
                colorList.remove(id);
            }
        } else if (type.equalsIgnoreCase("colour1"))
        {
               Log.d("fdgfgh",id);
                colorList.add(id);
            }
        //   UtilMethods.INSTANCE.saveselectdata(FilterActivity.this,list);
    }
    public void dataparseGetColour(String respose) {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {
            mAdapterColour = new ColourListAdapter(transactionsObjects,this);
            mLayoutManager = new LinearLayoutManager(this);
            filterCategorySubListRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
            filterCategorySubListRecyclerView.setItemAnimator(new DefaultItemAnimator());
            filterCategorySubListRecyclerView.setAdapter(mAdapterColour);

        } else {
           // color.setVisibility(View.GONE);
        }


    }
}
