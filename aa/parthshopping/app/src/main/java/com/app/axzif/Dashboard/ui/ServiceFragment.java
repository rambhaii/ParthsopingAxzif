package com.app.axzif.Dashboard.ui;

 import static android.content.Context.MODE_PRIVATE;

 import android.app.Fragment;
 import android.content.Intent;
 import android.content.SharedPreferences;
 import android.os.Bundle;

 import androidx.core.widget.NestedScrollView;
 import androidx.recyclerview.widget.DefaultItemAnimator;
 import androidx.recyclerview.widget.LinearLayoutManager;
 import androidx.recyclerview.widget.RecyclerView;
 import androidx.recyclerview.widget.StaggeredGridLayoutManager;
 import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
 import androidx.viewpager.widget.ViewPager;
 import android.os.Handler;
 import android.util.Log;
 import android.view.LayoutInflater;
 import android.view.View;
 import android.view.ViewGroup;
 import android.widget.LinearLayout;
 import android.widget.ProgressBar;
 import android.widget.RelativeLayout;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.ethanhua.skeleton.Skeleton;
 import com.ethanhua.skeleton.SkeletonScreen;
 import com.google.gson.Gson;
 import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
 import com.smarteist.autoimageslider.SliderAnimations;
 import com.smarteist.autoimageslider.SliderView;

 import org.greenrobot.eventbus.EventBus;
 import org.greenrobot.eventbus.Subscribe;

 import java.util.ArrayList;

  import com.app.axzif.Category.dto.Category;
 import com.app.axzif.Category.dto.CategoryRespose;
 import com.app.axzif.Category.ui.CategoryrAdapterhome;
 import com.app.axzif.Category.ui.CustomPagerAdapter;
 import com.app.axzif.Dashboard.dto.NewProductAdapter;
 import com.app.axzif.Dashboard.dto.ProductDatum;
 import com.app.axzif.Dashboard.dto.ProductResponse;
 import com.app.axzif.Dashboard.dto.SliderAdapter;
 import com.app.axzif.Products.ui.ProductAdapter;
 import com.app.axzif.Products.ui.ProductAdapterFull;
 import com.app.axzif.R;
 import com.app.axzif.Search.ui.SearchActivity;
 import com.app.axzif.Utils.ApplicationConstant;
 import com.app.axzif.Utils.FragmentActivityMessage;
 import com.app.axzif.Utils.GlobalBus;
 import com.app.axzif.Utils.Loader;
 import com.app.axzif.Utils.UtilMethods;

public class ServiceFragment extends Fragment implements View.OnClickListener {

    private static final int PAGE_START = 1;

    private boolean isLoading = true;
    private boolean isLastPage = false;
    // limiting to 5 for this tutorial, since total pages in actual API is very large. Feel free to modify.
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;
    private  int start=0;
    private  int next=40;
    RecyclerView recyclerView,recyclerViewtodayDEALS,recyclerViewallProduct,recyclerFashionZone,recyclerFestivalSpecial,recycler_category,recycler_view_skelton;
    TextView categary,todayDeals,featureProcuct,fashionZone,festivalSpecial;
    RelativeLayout Search,rec_featureProduct,rec_ToadyDeal,rec_fashionZone,rec_festivalSpecial;
    NestedScrollView nestedScroll;
    ViewPager mViewPager;
    LinearLayout dotsCount;
    Handler handler;
    Loader loader;
    ProductAdapterFull allProductAdapter;
    CategoryRespose sliderImage = new CategoryRespose();
    ArrayList<Category> sliderLists = new ArrayList<>() ;
    CustomPagerAdapter mCustomPagerAdapter;
    ArrayList<ProductDatum> productDatumArrayList = new ArrayList<>();
    Integer mDotsCount;
    CategoryrAdapterhome categoryrAdapterhome;
    public static TextView mDotsText[];
    TextView newa,feature_view_all,todaydeal_view_all,fashzone_view_all,festival_view_all;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ProductResponse productResponse = new ProductResponse();
    NewProductAdapter newAdapter;
    SliderView sliderView;
    ProgressBar idPBLoading;
    SkeletonScreen skeletonScreen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home, container, false);
        getid(v);
        return v;
    }

    private void getid(View v) {

        loader = new Loader(getActivity(),android.R.style.Theme_Translucent_NoTitleBar);
        Search =  v.findViewById(R.id.Search);
        rec_fashionZone =  v.findViewById(R.id.rec_fashionZone);
        rec_ToadyDeal =  v.findViewById(R.id.rec_todayDeals);
        rec_featureProduct =  v.findViewById(R.id.rec_featureProduct);
        rec_festivalSpecial =  v.findViewById(R.id.rec_festivalSpecial);
        Search =  v.findViewById(R.id.Search);
        Search =  v.findViewById(R.id.Search);
        Search =  v.findViewById(R.id.Search);
        newa =  v.findViewById(R.id.newa);
        sliderView = v.findViewById(R.id.imageSlider);
        todayDeals = v.findViewById(R.id.todayDeals);
        featureProcuct = v.findViewById(R.id.featureProduct);
        festivalSpecial = v.findViewById(R.id.festivalSpecial);
        fashionZone = v.findViewById(R.id.fashionZone);
        idPBLoading = v.findViewById(R.id.idPBLoading);
        feature_view_all = v.findViewById(R.id.feature_view_all);
        feature_view_all.setOnClickListener(this);
        todaydeal_view_all = v.findViewById(R.id.todayDeals_view_all);
        todaydeal_view_all.setOnClickListener(this);
        fashzone_view_all = v.findViewById(R.id.fashionZone_view_all);
        fashzone_view_all.setOnClickListener(this);
        festival_view_all = v.findViewById(R.id.festivalSpecial_view_all);
        festival_view_all.setOnClickListener(this);
        nestedScroll =  v.findViewById(R.id.nestedScroll);
        recycler_category =  v.findViewById(R.id.recycler_category);
        recycler_view_skelton =  v.findViewById(R.id.recycler_view_skelton);

//            nestedScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
//                       if(isLoading) {
//                            isLoading=false;
//                            start += 20;
//                            next += 20;
//                            idPBLoading.setVisibility(View.VISIBLE);
//                            UtilMethods.INSTANCE.getAllProduct(getActivity(), start + "", next + "", null, idPBLoading);
//                        }
//                    }
//                }
//            });



        dataParsehome();




    handler=new Handler();
        dotsCount   = (LinearLayout)v.findViewById(R.id.image_count);
        Search.setOnClickListener(new View.OnClickListener()
        {
             @Override
             public void onClick(View view) {startActivity(new Intent(getActivity(), SearchActivity.class));}
         });
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerFashionZone = (RecyclerView) v.findViewById(R.id.recyclerFashionZone);
        recyclerViewtodayDEALS = (RecyclerView) v.findViewById(R.id.recyclerViewtodayDEALS);
        recyclerViewallProduct = (RecyclerView) v.findViewById(R.id.recyclerViewproduct);
        recyclerFestivalSpecial = (RecyclerView) v.findViewById(R.id.recyclerFestivalSpecial);

        HitApi();


    }


    private void HitApi() {

        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false);
            recycler_view_skelton.setLayoutManager(manager);
            newAdapter = new NewProductAdapter(productDatumArrayList, getActivity(),"main");
            recycler_view_skelton.setAdapter(newAdapter);

             skeletonScreen = Skeleton.bind(recycler_view_skelton)
                    .load(R.layout.skelton_product_list_view)
                    .color(R.color.back_bg)
                    .angle(10).count(1)
                    .show();


//            UtilMethods.INSTANCE.products(getActivity(), null);
            UtilMethods.INSTANCE.offerTypeProduct(getActivity(), null,"feature_product",0,20);
            UtilMethods.INSTANCE.offerTypeProduct(getActivity(), null,"today_deals",0,20);
            UtilMethods.INSTANCE.offerTypeProduct(getActivity(), null,"fashion_zone",0,20);
            UtilMethods.INSTANCE.offerTypeProduct(getActivity(), null,"fastival_special",0,20);
            UtilMethods.INSTANCE.getAllProduct(getActivity(), start+"",next+"",null,idPBLoading);



        } else {
            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }


    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage fragmentActivityMessage) {

        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("feature_product")){
            dataParse(""+ fragmentActivityMessage.getMessage());
            GalleryList();
        }
        if (fragmentActivityMessage.getFrom().equalsIgnoreCase("today_deals")){
            dataParseDEALS(""+ fragmentActivityMessage.getMessage());
        }
      if (fragmentActivityMessage.getFrom().equalsIgnoreCase("fashion_zone")){
            dataParseFashionZone(""+ fragmentActivityMessage.getMessage());
      }
      if (fragmentActivityMessage.getFrom().equalsIgnoreCase("fastival_special")){
          dataParseFestival(""+ fragmentActivityMessage.getMessage());
      }
      if (fragmentActivityMessage.getFrom().equalsIgnoreCase("allProduct")){
            dataParseallProduct(""+ fragmentActivityMessage.getMessage());
      }




    }

    private void dataParseFashionZone(String s) {

          //  Log.d( "dataParseFashionZone: ",s+"");
         Gson gson = new Gson();
            productResponse = gson.fromJson(s, ProductResponse.class);
            productDatumArrayList = productResponse.getData();

            if (productDatumArrayList.size() > 0)
            {
                Log.d("fdvjhn","fdvmnvmn");
                recyclerViewtodayDEALS.setVisibility(View.VISIBLE);
                rec_fashionZone.setVisibility(View.VISIBLE);
                newAdapter = new NewProductAdapter(productDatumArrayList, getActivity(),"main");
                recyclerFashionZone.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerFashionZone.setItemAnimator(new DefaultItemAnimator());
                recyclerFashionZone.setAdapter(newAdapter);

            } else
            {
                Log.d("fdvjhn","fdvmndsfvmn");
                recyclerViewtodayDEALS.setVisibility(View.GONE);
                rec_fashionZone.setVisibility(View.GONE);
            }


    }
    private void dataParseFestival(String s) {

            Log.d( "dataParseFashionZone: ",s+"");


            Gson gson = new Gson();
            productResponse = gson.fromJson(s, ProductResponse.class);
            productDatumArrayList = productResponse.getData();

            if (productDatumArrayList.size() > 0) {
                recyclerFestivalSpecial.setVisibility(View.VISIBLE);
                rec_festivalSpecial.setVisibility(View.VISIBLE);
                newAdapter = new NewProductAdapter(productDatumArrayList, getActivity(),"main");
                recyclerFestivalSpecial.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerFestivalSpecial.setItemAnimator(new DefaultItemAnimator());
                recyclerFestivalSpecial.setAdapter(newAdapter);

            } else {
                recyclerFestivalSpecial.setVisibility(View.GONE);
                rec_festivalSpecial.setVisibility(View.GONE);
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

    ProductAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();

    public void dataParse(String respose) {
        Gson gson = new Gson();
        productResponse = gson.fromJson(respose, ProductResponse.class);
        productDatumArrayList = productResponse.getData();

        if (productDatumArrayList.size() > 0)
        {
            recyclerViewtodayDEALS.setVisibility(View.VISIBLE);
            rec_featureProduct.setVisibility(View.VISIBLE);
            nestedScroll.setVisibility(View.VISIBLE);
            skeletonScreen.hide();
            newAdapter = new NewProductAdapter(productDatumArrayList, getActivity(),"main");
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(newAdapter);


        } else {
            recyclerViewtodayDEALS.setVisibility(View.GONE);
            rec_featureProduct.setVisibility(View.GONE);
        }


    }
    public void dataParseallProduct(String respose) {

        Gson gson = new Gson();
        transactions = gson.fromJson(respose, CategoryRespose.class);
        transactionsObjects.addAll(transactions.getData());

        if (transactionsObjects.size() > 0) {

            allProductAdapter = new ProductAdapterFull(transactionsObjects, getActivity(),"main");
            mLayoutManager = new LinearLayoutManager(getActivity());
            final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            recyclerViewallProduct.setLayoutManager(staggeredGridLayoutManager);
            recyclerViewallProduct.setItemAnimator(new DefaultItemAnimator());
            recyclerViewallProduct.setAdapter(allProductAdapter);
            recyclerViewallProduct.setVisibility(View.VISIBLE);
            recyclerViewallProduct.setNestedScrollingEnabled(false);
            recyclerViewallProduct.setScrollContainer(false);
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isLoading=true;
                }
            },6000);
        } else {
            isLoading=false;

        }

    }

    public void dataParseDEALS(String respose) {

        Gson gson = new Gson();
        productResponse = gson.fromJson(respose, ProductResponse.class);
        productDatumArrayList = productResponse.getData();

        if (productDatumArrayList.size() > 0) {
            recyclerViewtodayDEALS.setVisibility(View.VISIBLE);
            todayDeals.setVisibility(View.VISIBLE);
            rec_ToadyDeal.setVisibility(View.VISIBLE);
            newAdapter = new NewProductAdapter(productDatumArrayList, getActivity(),"main");
            recyclerViewtodayDEALS.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerViewtodayDEALS.setItemAnimator(new DefaultItemAnimator());
            recyclerViewtodayDEALS.setAdapter(newAdapter);

        } else {
            recyclerViewtodayDEALS.setVisibility(View.GONE);
            todayDeals.setVisibility(View.GONE);
        }


    }

    private void loadNextPage() {

        Toast.makeText(getActivity(), "currentPage    :  "+ String.valueOf(currentPage), Toast.LENGTH_SHORT).show();

    }

    private void GalleryList() {
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.sliderPref, null);
        sliderImage = new Gson().fromJson(response, CategoryRespose.class);
        sliderLists = sliderImage.getData();
        SliderAdapter adapter = new SliderAdapter(getActivity(),sliderLists);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();

    }



    @Override
    public void onResume() {

       // HitApi();

        super.onResume();

    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onClick(View view) {
        if(view==feature_view_all)
        {
            startActivity(new Intent(getActivity(),ViewAllOfferTypeProduct.class).putExtra("title","FEATURED PRODUCRTS").putExtra("category_type","feature_product"));
        }
        if(view==todaydeal_view_all)
        {
            startActivity(new Intent(getActivity(),ViewAllOfferTypeProduct.class).putExtra("title","TODAY'S DEALS").putExtra("category_type","today_deals"));
        }
        if(view==fashzone_view_all)
        {
            startActivity(new Intent(getActivity(),ViewAllOfferTypeProduct.class).putExtra("title","FASHION ZONE").putExtra("category_type","fashion_zone"));
        }
        if(view==festival_view_all)
        {
            startActivity(new Intent(getActivity(),ViewAllOfferTypeProduct.class).putExtra("title","FESTIVAL SPECIAL").putExtra("category_type","fastival_special"));
        }
    }
    public void dataParsehome()
    {
        CategoryRespose categoryRespose = new CategoryRespose();
        ArrayList<Category> categoryArrayList = new ArrayList<>();

        SharedPreferences myPreferences =  getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Categary, null);
        Log.d("bhcsahzbca",response);
        Gson gson = new Gson();
        categoryRespose = gson.fromJson(response, CategoryRespose.class);
        categoryArrayList = categoryRespose.getData();

        if (categoryArrayList.size() > 0)
        {

            categoryrAdapterhome = new CategoryrAdapterhome(categoryArrayList, getActivity(),"main");
            mLayoutManager = new LinearLayoutManager(getActivity());
            //  recycler_category.setLayoutManager(mLayoutManager);
            recycler_category.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recycler_category.setItemAnimator(new DefaultItemAnimator());
            recycler_category.setAdapter(categoryrAdapterhome);
            recycler_category.setVisibility(View.VISIBLE);

        } else {
            recycler_category.setVisibility(View.GONE);
        }
    }

}