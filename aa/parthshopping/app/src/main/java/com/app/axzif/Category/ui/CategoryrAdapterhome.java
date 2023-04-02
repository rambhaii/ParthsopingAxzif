package com.app.axzif.Category.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.SubCategory;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

import soup.neumorphism.NeumorphCardView;


public class CategoryrAdapterhome extends RecyclerView.Adapter<CategoryrAdapterhome.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    String typr="";
    int i=0;

    SubCategoryrAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<SubCategory> transactionsObjects = new ArrayList<>();
    Category transactions = new Category();



    public class MyViewHolder extends RecyclerView.ViewHolder {
         public  TextView name;
         ImageView downlayout;
        NeumorphCardView main;
        RecyclerView recyclerView;
        ImageView imageview;


        public MyViewHolder(View view) {
            super(view);

            name =  view.findViewById(R.id.name);
            imageview =  view.findViewById(R.id.imageview);
            downlayout =  view.findViewById(R.id.downlayout);
           main =  view.findViewById(R.id.neowidget);
            recyclerView =  view.findViewById(R.id.recyclerView);

        }
    }

    public CategoryrAdapterhome(ArrayList<Category> transactionsList, Context mContext, String typr) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.typr = typr;
    }

    @Override
    public CategoryrAdapterhome.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_home, parent, false);

        return new CategoryrAdapterhome.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final CategoryrAdapterhome.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.name.setText("" + operator.getCategoryName());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noimage);
        requestOptions.error(R.drawable.rnd_logo);



        Glide.with(mContext)
                .load( operator.getIconImg())
                .apply(requestOptions)
                .into(holder.imageview);



        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;

                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                  //
                    //  UtilMethods.INSTANCE.GetSubcategoryHome(mContext,operator.getId(),""+operator.getCategoryName(),loader);

                    UtilMethods.INSTANCE.GetProductList(mContext, ""+operator.getId(),""+"",
                            ""+ "", loader);


                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }







            }
        });

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}