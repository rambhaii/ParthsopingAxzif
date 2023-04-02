package com.app.axzif.Category.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;
import de.hdodenhof.circleimageview.CircleImageView;


public class SubSubCategoryrHomeAdapter extends RecyclerView.Adapter<SubSubCategoryrHomeAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    String typr="";
    int i=0;




    public class MyViewHolder extends RecyclerView.ViewHolder {
         public  TextView name;
         LinearLayout main;
         CircleImageView imageview;

        public MyViewHolder(View view) {
            super(view);

            name =  view.findViewById(R.id.name);
             main =  view.findViewById(R.id.li_main);
             imageview =  view.findViewById(R.id.imageview);

        }
    }

    public SubSubCategoryrHomeAdapter(ArrayList<Category> transactionsList, Context mContext, String typr) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.typr = typr;
    }

    @Override
    public SubSubCategoryrHomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_home_item, parent, false);

        return new SubSubCategoryrHomeAdapter.MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final SubSubCategoryrHomeAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.name.setText("" + operator.getSubcategory_type());


        holder.main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;


                if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {


                    loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);

                   // Toast.makeText(mContext, operator.getId()+"", Toast.LENGTH_SHORT).show();
                    loader.show() ;
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    Toast.makeText(mContext, ""+operator.getCategory_id() +"    "+operator.getSubcategory_id()  , Toast.LENGTH_SHORT).show();
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