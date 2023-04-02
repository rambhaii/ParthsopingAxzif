package com.app.axzif.Dashboard.dto;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.axzif.R;


public class HomeAdapterPro extends RecyclerView.Adapter<HomeAdapterPro.ViewHolder>{
    private MyLeadData[] listdata;
    String val;

     public HomeAdapterPro(MyLeadData[] listdata,String val) {
        this.listdata = listdata;
        this.val = val;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.prod_lead_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MyLeadData myListData = listdata[position];



        holder.idCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i=new Intent(view.getContext(), Item_Detail_Product.class);
                view.getContext().startActivity(i);


            }
        });


    }

    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

TextView text;
ImageView image;
CardView idCardView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.text = (TextView) itemView.findViewById(R.id.text);
            this.image = (ImageView) itemView.findViewById(R.id.image);
            this.idCardView = (CardView) itemView.findViewById(R.id.idCardView);
          //  this.item =  itemView.findViewById(R.id.item);


        }
    }


}
