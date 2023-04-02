package com.app.axzif.Category.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.CategoryRespose;
import com.app.axzif.Category.dto.subProduct;
import com.app.axzif.Login.ui.LoginActivity;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;
import soup.neumorphism.NeumorphCardView;

import static android.content.Context.MODE_PRIVATE;

public class SubProductAdatpter extends RecyclerView.Adapter<SubProductAdatpter.MyViewHolder> {

    private ArrayList<subProduct> transactionsList;
    private Context mContext;
    int i=0;
    double num;
    double finalAmo;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text,textlong,amount,finalamout_product,dicsunte,rating,Temporarily,otofstock;
        ImageView image,do_wish_image;
        NeumorphCardView idCardView;
        ImageView cart_item;
        LinearLayout do_wish;
        RatingBar ratingBar;

        public MyViewHolder(View view) {
            super(view);

            text =  view.findViewById(R.id.text);
            textlong =  view.findViewById(R.id.textlong);
            amount =  view.findViewById(R.id.amount);
            finalamout_product =  view.findViewById(R.id.finalamout_product);
            image =  view.findViewById(R.id.image);
            do_wish_image =  view.findViewById(R.id.do_wish_image);
            idCardView =  view.findViewById(R.id.idCardView);
            cart_item =  view.findViewById(R.id.cart_item);
            dicsunte =  view.findViewById(R.id.dicsunte);
            rating =  view.findViewById(R.id.rating);
            do_wish =  view.findViewById(R.id.do_wish);
            Temporarily =  view.findViewById(R.id.Temporarily);
            ratingBar =  view.findViewById(R.id.ratingBar);
            otofstock=view.findViewById(R.id.outofstock);
        }
    }
    public SubProductAdatpter(ArrayList<subProduct> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public SubProductAdatpter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.variation_view, parent, false);

        return new SubProductAdatpter.MyViewHolder(itemView);

    }

    ArrayList<Category> transactionsObjects = new ArrayList<>();
    CategoryRespose transactions = new CategoryRespose();


    @Override
    public void onBindViewHolder(final SubProductAdatpter.MyViewHolder holder, final int position) {
        final subProduct operator = transactionsList.get(position);

        if(!operator.getMainStock().equalsIgnoreCase("0")){
            holder.Temporarily.setVisibility(View.GONE);
        }
        if(operator.getAvlStock().equals("0"))
        {
            holder.otofstock.setVisibility(View.VISIBLE);
        }

        holder.textlong.setText("" + operator.getProductName());
        holder.text.setText("" + operator.getProductName());
        holder.amount.setText("\u20B9 " + operator.getProductPrice());
        holder.dicsunte.setText("" + operator.getDiscount()+" %off ");
        holder.amount.setPaintFlags(holder.amount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        num =Double.parseDouble(operator.getProductPrice());
        double productprice =  num     ;
        int discount = Integer.parseInt(operator.getDiscount());
        double co=  0.01;
        double div=discount * co;
        double muldiv=productprice * div ;
        finalAmo=productprice-muldiv;
        holder.finalamout_product.setText("\u20B9 " + finalAmo);
        UtilMethods.INSTANCE.GetProductImg(mContext,operator.getId(),null,holder.image);
        UtilMethods.INSTANCE.CountProductReview(mContext,operator.getId(),null,holder.ratingBar);

        SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String respo = myPreferences.getString(ApplicationConstant.INSTANCE.setWishList, "");
        Log.e("wishidwishidwishid","  wishid  :  "+ "wishid"  +"   id  :   "+  operator.getId() +"   mm     "+respo );

        if(!respo.equalsIgnoreCase(""))
        {
            Gson gson = new Gson();
            transactions = gson.fromJson(respo, CategoryRespose.class);
            transactionsObjects = transactions.getData();
            if (transactionsObjects.size() > 0) {

                for(int i=0;i<transactionsObjects.size();i++) {

                    String  wishid = transactionsObjects.get(i).getId();

                    Log.e("wishidwdwishid","  wishid  :  "+ wishid  +"   id  :   "+  operator.getId() +"   mm     "+respo );

                    if(wishid.equalsIgnoreCase(operator.getId())){

                        holder.do_wish_image.setColorFilter(mContext.getResources().getColor(R.color.red));
                        holder.do_wish.setClickable(false);

                    }else {

                        //  holder.do_wish_image.setColorFilter(mContext.getResources().getColor(R.color.white));

                    }

                }

            }
        }





















        holder.do_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email="";

                SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
                Email = ""+balanceResponse;

                if ( Email.equalsIgnoreCase("1")){

                    Loader loader = null;
                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);

                    holder.do_wish_image.setColorFilter(mContext.getResources().getColor(R.color.red));

                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

//                        loader.show();
//                        loader.setCancelable(false);
//                        loader.setCanceledOnTouchOutside(false);

                        UtilMethods.INSTANCE.AddToWishlist(mContext, operator.getId(),   loader,holder.do_wish_image);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                mContext.getResources().getString(R.string.network_error_message));
                    }

                }else{

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                }

            }
        });

        holder.cart_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Email="";

                SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
                Email = ""+balanceResponse;

                // Log.e("Email","  Email"+   Email +"    "+  Email.length() );

                if ( Email.equalsIgnoreCase("1")){

                    Loader loader = null;
                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);



                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

                        UtilMethods.INSTANCE.addcart(mContext, operator.getId(),
                                "1", loader,1);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                mContext.getResources().getString(R.string.network_error_message));
                    }


                }else{

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                }





            }
        });


        /*  if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.addcart(this, productid,
                        lunchdeta.getText().toString().trim(), loader);

            } else {
                UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }*/


        holder.idCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader = null;
                loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);


                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

              UtilMethods.INSTANCE.singleproduct(mContext, operator.getId(), loader,null,operator.getAvlStock());

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