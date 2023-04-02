package com.app.axzif.Filter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;

    private int row_index=-1;
    ArrayList<Category> selecteditems=new ArrayList<>();
    int selectedCount=50;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox select_brand;
        RelativeLayout select;

        public MyViewHolder(View view) {
            super(view);

            title =  view.findViewById(R.id.title);
            select_brand =  view.findViewById(R.id.select_brand);
            select =  view.findViewById(R.id.select);


        }
    }

    public BrandListAdapter(ArrayList<Category> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
     }

    @Override
    public BrandListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_category_sub_list_item, parent, false);

        return new BrandListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final BrandListAdapter.MyViewHolder holder, final int position)
    {

         Category operator = transactionsList.get(position);
        SharedPreferences myPreferences1 = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String id = myPreferences1.getString(ApplicationConstant.INSTANCE.id, "").toString();


        holder.title.setText(" "+operator.getBrand_name());
        holder.select_brand.setText("  "+operator.getBrand_name());
        holder.select_brand.setChecked(operator.isSelected);

            if(!id.equals("null"))
            {
                List<String[]> list= new Gson().fromJson(id,new TypeToken<List<String>>() {}.getType());
                int index= list.indexOf(operator.getId());
                Log.d("djfjhfg",index+"---"+operator.getId());
                if (index!=-1)
                {
                    Log.d("dfhgdhfddd",operator.getId());
                    operator.setSelected(true);
                    selecteditems.add(operator);
                    holder.select_brand.setChecked(true);
                    ((FilterActivity) mContext).ItemClick(operator.getId() ,operator,"Brand1");
                }

        }


        holder.select_brand.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                row_index=position;

                ((FilterActivity) mContext).ItemClick(operator.getId() ,operator,"Brand");
                if (operator.isSelected)
                {
                    operator.setSelected(false);

                }
                else
                {
                    operator.setSelected(true);
                    selecteditems.add(operator);
                    holder.select_brand.setChecked(true);
                }


            /*    Log.d("dhfoihgoeg: ",String.valueOf(selecteditems.size()));
                    if(selectedCount>selecteditems.size())
                    {
                        selecteditems.add(operator);
                        operator.setSelected(true);
                        holder.select_brand.setChecked(true);
                    }
                    else
                    {
                        if (selecteditems.contains(operator))
                        {
                            selecteditems.remove(operator);
                            holder.select_brand.setChecked(false);
                            operator.setSelected(false);
                        }
                        else
                        {
                            operator.setSelected(false);
                            Toast.makeText(mContext, "You can not select more item", Toast.LENGTH_SHORT).show();
                            holder.select_brand.setChecked(false);
                        }

                    }
//*/



              /*    row_index=position;
                  notifyDataSetChanged();

                ((FilterActivity) mContext).ItemClick(operator.getId() ,"Brand");
        */    }
        });


        if (row_index==position)
        {
          /*  selecteditems.add(operator);
            operator.setSelected(true);
            holder.select_brand.setChecked(true);
*/

        }
        else
        {
          /*  if(id.contains(operator.getId()))
        {

            ((FilterActivity) mContext).ItemClick(operator.getId() ,operator,"Brand");
            selecteditems.add(operator);
            operator.setSelected(true);
            holder.select_brand.setChecked(true);
        }
        else
        {*/
            if (selecteditems.contains(operator))
            {
               /* selecteditems.remove(operator);
                holder.select_brand.setChecked(false);
                operator.setSelected(false);*/

            }

            /*else
            {
                operator.setSelected(false);
                holder.select_brand.setChecked(false);
            }*/
        }
      //  }



    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}