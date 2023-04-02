package com.app.axzif.Order.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.UtilMethods;


public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    double num;
    double finalAmo;

    RelativeLayout main_rec;
    Activity activity;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,product_status;
        ImageView image_product;
        RelativeLayout order_detail;



        public MyViewHolder(View view) {
            super(view);

             product_name =  view.findViewById(R.id.product_name);
            product_status =  view.findViewById(R.id.product_status);
            image_product =  view.findViewById(R.id.image_product);
            order_detail =  view.findViewById(R.id.order_detail);


        }
    }

    public OrderListAdapter(ArrayList<Category> transactionsList, Context mContext, RelativeLayout main_rec, Activity activity) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.main_rec = main_rec;
        this.activity = activity;

    }

    double finalamouny;



    @Override
    public OrderListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_adapter, parent, false);

        return new OrderListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final OrderListAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.product_name.setText("" + operator.getProductName());

        if(operator.getOrder_status().equalsIgnoreCase("0")){

            holder.product_status.setText("canceled ");

        }else  if(operator.getOrder_status().equalsIgnoreCase("1")){

            holder.product_status.setText("Ordered ");


        }else  if(operator.getOrder_status().equalsIgnoreCase("2")){
            holder.product_status.setText("Packed ");


        }else  if(operator.getOrder_status().equalsIgnoreCase("3")){

            holder.product_status.setText("On the way ");



        }else   if(operator.getOrder_status().equalsIgnoreCase("4")){

            holder.product_status.setText("Delivered ");



        }


        holder.order_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i= new Intent(mContext, OrderDetailActivity.class);
                i.putExtra("operator",""+new Gson().toJson(operator).toString());
                mContext.startActivity(i);

            }
        });


        UtilMethods.INSTANCE.GetProductImg(mContext,operator.getId(),null,holder.image_product);



    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}