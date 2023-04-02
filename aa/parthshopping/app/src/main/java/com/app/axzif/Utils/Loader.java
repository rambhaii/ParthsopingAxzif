package com.app.axzif.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.app.axzif.R;


public class Loader extends Dialog{

     public Loader(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public Loader(Context context, int theme) {
        super(context, theme);
        // TODO Auto-generated constructor stub
        setContentView(R.layout.loader_layout);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(context,R.color.colorPrimary));




    }

    public Loader(Context context, boolean cancelable,
                  OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        // TODO Auto-generated constructor stub
    }
}
