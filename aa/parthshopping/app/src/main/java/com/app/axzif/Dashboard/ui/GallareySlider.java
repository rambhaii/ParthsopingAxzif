package com.app.axzif.Dashboard.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;
import com.bumptech.glide.Glide;
import com.jsibbold.zoomage.ZoomageView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class GallareySlider extends SliderViewAdapter<GallareySlider.SliderAdapterVH> {

    private Context context;

    private List<Category> msubslider = new ArrayList<>();


    public GallareySlider(Context context, List<Category> SliderItems)
    {
        this.context = context;
        msubslider=SliderItems;
    }



    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slidelayout, null);
        return new SliderAdapterVH(inflate);

    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        Category sliderItem = msubslider.get(position);
        Log.d("dskjgdjgh",ApplicationConstant.INSTANCE.baseUrl+"/uploads/product/"+sliderItem.getProduct_img());
        Glide.with(viewHolder.itemView)
                .load(ApplicationConstant.INSTANCE.baseUrl+"/uploads/product/"+sliderItem.getProduct_img())
                .fitCenter()
                .into(viewHolder.imageViewBackground);





    }




    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return msubslider.size();
    }

    class SliderAdapterVH extends ViewHolder {


        View itemView;
        ZoomageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider1);

            this.itemView = itemView;
        }
    }


}
