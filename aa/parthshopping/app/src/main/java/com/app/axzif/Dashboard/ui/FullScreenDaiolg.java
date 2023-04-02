package com.app.axzif.Dashboard.ui;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.app.axzif.Category.dto.Category;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jsibbold.zoomage.ZoomageView;

import com.app.axzif.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class FullScreenDaiolg extends DialogFragment {
   // ZoomageView fullImage;
    ImageView back_btn;
    SliderView sliderView;
    String url;
    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // getActivity().getSupportActionBar().hide();

        // Inflate the layout to use as dialog or embedded fragment
        View view=inflater.inflate(R.layout.purchase_items, container, false);
        sliderView=view.findViewById(R.id.fullImage);
       // fullImage=view.findViewById(R.id.fullImage);
        back_btn=view.findViewById(R.id.back_btn);
      /*  url= getArguments().getString("imageUrl")+"";
        Log.d("dkjgkj",url);*/
        url= getArguments().getString("imageUrl")+"";
        dataParse(url);




      /*  Glide.with(this)
                .load(url)
                .into(fullImage);*/
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             FullScreenDaiolg.this.dismiss();
            }
        });

        return view;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);
        // dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog;
    }

    private void dataParse(String  list)
    {

        ArrayList<Category> banerData=new ArrayList<Category>() ;
        banerData=new Gson().fromJson(list,new TypeToken<ArrayList<Category> >() {}.getType());

        sliderView.setSliderAdapter(new GallareySlider(getActivity(),banerData));
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        // sliderView.startAutoCycle();
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LOCALE);
        sliderView.setIndicatorSelectedColor(Color.BLACK);
        sliderView.setIndicatorUnselectedColor(Color.BLACK);
        sliderView.setScrollTimeInSec(2);
    }


}
