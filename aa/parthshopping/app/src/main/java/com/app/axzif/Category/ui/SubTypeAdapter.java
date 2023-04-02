package com.app.axzif.Category.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.app.axzif.Category.dto.SubTypeData;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class SubTypeAdapter extends   RecyclerView.Adapter<SubTypeAdapter.ViewHolder>
{
    ArrayList<SubTypeData> subTypeData= new ArrayList<>();
    private  Context context;

    public SubTypeAdapter(ArrayList<SubTypeData> subTypeData, Context context )
    {
        this.subTypeData = subTypeData;
        this.context=context;
    }


    @NonNull
    @Override
    public SubTypeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.sub_type_layout, parent, false);
        return  new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull SubTypeAdapter.ViewHolder holder, int position)
    {
        final SubTypeData slider=subTypeData.get(position);
          holder.title.setText(slider.getSubcategory_type().toString());
           holder.ic_card.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Loader loader;


                   if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {


                       loader = new Loader(context,android.R.style.Theme_Translucent_NoTitleBar);

                       // Toast.makeText(mContext, operator.getId()+"", Toast.LENGTH_SHORT).show();
                       loader.show() ;
                       loader.setCancelable(false);
                       loader.setCanceledOnTouchOutside(false);
                      // Toast.makeText(context, ""+slider.getCategory_id() +"    "+slider.getSubcategory_id()  , Toast.LENGTH_SHORT).show();
                       UtilMethods.INSTANCE.GetProductList(context, ""+slider.getCategory_id(),""+slider.getSubcategory_id(),
                               ""+ slider.getId(), loader);

                   } else {
                       UtilMethods.INSTANCE.NetworkError(context, context.getResources().getString(R.string.network_error_title),
                               context.getResources().getString(R.string.network_error_message));
                   }
               }
           });
    }

    @Override
    public int getItemCount()
    {
        return subTypeData.size();
        //Log.d("sdfsdff",menuList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {   TextView title,price;
         CardView    ic_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title=itemView.findViewById(R.id.name);
            this.ic_card=itemView.findViewById(R.id.ic_card);


        }
    }
}