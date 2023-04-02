package com.app.axzif.Category.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;
import de.hdodenhof.circleimageview.CircleImageView;


public class SubSubCategoryrAdapter extends RecyclerView.Adapter<SubSubCategoryrAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    String typr="";
    int i=0;



    public class MyViewHolder extends RecyclerView.ViewHolder {
         public  TextView name;
         ImageView downlayout;
        LinearLayout main;
        RecyclerView recyclerView;
        CircleImageView imageview;

        public MyViewHolder(View view) {
            super(view);

            name =  view.findViewById(R.id.name);
            downlayout =  view.findViewById(R.id.downlayout);
            main =  view.findViewById(R.id.detaillayout);
            recyclerView =  view.findViewById(R.id.recyclerView);
            imageview =  view.findViewById(R.id.imageview);

        }
    }

    public SubSubCategoryrAdapter(ArrayList<Category> transactionsList, Context mContext, String typr) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.typr = typr;
    }

    @Override
    public SubSubCategoryrAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_item, parent, false);

        return new SubSubCategoryrAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final SubSubCategoryrAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.name.setText("" + operator.getSubcategory_type());

        holder.downlayout.setVisibility(View.INVISIBLE);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;


                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {


                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.GetProductList(mContext, ""+operator.getCategory_id(),""+operator.getSubcategory_id(),
                            ""+ operator.getId(), loader);

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