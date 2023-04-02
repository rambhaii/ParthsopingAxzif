package com.app.axzif.CardView.ui;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;


public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    double num;
    double finalAmo;

    RelativeLayout main_rec;
    Activity activity;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textlong,finalamout_product,amount,dicsunte,rating;
        ImageView image;
        LinearLayout remove_wish;
        CardView idCardView;
        RatingBar ratingBar;





        public MyViewHolder(View view) {
            super(view);

            textlong =  view.findViewById(R.id.textlong);
             image =  view.findViewById(R.id.image);
            finalamout_product =  view.findViewById(R.id.finalamout_product);
            amount =  view.findViewById(R.id.amount);
            dicsunte =  view.findViewById(R.id.dicsunte);
            rating =  view.findViewById(R.id.rating);
            remove_wish =  view.findViewById(R.id.remove_wish);
            idCardView =  view.findViewById(R.id.idCardView);
            ratingBar =  view.findViewById(R.id.ratingBar);


        }
    }

    public WishListAdapter(ArrayList<Category> transactionsList, Context mContext,RelativeLayout main_rec,Activity activity) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.main_rec = main_rec;
        this.activity = activity;

    }

    @Override
    public WishListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_wish_adapter, parent, false);

        return new WishListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final WishListAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.textlong.setText("" + operator.getProductName());
        UtilMethods.INSTANCE.GetProductImg(mContext,operator.getId(),null,holder.image);

        num =operator.getProductPrice();
        double productprice =  num   ;
        int discount = Integer.parseInt(operator.getDiscount());
        double co=  0.01;
        double div=discount * co;
        double muldiv=productprice * div ;
        finalAmo=productprice-muldiv;
        holder.finalamout_product.setText("" + finalAmo);
        holder.amount.setText("\u20B9 " + operator.getProductPrice());
        holder.dicsunte.setText("" + operator.getDiscount()+" %off");

       // UtilMethods.INSTANCE.CountProductReview(mContext,operator.getId(),null,holder.ratingBar);
        UtilMethods.INSTANCE.ProductRating(mContext,operator.getId(),holder.ratingBar);

        holder.remove_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;

                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                     UtilMethods.INSTANCE.DeleteWishList(mContext, ""+operator.getId(),  loader,main_rec,activity);

                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }

            }
        });


        holder.idCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader = null;
                loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);


                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.singleproduct(mContext, operator.getId(), loader,operator,operator.getAvlStock());

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