package com.app.axzif.Dashboard.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import com.app.axzif.Dashboard.dto.NewProductAdapter;
import com.app.axzif.Dashboard.dto.ProductDatum;
import com.app.axzif.Dashboard.dto.ProductResponse;
import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.UtilMethods;

public class ViewAllOfferTypeProduct extends AppCompatActivity {
    ProductResponse productResponse = new ProductResponse();
    NewProductAdapter newAdapter;
    SliderView sliderView;
    String title="";
    String categoryType="";

    ArrayList<ProductDatum> productDatumArrayList = new ArrayList<>();

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_offer_type_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
         title=getIntent().getStringExtra("title");
         categoryType=getIntent().getStringExtra("category_type");
            toolbar.setTitle(title);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            recyclerView=findViewById(R.id.recyclerView);
            UtilMethods.INSTANCE.offerTypeProduct(ViewAllOfferTypeProduct.this, null,categoryType,0,100);

    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {

        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("feature_product")){
            dataParse(""+ fragmentActivityMessage.getMessage());

        }
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("today_deals")){
            dataParse(""+ fragmentActivityMessage.getMessage());

        }
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("fashion_zone")){
            dataParse(""+ fragmentActivityMessage.getMessage());

        }
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("fastival_special")){
            dataParse(""+ fragmentActivityMessage.getMessage());

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
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }
    public void dataParse(String respose) {
        Gson gson = new Gson();
        productResponse = gson.fromJson(respose, ProductResponse.class);
        productDatumArrayList = productResponse.getData();

        if (productDatumArrayList.size() > 0) {
            newAdapter = new NewProductAdapter(productDatumArrayList,this,"main");
            final StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(staggeredGridLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(newAdapter);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }
}