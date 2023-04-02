package com.app.axzif.Category.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.UtilMethods;
import de.hdodenhof.circleimageview.CircleImageView;


public class SubCategoryrAdapter extends RecyclerView.Adapter<SubCategoryrAdapter.MyViewHolder> {

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

    public SubCategoryrAdapter(ArrayList<Category> transactionsList, Context mContext, String typr) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.typr = typr;
    }

    @Override
    public SubCategoryrAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_item, parent, false);
        return new SubCategoryrAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final SubCategoryrAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.name.setText("" + operator.getSubcategory_name());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noimage);
        requestOptions.error(R.drawable.active_dot);
        Glide.with(mContext)
                .load( operator.getIconImg())
                .apply(requestOptions)
                .into(holder.imageview);
        UtilMethods.INSTANCE.GetSubSubcategory(mContext,operator.getId(),operator.getCategory_id(),null,holder.recyclerView,holder.downlayout);
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    holder.downlayout.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    holder.main.setVisibility(View.VISIBLE);
                    i=1;


                }else if(i==1){

                    holder.main.setVisibility(View.GONE);

                    holder.downlayout.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

                    i=0;

                }

            }
        });


      /*  if(typr.equalsIgnoreCase("sub")){

            holder.downlayout.setVisibility(View.GONE);
        }*/

    }



    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}