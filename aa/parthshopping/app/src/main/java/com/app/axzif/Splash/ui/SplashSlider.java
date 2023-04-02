package com.app.axzif.Splash.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.axzif.Dashboard.ui.DashboardMain;
import com.app.axzif.R;


public class SplashSlider extends AppCompatActivity implements View.OnClickListener{

    SplashSliderPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    Handler handler;
    LinearLayout dotsCount;
    Integer mDotsCount;
    public static TextView mDotsText[];
    private LinearLayout llCategory;


    TextView tvSkip;
    TextView tvLogin;
    TextView tvSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_slider);

        getId();

    }

    public void getId()
    {
        tvSkip=(TextView)findViewById(R.id.tv_skip);
        tvLogin=(TextView)findViewById(R.id.tv_login);
        tvSignup=(TextView)findViewById(R.id.tv_signup);

        mCustomPagerAdapter = new SplashSliderPagerAdapter(this);
         handler=new Handler();
        dotsCount   = (LinearLayout)findViewById(R.id.image_count);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        mDotsCount = mViewPager.getAdapter().getCount();

        mDotsText = new TextView[mDotsCount];

        //here we set the dots
        for(int i = 0; i < mDotsCount; i++) {
            mDotsText[i] = new TextView(this);
            mDotsText[i].setText(".");
            mDotsText[i].setTextSize(45);
            mDotsText[i].setTypeface(null, Typeface.BOLD);
            mDotsText[i].setTextColor(android.graphics.Color.GRAY);
            dotsCount.addView(mDotsText[i]);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < mDotsCount; i++)
                {
                    mDotsText[i].setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                mDotsText[position].setTextColor(getResources().getColor(R.color.colorBackground));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        postDelayedScrollNext();

        setListners();
    }

    private void setListners() {
        tvSkip.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);
    }

    private void postDelayedScrollNext() {
        handler.postDelayed(new Runnable() {
            public void run() {

                if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                    mViewPager.setCurrentItem(0);
                    postDelayedScrollNext();
                    return;
                }
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                // postDelayedScrollNext(position+1);
                postDelayedScrollNext();

                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        }, 3000);

    }

    @Override
    public void onClick(View v) {
        if(v==tvSkip)
        {
            Intent intent = new Intent(SplashSlider.this, DashboardMain.class);
            startActivity(intent);
            finish();

        }
        if(v==tvLogin)
        {


           // Intent intent = new Intent(SplashSlider.this, LoginActivity.class);
            Intent intent = new Intent(SplashSlider.this, SelectAccountActivity.class);
            startActivity(intent);
            finish();



        } if(v==tvSignup)
        {
           // UtilMethods.INSTANCE.goAnotherActivity(this, SignUp.class);
        }
    }
}
