package com.app.axzif.Dashboard.TabViewFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.app.axzif.Category.dto.ReviewData;
import com.app.axzif.Category.ui.ReviewAdapter;
import com.app.axzif.Login.dto.LoginResponse;
import com.app.axzif.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Review extends Fragment {


    WebView webView;
    RecyclerView recyclerview;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        webView = view.findViewById(R.id.descriptionWebView);


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
        webView.loadData(getArguments().getString("review"), "text/html", "UTF-8");

        String data=getArguments().getString("review");
        Log.d("kkd",data);
        LoginResponse dataum = new Gson().fromJson(data, LoginResponse.class);
        recyclerview =view.findViewById(R.id.recyclerview);
        ArrayList<ReviewData> reviewData = dataum.getReviewData();
        ReviewAdapter servicesAdapter=new ReviewAdapter(reviewData,getActivity());
        recyclerview.setHasFixedSize(false);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(llm);
        recyclerview.setAdapter(servicesAdapter);
        return view;
    }
}