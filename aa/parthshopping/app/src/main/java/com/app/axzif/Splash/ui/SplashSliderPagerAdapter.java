package com.app.axzif.Splash.ui;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.axzif.R;


/**
 * it display the list of Images at start of app
 */


public class SplashSliderPagerAdapter extends PagerAdapter {

    int[] mResources = {

            R.drawable.so,
            R.drawable.so,
            R.drawable.so,
            R.drawable.so,
            R.drawable.so
    };



    Context mContext;
    LayoutInflater mLayoutInflater;

    public SplashSliderPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

      /*  CircleImageView imageView = (CircleImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);*/

        container.addView(itemView);

        return itemView;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}