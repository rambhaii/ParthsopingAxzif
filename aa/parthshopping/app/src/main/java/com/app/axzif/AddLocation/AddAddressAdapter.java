package com.app.axzif.AddLocation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.axzif.Address.ui.BillingDetailsEditActivity;
import com.app.axzif.Address.ui.OrderAllViewActivity;
import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;
import com.google.gson.Gson;

import java.util.ArrayList;


public class AddAddressAdapter extends RecyclerView.Adapter<AddAddressAdapter.MyViewHolder> {

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

    public AddAddressAdapter(ArrayList<Category> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
     }

    @Override
    public AddAddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_order_list_adapter, parent, false);

        return new AddAddressAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final AddAddressAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.phone.setText(" +91-  "+operator.getMobile());
        holder.addressType.setText(" "+operator.getAddressType()+" ");
        holder.name.setText(" "+operator.getName()+"   ");
        holder.address.setText(""+operator.getLandmark()+" , "  +""+operator.getAddress()+" , "  +""+operator.getCity()+" , "  +""+operator.getState()+" , "+operator.getZip() );


        holder.rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                row_index = position;
                notifyDataSetChanged();
                ((AddAddressActivity) mContext).ItemClick(operator.getId() ,operator);

            }
        });

    holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, BillingDetailsEditActivity.class);
                i.putExtra("operator",""+new Gson().toJson(operator).toString());
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


        if(row_index==position){
            holder.rating.setChecked(true);
        }else {
            holder.rating.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}