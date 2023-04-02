package com.app.axzif.Dashboard.TabViewFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.app.axzif.R;


public class Description extends Fragment {

  WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.fragment_description, container, false);
        webView=view.findViewById(R.id.descriptionWebView);


        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultFontSize(14);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);
        webView.setHapticFeedbackEnabled(false);
        webView.loadData(getArguments().getString("descrption"), "text/html", "UTF-8");

        Log.d("bhcsbjdcbjsdjbsh: ", getArguments().getString("descrption"));
        return view;
    }
}