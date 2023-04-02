package com.app.axzif.Filter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class ColourListAdapter extends RecyclerView.Adapter<ColourListAdapter.MyViewHolder> {

    private ArrayList<Category> transactionsList;
    private Context mContext;

    private int row_index=-1;
    ArrayList<Category> selecteditems=new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox rating;

        public MyViewHolder(View view) {
            super(view);

            title =  view.findViewById(R.id.title);
            rating =  view.findViewById(R.id.select_brand);


        }
    }

    public ColourListAdapter(ArrayList<Category> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public  ColourListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_category_sub_list_item, parent, false);

        return new ColourListAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ColourListAdapter.MyViewHolder holder, final int position) {
        final Category operator = transactionsList.get(position);
        holder.rating.setChecked(operator.isSelected);

        holder.title.setText(" "+operator.getColor_name());
        SharedPreferences myPreferences1 = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String id = myPreferences1.getString(ApplicationConstant.INSTANCE.colorid, "").toString();
        holder.rating.setText("  "+operator.getColor_name());

        Log.d("jdfgjd",id);

        if(!id.equals("null"))
        {
            List<String[]> list= new Gson().fromJson(id,new TypeToken<List<String>>() {}.getType());
            int index= list.indexOf(operator.getId());
            Log.d("djfjhfg",index+"---"+operator.getId());
            if (index!=-1)
            {
                Log.d("dfhgdhfddd",id);
                //operator.setSelected(true);
                // selecteditems.add(operator);
                holder.rating.setChecked(true);
                ((FilterActivity) mContext).ItemClick(operator.getId() ,operator,"colour1");
            }

        }


        holder.rating.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                row_index = position;
                //notifyDataSetChanged();
                ((FilterActivity) mContext).ItemClick(operator.getId(),operator ,"colour");
                if (operator.isSelected)
                {
                    operator.setSelected(false);

                }
                else
                {
                    operator.setSelected(true);
                    selecteditems.add(operator);
                    holder.rating.setChecked(true);

                }

            }
        });

        if(row_index==position)
        {
            /*selecteditems.add(operator);
            operator.setSelected(true);
            holder.rating.setChecked(true);
*/


        }
        else
        { /*  if(id.contains(operator.getId()))
          {
              ((FilterActivity) mContext).ItemClick(operator.getId() ,operator,"Brand");
            selecteditems.add(operator);
            operator.setSelected(true);
            holder.rating.setChecked(true);
         }
        else {*/
            if (selecteditems.contains(operator))
            {
                selecteditems.remove(operator);
                holder.rating.setChecked(false);
                operator.setSelected(false);
            } /*else {
                operator.setSelected(false);
                holder.rating.setChecked(false);
            }
        }*/
        }
         /*   if (operator.getId().equalsIgnoreCase(id))
           {
               ((FilterActivity) mContext).ItemClick(id ,operator,"colour");
            holder.rating.setChecked(true);
           }
        else
        {
            holder.rating.setChecked(false);
        }

        }*/


    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }
}