package com.app.axzif.Dashboard.dto;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.Dashboard.ui.FullScreenDaiolg;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;

public class FullScreenImageAdapter extends SliderViewAdapter<FullScreenImageAdapter.FullScreenImageAdapterVH> {

    private Context context;

    private ArrayList<Category> ImageList;

    public FullScreenImageAdapter(Context context,ArrayList<Category> ImageList) {
        this.context = context;
        this.ImageList = ImageList;
    }

    public void renewItems(ArrayList<Category> ImageList) {
        this.ImageList = ImageList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.ImageList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Category sliderItem) {
        this.ImageList.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public FullScreenImageAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.full_image_slider_layout_item, null);
        return new FullScreenImageAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(FullScreenImageAdapterVH viewHolder, final int position)
    {
        final Category sliderItem = ImageList.get(position);
        Log.d("ksjghdhjgh",sliderItem.getProduct_img());
        Glide.with(viewHolder.itemView)
                .load(ApplicationConstant.INSTANCE.baseUrl+"/uploads/product/"+sliderItem.getProduct_img())
                .into(viewHolder.imageViewBackground);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


           //     showDialog(ApplicationConstant.INSTANCE.baseUrl+"/uploads/product/"+sliderItem.getProduct_img());

                showDialog( new Gson().toJson(ImageList));


           //   Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return ImageList.size();
    }

    class FullScreenImageAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public FullScreenImageAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }
    public void showDialog(String url)
    {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FullScreenDaiolg newFragment = new FullScreenDaiolg();
        Bundle bundle = new Bundle();
        bundle.putString("imageUrl", url);
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment)
                .addToBackStack(null).commit();




    }
}