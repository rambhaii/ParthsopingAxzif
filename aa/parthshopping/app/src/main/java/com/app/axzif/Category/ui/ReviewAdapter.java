package com.app.axzif.Category.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.axzif.Category.dto.ReviewData;
import com.app.axzif.Dashboard.dto.ReviewImages;
import com.app.axzif.R;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter  extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>
{   Context context;
    ArrayList<ReviewData>loginResponses;

    public ReviewAdapter(ArrayList<ReviewData> loginResponses, Context context) {
    this.loginResponses = loginResponses;
    this.context = context;

}

    @NonNull
    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list, parent, false);
        return new ReviewAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.MyViewHolder holder, int position)
    {
        ReviewData data = loginResponses.get(position);
        holder.name.setText(data.getCustomer_id().getName());
        holder.description.setText(data.getDescription());
        holder.title.setText(data.getTitle());

        try {
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Date time1 = null;
            time1 = fmt.parse(data.getCreated_date());
            String sd = newFormat.format(time1);
            holder.date.setText("" + sd + "");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        Glide.with(holder.itemView)
                .load(data.getCustomer_id().getProfile())
                .fitCenter()
                .error(R.drawable.noimage1)
                .into(holder.img);

        ReviewImages adapter=new ReviewImages(context,data.getImages());
        GridLayoutManager layoutManager=new GridLayoutManager(context,10);
        holder.recyclerview.setLayoutManager(layoutManager);
        holder.recyclerview.setAdapter(adapter);
        adapter.notifyDataSetChanged();



    }

    @Override
    public int getItemCount() {
        return loginResponses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {  TextView name,title,description,date;
       CircleImageView img;
       RecyclerView recyclerview;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            name=itemView.findViewById(R.id.name_1);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            description=itemView.findViewById(R.id.description);
            img=itemView.findViewById(R.id.img);
            recyclerview=itemView.findViewById(R.id.recyclerview);

        }
    }
}
