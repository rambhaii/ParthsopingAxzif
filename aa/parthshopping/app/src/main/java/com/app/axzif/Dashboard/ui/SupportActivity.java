package com.app.axzif.Dashboard.ui;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {

    TextView link;
    WebView chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        getid();


    }

    private void getid() {

/*
        link=findViewById(R.id.link);
        link.setOnClickListener(this);*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Support");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });


        chat=findViewById(R.id.chat);
        chat.loadUrl("https://www.axzif.com/");
        chat.getSettings().setJavaScriptEnabled(true);
        chat.setWebViewClient(new WebViewClient());

    }

    @Override
    public void onClick(View view) {

        /*if(view==link){

            Intent i=new Intent(this,WebViewActivity.class);
            i.putExtra("name","Website");
            i.putExtra("url", ApplicationConstant.INSTANCE.baseUrl);
            startActivity(i);

        }*/

    }
}
