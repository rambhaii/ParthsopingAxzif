package com.app.axzif.Dashboard.dto;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.axzif.Category.dto.ReviewData;
import com.app.axzif.Category.ui.ReviewAdapter;
import com.app.axzif.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ReviewImages  extends RecyclerView.Adapter<ReviewImages.MyViewHolder>
{

    Context context;
    ArrayList<String> list;
    public ReviewImages( Context context,ArrayList<String> loginResponses) {
        this.list = loginResponses;
        this.context = context;


    }

        @NonNull
    @Override
    public ReviewImages.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewimage, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewImages.MyViewHolder holder, int position)
    {
        String dataimage=list.get(position);


        Log.d("lkgdjjfhkj",dataimage);
        Glide.with(holder.itemView)
                .load(dataimage)
                .fitCenter()
                .error(R.drawable.noimage1)
                .into(holder.cart_image);

    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cart_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cart_image=itemView.findViewById(R.id.cart_image);
        }
    }
}
