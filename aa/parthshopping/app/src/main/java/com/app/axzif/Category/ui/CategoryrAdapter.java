package com.app.axzif.Category.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Category.dto.SubCategory;
import com.app.axzif.R;
import com.app.axzif.Utils.UtilMethods;
import de.hdodenhof.circleimageview.CircleImageView;


public class CategoryrAdapter extends RecyclerView.Adapter<CategoryrAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;
    String typr="";
    int i=0;

    SubCategoryrAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<SubCategory> transactionsObjects = new ArrayList<>();
    Category transactions = new Category();



    public class MyViewHolder extends RecyclerView.ViewHolder {
         public  TextView name;
         ImageView downlayout;
        LinearLayout main;
        RecyclerView recyclerView;
        CircleImageView imageview;


        public MyViewHolder(View view) {
            super(view);

            name =  view.findViewById(R.id.name);
            imageview =  view.findViewById(R.id.imageview);
            downlayout =  view.findViewById(R.id.downlayout);
            main =  view.findViewById(R.id.detaillayout);
            recyclerView =  view.findViewById(R.id.recyclerView);

        }
    }

    public CategoryrAdapter(ArrayList<Category> transactionsList, Context mContext,String typr) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.typr = typr;
    }

    @Override
    public CategoryrAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);

        return new CategoryrAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final CategoryrAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);

        holder.name.setText("" + operator.getCategoryName());


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.noimage);
        requestOptions.error(R.drawable.ic_customer_service);

       Log.e("url","url    " + operator.getIconImg() );


        Glide.with(mContext)
                .load( operator.getIconImg())
                .apply(requestOptions)
                .into(holder.imageview);


        UtilMethods.INSTANCE.GetSubcategory(mContext,operator.getId(),null,holder.recyclerView,holder.downlayout);



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

    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}