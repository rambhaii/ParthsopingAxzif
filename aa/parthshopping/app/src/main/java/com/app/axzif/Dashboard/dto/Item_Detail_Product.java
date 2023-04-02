package com.app.axzif.Dashboard.dto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.axzif.Category.dto.ReviewData;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.jsibbold.zoomage.ZoomageView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.List;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Category.dto.subProduct;
import com.app.axzif.Category.ui.SubProductAdatpter;
import com.app.axzif.Dashboard.TabViewFragment.AditionalInfo;
import com.app.axzif.Dashboard.TabViewFragment.Description;
import com.app.axzif.Dashboard.TabViewFragment.Review;
import com.app.axzif.Login.dto.LoginResponse;
import com.app.axzif.Login.ui.LoginActivity;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class Item_Detail_Product extends AppCompatActivity implements View.OnClickListener {


    ZoomageView imageproduct;
    WebView descriptionWebView;
    TextView tv_title,amount,longdescription,sort_description,warranty,ReturnPolicy,discount,product_description,Size_pro,seller_name,Color_pro,CashonDeliveryavailable;
     String stock="";
    ProductResponse productResponse = new ProductResponse();
    NewProductAdapter newAdapter;
    RecyclerView subProductRec,variationProductRec;
    TextView add_to_cart,buynow;
    String respose="";
    String operator="";
    Loader loader;
    String productid="";
    LinearLayoutManager mLayoutManager;
    ArrayList<ProductDatum> productDatumArrayList = new ArrayList<>();

    ImageView less,increment;
    TextView lunchdeta,similartv;
     public int count = 0;

    ViewPager mViewPager;
    LinearLayout dotsCount;
    Handler handler;
    double finalamouny;
    double finalAmo;
    TextView apifinalamount,rating,reviewcount;
    RatingBar ratingBar;
    SubProductAdatpter subProductAdatpter;
    ArrayList<subProduct> subResponse;
    SliderView sliderView;
    private TabLayout tabLayout;
    private AditionalInfo aditionalInfo;
    private Description description;
    private Review review;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        Size_pro=findViewById(R.id.Size);
        Color_pro=findViewById(R.id.Color);
        seller_name=findViewById(R.id.seller_name);
        reviewcount=findViewById(R.id.reviewcount);
        sliderView=findViewById(R.id.imageSlider);
        tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager1);
        aditionalInfo = new AditionalInfo();
        description = new Description();
        review = new Review();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0);
        viewPagerAdapter.addFragment(description,"Description");
        viewPagerAdapter.addFragment(aditionalInfo,"Additional");
        viewPagerAdapter.addFragment(review,"Review");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        UtilMethods.INSTANCE.similarproduct(Item_Detail_Product.this,getIntent().getStringExtra("operator"),null);

         Getdeta();



    }

    private void Getdeta()
    {

        ratingBar=findViewById(R.id.ratingBar);

        handler=new Handler();
        dotsCount   = findViewById(R.id.image_count);
        mViewPager =  findViewById(R.id.pager);
        apifinalamount =  findViewById(R.id.apifinalamount);
        rating =  findViewById(R.id.rating);

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        imageproduct=findViewById(R.id.imageproduct);
        tv_title=findViewById(R.id.tv_title);
        amount=findViewById(R.id.amount);
        longdescription=findViewById(R.id.longdescription);
        similartv=findViewById(R.id.similartv);
       // sort_description=findViewById(R.id.sort_description);
        warranty=findViewById(R.id.warranty);
        ReturnPolicy=findViewById(R.id.ReturnPolicy);
        subProductRec=findViewById(R.id.subProductRec);
         CashonDeliveryavailable=findViewById(R.id.CashonDeliveryavailable);
        variationProductRec=findViewById(R.id.variationProductRec);


        discount=findViewById(R.id.discount);
        product_description=findViewById(R.id.product_description);
        descriptionWebView=findViewById(R.id.descriptionWebView);

        WebSettings webSettings = descriptionWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultFontSize(14);
        descriptionWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        descriptionWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        descriptionWebView.setLongClickable(false);
        descriptionWebView.setHapticFeedbackEnabled(false);



        respose = getIntent().getExtras().getString("respose");
        operator = getIntent().getExtras().getString("operator");
        Log.d("dfgn",respose);
        stock=getIntent().getStringExtra("stock");

        //   Toast.makeText(this, ""+stock, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Product Details");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        increment=findViewById(R.id.increment);
        less=findViewById(R.id.less);
        lunchdeta=findViewById(R.id.lunchdeta);

        increment.setOnClickListener(this);
        less.setOnClickListener(this);

        add_to_cart = findViewById(R.id.add_to_cart);
        buynow = findViewById(R.id.buynow);

         if (stock.equals("0"))
         {
         buynow.setText("Out Of Stock");
         buynow.setBackgroundColor(getResources().getColor(R.color.red));
        }
        else {
             buynow.setOnClickListener(this);
             add_to_cart.setOnClickListener(this);
         }
        dataParse(respose);
      dataParseoperator(operator);
        dataParseVariation(respose);
    }

    LoginResponse transactions = new LoginResponse();
    Category transactionsoption = new Category();


    public void dataParseoperator(String respose)
    {
        UtilMethods.INSTANCE.GetProductImg(this,respose,null,imageproduct);
    }


    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("ProductImg")){
            GalleryList(fragmentActivityMessage.getMessage());
        }
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("similarProduct")){
            Log.d( "sdbvjkdsbvksbd: ",fragmentActivityMessage.getMessage());
            dataParseSub(fragmentActivityMessage.getMessage());
        }
    }

    CategoryRespose sliderImage = new CategoryRespose();
    ArrayList<Category> sliderLists = new ArrayList<>() ;
    Integer mDotsCount;
    private void GalleryList(String response)
    {

        Log.d("dhfghghj",response);
        sliderImage = new Gson().fromJson(response, CategoryRespose.class);
        sliderLists = sliderImage.getData();
        FullScreenImageAdapter adapter = new FullScreenImageAdapter(Item_Detail_Product.this,sliderLists);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();
    }

    private void postDelayedScrollNext() {
        handler.postDelayed(new Runnable() {
            public void run() {

                try {
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                        mViewPager.setCurrentItem(0);
                        postDelayedScrollNext();
                        return;
                    }
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    // postDelayedScrollNext(position+1);
                    postDelayedScrollNext();
                }catch (Exception e){

                }

                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);

            }
        }, 5000);

    }




    double num;

    @SuppressLint("ResourceAsColor")
    public void dataParse(String respose)
    {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, LoginResponse.class);

       productid= transactions.getData().getId();

        tv_title.setText(""+ transactions.getData().getProductName() );

        apifinalamount.setText("\u20B9 "+ transactions.getData().getProductPrice() );
        apifinalamount.setPaintFlags(apifinalamount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        num = transactions.getData().getProductPrice();

        double productprice =  lunchdeta.getText()!=null?(num * Integer.parseInt(lunchdeta.getText().toString().trim())):0.0 ;
        int discountapi = transactions.getData().getDiscount()!=null?(Integer.parseInt(transactions.getData().getDiscount())):0;
        double co=  0.01;
        double div=discountapi * co;
        double muldiv=productprice * div ;
        finalAmo=productprice-muldiv;
        finalamouny=finalAmo+finalamouny;
        amount.setText("\u20B9  "+finalamouny );


       //UtilMethods.INSTANCE.CountProductReview(this,productid,null,ratingBar);

        double  rat= Double.parseDouble(transactions.getData().getRetingAvg());
        ratingBar.setMax(5);
        ratingBar.setRating(Float.parseFloat(String.valueOf(rat)));
        Log.d("jhfddfgry",""+rat);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#e6b044"), PorterDuff.Mode.SRC_ATOP);


        int i=0;
        for (ReviewData d:transactions.getReviewData())
        {
           i++;
        }
        reviewcount.setText("("+i+")");


        descriptionWebView.loadData(transactions.getData().getHighlights(), "text/html", "UTF-8");
        String roductDescription = transactions.getData().getHighlights()!=null?transactions.getData().getHighlights().toString().trim():"";      // used by WebView
        Spanned pproductDescription = Html.fromHtml(roductDescription);

        String htmlAsString =transactions.getData().getProductDescription()!=null? transactions.getData().getProductDescription().toString().trim():"";      // used by WebView
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString);

        Bundle bundle=new Bundle();
        bundle.putString("descrption",transactions.getData().getProductDescription()!=null?transactions.getData().getProductDescription().toString().trim():"");
        description.setArguments(bundle);

        Bundle bundle2=new Bundle();
        bundle2.putString("additional",transactions.getData().getAdditionalInfo()!=null?transactions.getData().getAdditionalInfo().toString().trim():"");
        aditionalInfo.setArguments(bundle2);
        Log.d("skjfdjkfvnvdv",transactions.getData().getAdditionalInfo());
        Bundle bundle3=new Bundle();
        //bundle3.putString("review",transactions.getData().getAdditionalInfo()!=null?transactions.getData().getAdditionalInfo().toString().trim():"");
        bundle3.putString("review",transactions!=null? new Gson().toJson(transactions):"");
        review.setArguments(bundle3);

        //
       // longdescription.setText(""+ htmlAsSpanned );
       // Toast.makeText(this, ""+transactions.getData().getId(), Toast.LENGTH_SHORT).show();
       // sort_description.setText(""+ transactions.getData().getSortDescription() );
    //    product_description.setText(""+ pproductDescription );

        warranty.setText(""+ transactions.getData().getWarranty());
        ReturnPolicy.setText( " "+transactions.getData().getReturnPolicy());

        if(transactions.getData().getCod()!=null ) {
            if (!transactions.getData().getCod().equalsIgnoreCase("1")) {
                CashonDeliveryavailable.setText("Cash on Delivery not available");
            }
        }
        discount.setText(""+ transactions.getData().getDiscount()+" % OFF  " );
        Size_pro.setText(""+ transactions.getData().getUnitFullName() +" "+ transactions.getData().getUnitSortName());
        Color_pro.setText(""+ transactions.getData().getColorName());
        seller_name.setText(""+ transactions.getData().getCompany_name());



    }
    public void dataParseVariation(String respose) {
        Gson gson = new Gson();
        transactions = gson.fromJson(respose, LoginResponse.class);
            subResponse = transactions.getData().getSubProducts();
            if (subResponse.size() > 0)
            {
                subProductAdatpter = new SubProductAdatpter(subResponse, Item_Detail_Product.this);
                mLayoutManager = new LinearLayoutManager(Item_Detail_Product.this);
                variationProductRec.setLayoutManager(new LinearLayoutManager(Item_Detail_Product.this, LinearLayoutManager.HORIZONTAL, false));
                variationProductRec.setItemAnimator(new DefaultItemAnimator());
                variationProductRec.setAdapter(subProductAdatpter);
                variationProductRec.setVisibility(View.VISIBLE);
                variationProductRec.setVisibility(View.VISIBLE);

            } else {
                variationProductRec.setVisibility(View.GONE);
               // variationProductRec.setVisibility(View.GONE);
            }
    }
    public void dataParseSub(String respose) {
        Gson gson = new Gson();
        productResponse = gson.fromJson(respose, ProductResponse.class);
        productDatumArrayList = productResponse.getData();

        if (productDatumArrayList.size() > 0) {
            newAdapter = new NewProductAdapter(productDatumArrayList,this,"main");
            StaggeredGridLayoutManager layoutManage=new StaggeredGridLayoutManager(2,LinearLayout.VERTICAL);
            subProductRec.setLayoutManager(layoutManage);
            subProductRec.setItemAnimator(new DefaultItemAnimator());
            subProductRec.setAdapter(newAdapter);
            newAdapter.notifyDataSetChanged();

        } else {
            subProductRec.setVisibility(View.GONE);
            similartv.setVisibility(View.GONE);
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
    public void onClick(View view) {

        if(view==increment){

            count = Integer.parseInt((String)lunchdeta.getText());
            count++;

            lunchdeta.setText("" + count);

            dataParse(respose);

        }

        if(view==less){

            if(!lunchdeta.getText().toString().trim().equalsIgnoreCase("1")){

                count = Integer.parseInt((String)lunchdeta.getText());
                count--;
                lunchdeta.setText("" + count);

            }else {
                Toast.makeText(this, "Check Number of Quantity", Toast.LENGTH_SHORT).show();
            }

            dataParse(respose);

        }

        if(view==add_to_cart){


            String Email="";

            SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
            Email = ""+balanceResponse;
            if ( Email.equalsIgnoreCase("1")){


                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.addcart(this, productid,
                            lunchdeta.getText().toString().trim(), loader,1);

                } else {
                    UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                            getResources().getString(R.string.network_error_message));
                }

            }else{

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

            }

        }

    if(view==buynow) {


            String Email = "";

            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
            Email = "" + balanceResponse;


            if (Email.equalsIgnoreCase("1")) {

                UtilMethods.INSTANCE.addcart(this, productid,
                        lunchdeta.getText().toString().trim(), loader,2);



                // startActivity(new Intent(this, CartActivity.class));


            } else {

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

            }


//             startActivity(new Intent(this, CartActivity.class));
//
//              finish();


        }


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitles = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }
        //add fragment to the viewpager
        public void addFragment(Fragment fragment, String title)
        {
            fragments.add(fragment);
            fragmentTitles.add(title);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
        @Override
        public int getCount()
        {
            return fragments.size();
        }
        //to setup title of the tab layout
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitles.get(position);
        }
    }

}
