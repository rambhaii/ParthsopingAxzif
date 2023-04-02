package com.app.axzif.Category.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;


/**
 * it display the list of Images at start of app
 */


public class CustomPagerAdapter extends PagerAdapter {

    private ArrayList<Category> ImageList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(ArrayList<Category> ImageList, Context context) {
        this.ImageList = ImageList;
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);


        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

         // Log.e("Image"," "+ApplicationConstant.INSTANCE.baseUrtofferimage+ImageList.get(position).getImage());

        Glide.with(mContext)
                .load( ImageList.get(position).getBanner_img())
                .into(imageView);

        itemView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

               /* Loader loader = null;

                loader = new Loader(mContext,android.R.style.Theme_Translucent_NoTitleBar);

             if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.Catsubproduct(mContext, ImageList.get(position).getId(),"cat",loader);

                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }*/

            }
        });

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}