package com.app.axzif.Favourite.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;


public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    String typr="";
    int i=0;
    int sumeamount;
    int sumeamount2;
    public int count = 0;
    public double num ;
    RelativeLayout main_rec;
    Activity activity;
    View noDataView;
    DecimalFormat twoDForm = new DecimalFormat("#.##");



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Description,qty,amount,finalamount;
        ImageView image;
        CardView idCardView;
         LinearLayout delete_card;
        ImageView less,increment;



        public MyViewHolder(View view) {
            super(view);

            Description =  view.findViewById(R.id.Description);
            finalamount =  view.findViewById(R.id.finalamount);
            amount =  view.findViewById(R.id.amount);
            image =  view.findViewById(R.id.image);
            qty =  view.findViewById(R.id.qty);
            idCardView =  view.findViewById(R.id.idCardView);
            less =  view.findViewById(R.id.less);
            increment =  view.findViewById(R.id.increment);
            delete_card =  view.findViewById(R.id.delete_card);

        }
    }

    public CardListAdapter(ArrayList<Category> transactionsList, Context mContext, RelativeLayout main_rec, Activity activity,View noDataView) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.main_rec = main_rec;
        this.activity = activity;
        this.noDataView = noDataView;
    }

    @Override
    public CardListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_cart_adapter, parent, false);

        return new CardListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final CardListAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.Description.setText("" + operator.getProductName());
        UtilMethods.INSTANCE.GetProductImg(mContext,operator.getId(),null,holder.image);
        holder.qty.setText(""+operator.getQuantity());


        try{
            num = operator.getProductPrice();
        } catch(NumberFormatException ex){ // handle your exception

        }


        double productprice = num* Integer.parseInt(operator.getQuantity())    ;
        holder.finalamount.setText("" + productprice);
        int discount = Integer.parseInt(operator.getDiscount());
        double co=  0.01;
        double div=discount * co;
        double muldiv=productprice * div ;

       double finalAmo=productprice-muldiv;
       double finalAmoToatal= Double.valueOf(twoDForm.format(finalAmo));
        holder.amount.setText(""+finalAmoToatal);

        Log.e("finalAmofinalAmo","productprice  :  "+ productprice +"+  discount "+ discount + "  div "+  div +"  muldiv  "+  muldiv +"" +
                "    ,   finalAmo   "+ finalAmo);

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

        holder.delete_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;

                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.DeleteCartProduct(mContext, ""+operator.getId(),  loader,main_rec,activity,noDataView);

                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }




            }
        });


    holder.less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(!holder.qty.getText().toString().trim().equalsIgnoreCase("1")){

                    count = Integer.parseInt((String)holder.qty.getText());
                    count--;
                    holder.qty.setText("" + count);

                    Loader loader;


                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                        loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);


                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

                        UtilMethods.INSTANCE.UpdateCartQuantity(mContext, ""+operator.getId(),
                                ""+holder.qty.getText().toString().trim(), loader,main_rec,activity,noDataView);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                mContext.getResources().getString(R.string.network_error_message));
                    }







                }else {
                    Toast.makeText(mContext, "Check Number of Quantity", Toast.LENGTH_SHORT).show();
                }





            }
        });


        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                    count = Integer.parseInt((String)holder.qty.getText());
                    count++;
                    holder.qty.setText("" + count);


                    Loader loader;


                    if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                        loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);


                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

                        UtilMethods.INSTANCE.UpdateCartQuantity(mContext, ""+operator.getId(),
                                ""+holder.qty.getText().toString().trim(), loader,main_rec,activity,noDataView);

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