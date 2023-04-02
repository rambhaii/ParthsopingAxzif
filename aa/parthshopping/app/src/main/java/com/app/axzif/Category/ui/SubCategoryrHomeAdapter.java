package com.app.axzif.Category.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;


public class SubCategoryrHomeAdapter extends RecyclerView.Adapter<SubCategoryrHomeAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    String typr="";
    int i=0;

    boolean isClicked=true;

    public class MyViewHolder extends RecyclerView.ViewHolder {
         public  TextView name;
         LinearLayout li_main;
         ImageView arrow;
        private RecyclerView recyclerView;

        public MyViewHolder(View view) {
            super(view);

            name =  view.findViewById(R.id.name);
             li_main =  view.findViewById(R.id.li_main);
            recyclerView =  view.findViewById(R.id.rec_subItem);
            arrow =  view.findViewById(R.id.arrow);

        }
    }

    public SubCategoryrHomeAdapter(ArrayList<Category> transactionsList, Context mContext, String typr) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.typr = typr;
    }

    @Override
    public SubCategoryrHomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_home_item, parent, false);

        return new SubCategoryrHomeAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final SubCategoryrHomeAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);
        holder.name.setText("" + operator.getSubcategory_name());
        boolean isExpendable=operator.isSelected();
        holder.recyclerView.setVisibility(isExpendable ? View.GONE :View.VISIBLE);
        if (isExpendable)
        {
            holder.arrow.setImageResource(R.drawable.ic_baseline_arrow_forward_24);
        }
        else
        {
            holder.arrow.setImageResource(R.drawable.arrow_downward_24);
        }

        holder.li_main.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                operator.setSelected(!operator.isSelected());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
        SubTypeAdapter subTypeAdapter=new SubTypeAdapter(operator.getSubTypeData(),mContext);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setAdapter(subTypeAdapter);

//        holder.li_main.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Loader loader;
//                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
//                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);
//                    loader.show();
//                    loader.setCancelable(false);
//                    loader.setCanceledOnTouchOutside(false);
//                    UtilMethods.INSTANCE.GetSubSubcategoryHome(mContext,operator.getId(),operator.getCategory_id(),""+operator.getSubcategory_name(),loader);
//
//
//                } else {
//                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
//                            mContext.getResources().getString(R.string.network_error_message));
//                }}
//        });



    }



    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}