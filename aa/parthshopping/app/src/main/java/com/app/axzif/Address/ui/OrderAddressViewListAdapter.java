package com.app.axzif.Address.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.axzif.Utils.ApplicationConstant;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;


public class OrderAddressViewListAdapter extends RecyclerView.Adapter<OrderAddressViewListAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;

    private int row_index=0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,phone ,addressType,Edit;
        RadioButton rating;
        LinearLayout remove_address;

        public MyViewHolder(View view) {
            super(view);

            address =  view.findViewById(R.id.address);
            name =  view.findViewById(R.id.name);
            phone =  view.findViewById(R.id.phone);
            addressType =  view.findViewById(R.id.addressType);
            rating =  view.findViewById(R.id.rating);
            remove_address =  view.findViewById(R.id.remove_address);
            Edit =  view.findViewById(R.id.Edit);


        }
    }

    public OrderAddressViewListAdapter(ArrayList<Category> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
     }

    @Override
    public OrderAddressViewListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_order_list_adapter, parent, false);

        return new OrderAddressViewListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final OrderAddressViewListAdapter.MyViewHolder holder, final int position) {
         Category operator = transactionsList.get(position);
        Log.d("jdkghjdgh",operator.getMobile());
        SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, mContext.MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.address_id, null);
          Log.d("djbfhjdfhhjdfjbf",response);

        holder.phone.setText(" +91-  "+operator.getMobile());
        holder.addressType.setText(" "+operator.getAddressType()+" ");
        holder.name.setText(" "+operator.getName()+"   ");
        holder.address.setText(""+operator.getLandmark()+" , "  +""+operator.getAddress()+" , "  +""+operator.getCity()+" , "  +""+operator.getState()+" , "+operator.getZip() );



        holder.rating.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                row_index = position;
                notifyDataSetChanged();
                ((OrderAllViewActivity) mContext).ItemClick(operator.getId() ,operator);

            }
        });

    holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, BillingDetailsEditActivity.class);
                i.putExtra("operator",""+new Gson().toJson(operator).toString());
                i.putExtra("status","1");
                mContext.startActivity(i);
            }
        });




         holder.remove_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;
                loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);

                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    UtilMethods.INSTANCE.DeleteUserAddress(mContext,operator.getId(),loader);

                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }

            }
        });


        if(row_index==position)
        {

            if (response.equalsIgnoreCase(""+operator.getId()))
            {
                holder.rating.setChecked(true);
            }

        }else
        {
            if (response.equalsIgnoreCase(""+operator.getId()))
            {
                holder.rating.setChecked(true);
            }
            else
            {
                holder.rating.setChecked(false);
            }


        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}