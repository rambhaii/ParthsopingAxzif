package com.app.axzif.Order.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.VerticalStepView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.app.axzif.Category.dto.Category;
import com.app.axzif.R;
import com.app.axzif.Utils.UtilMethods;

public class OrderDetailActivity extends AppCompatActivity implements View.OnClickListener {

    String operatorData;
    ImageView image_product, downloadinvoice;
    TextView orderid, product_status, product_name, product_description, prise, name, phonenumber, addressDetail, Total_Price, Special_Price, Extra_Discount, Selling_Price, Last_Price;
    File imagePath, imagePathspdf;
    String directoryPath;
    int pageWidth = 1200;
    CoordinatorLayout coordinatorLayout;
    Bitmap btm, btmScaleSet;
    VerticalStepView step_view;
    int order_status ;
    TextView orderTracking;
    boolean status = true;

    @SuppressLint({"UseCompatLoadingForDrawables", "SuspiciousIndentation"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        operatorData = getIntent().getStringExtra("operator");
        orderid = findViewById(R.id.orderid);
        product_status = findViewById(R.id.product_status);
        product_name = findViewById(R.id.product_name);
        product_description = findViewById(R.id.product_description);
        image_product = findViewById(R.id.image_product);
        prise = findViewById(R.id.prise);
        name = findViewById(R.id.name);
        phonenumber = findViewById(R.id.phonenumber);
        addressDetail = findViewById(R.id.addressDetail);
        Total_Price = findViewById(R.id.Total_Price);
        Special_Price = findViewById(R.id.Special_Price);
        Extra_Discount = findViewById(R.id.Extra_Discount);
        Selling_Price = findViewById(R.id.Selling_Price);
        Last_Price = findViewById(R.id.Last_Price);
        downloadinvoice = findViewById(R.id.downloadinvoice);
        step_view = findViewById(R.id.step_view);
        orderTracking = findViewById(R.id.orderTracking);
        orderTracking.setOnClickListener(this);

        Gson gson = new Gson();
        operatorget = gson.fromJson(operatorData, Category.class);
     order_status =  Integer.parseInt(operatorget.getOrder_status());
        Log.d("ooo", "" + order_status);


        btm = BitmapFactory.decodeResource(getResources(), R.drawable.rnd_logo);
        btm = BitmapFactory.decodeResource(getResources(), R.drawable.rnd_logo);
        btmScaleSet = Bitmap.createScaledBitmap(btm, 200, 100, false);
        downloadinvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                createPdf(operatorData);
            }
        });
        // react-native init project
        getSetVAlue();

        //view order details

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Order View Details");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            step_view.setStepsViewIndicatorComplectingPosition(getList().size())
                    .reverseDraw(false)
                    .setStepViewTexts(getList())
                    .setLinePaddingProportion(0.85f)
                    .setStepsViewIndicatorCompletedLineColor(getColor(R.color.green))
                    .setStepsViewIndicatorUnCompletedLineColor(getColor(R.color.black))
                    .setStepViewComplectedTextColor(getColor(R.color.black))
                    .setStepViewUnComplectedTextColor(getColor(R.color.catgcol))
                    .setStepsViewIndicatorCompleteIcon(getDrawable(R.drawable.cc))
                    .setStepsViewIndicatorAttentionIcon(getDrawable(R.drawable.attention))
                    .setStepsViewIndicatorDefaultIcon(getDrawable(R.drawable.default_icon));
            step_view.setStepsViewIndicatorComplectingPosition(order_status);
        }
       /* 1=orderd,
                0=cancled,
                2=packed,
                3=on the way,
        4=delevered*/

    }

    private List<String> getList() {

        List<String> list = new ArrayList<>();
        if (order_status==1)
        {
            list.add("Order confirmed");
        }
        else if(order_status==0)
        {
            list.add("Order cancelled");
        }else
        {
            list.add("Order confirmed");
        }

        list.add("Picked by courier");
        list.add("On the way");
        list.add("Delivered");
        return list;
    }


    Category operatorget;

    private void getSetVAlue() {


        orderid.setText("Order ID : " + operatorget.getOrder_number());
        product_name.setText("" + operatorget.getProductName());
        product_description.setText("" + operatorget.getProduct_quantity() + " Quantity ");
        prise.setText("\u20B9 " + operatorget.getProductPrice());
     //   Log.d("sjdfhdfjh",operatorget.getName());
        name.setText("" + operatorget.getName());
        phonenumber.setText("+91- " + operatorget.getMobile());
        Total_Price.setText("\u20B9 " + operatorget.getProductPrice() + "  ");
        Special_Price.setText("\u20B9 " + operatorget.getProductPrice() + "  ");
        Selling_Price.setText("\u20B9 " + operatorget.getProductPrice() + "  ");
        addressDetail.setText("" + operatorget.getAddress() + " , " + operatorget.getLandmark() + " , " + operatorget.getState() + " , " +
                operatorget.getCity() + " , " + operatorget.getZip());
        Last_Price.setText("");
        Extra_Discount.setText("\u20B9 " + operatorget.getDiscount() + "  ");
        if (operatorget.getOrder_status().equalsIgnoreCase("0")) {
            product_status.setText("cancled ");
            product_status.setTextColor(this.getResources().getColor(R.color.red));

        } else if (operatorget.getOrder_status().equalsIgnoreCase("1")) {
            product_status.setText("orderd ");
            product_status.setTextColor(this.getResources().getColor(R.color.green));

        } else if (operatorget.getOrder_status().equalsIgnoreCase("2")) {
            product_status.setText("packed ");
            product_status.setTextColor(this.getResources().getColor(R.color.yallow));

        } else if (operatorget.getOrder_status().equalsIgnoreCase("3")) {
            product_status.setText("on the way ");
            product_status.setTextColor(this.getResources().getColor(R.color.yallow));

        } else if (operatorget.getOrder_status().equalsIgnoreCase("4")) {
            product_status.setText("delevered ");
            product_status.setTextColor(this.getResources().getColor(R.color.green));
        }
        UtilMethods.INSTANCE.GetProductImg(this, operatorget.getId(), null, image_product);
    }


    private void createPdf(String sometext)
    {
        Gson gson = new Gson();
        operatorget = gson.fromJson(sometext, Category.class);
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1200, 1600, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();


        canvas.drawBitmap(btmScaleSet, 20, 20, paint);

        Paint titlePaint = new Paint();
        titlePaint.setTextAlign(Paint.Align.CENTER);
        titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        titlePaint.setTextSize(22);

        Paint headingPaint = new Paint();
        headingPaint.setTextAlign(Paint.Align.CENTER);
        headingPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        headingPaint.setTextSize(70);

        Paint subTitlePaint = new Paint();
        subTitlePaint.setTextAlign(Paint.Align.CENTER);
        subTitlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        subTitlePaint.setTextSize(18);

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(30);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        canvas.drawText("ORDER ID : " + operatorget.getOrder_number(), 1160, 60, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(36);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        canvas.drawText(operatorget.getProductName(), 30, 160, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(18f);
        paint.setColor(Color.BLACK);
        canvas.drawText(operatorget.getProduct_quantity() + " Quantity ", 30, 185, paint);


        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(25f);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setColor(Color.BLACK);
        canvas.drawText("\u20B9 " + operatorget.getProductPrice(), 40, 220, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(25f);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        paint.setColor(Color.GREEN);
        canvas.drawText("ORDERED", pageWidth - 40, 220, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        canvas.drawRect(20, 250, pageWidth - 20, 300, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        canvas.drawRect(20, 300, pageWidth - 20, 400, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        canvas.drawText("Shipping Details", 40, 280, paint);


        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(18f);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        paint.setColor(Color.BLACK);
        canvas.drawText("" + operatorget.getAddress() + " , " + operatorget.getLandmark() + " , " + operatorget.getState() + " , " +
                operatorget.getCity() + " , " + operatorget.getZip(), 40, 340, paint);
        canvas.drawText("Phone Number :- " + "+91- " + operatorget.getMobile(), 40, 380, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        canvas.drawRect(20, 450, pageWidth - 20, 740, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        paint.setColor(Color.GRAY);
        canvas.drawRect(20, 500, pageWidth - 20, 740, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(30f);
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("Price Detais", 40, 485, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText("Last Price", 40, 540, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(25);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawText("\u20B9 " + operatorget.getProductPrice() + "  ", pageWidth - 40, 540, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
        canvas.drawText("Selling Price", 40, 580, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
        canvas.drawText("\u20B9 " + operatorget.getProductPrice() + "  ", pageWidth - 40, 580, paint);

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
        canvas.drawText("Extra Discount", 40, 620, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
        canvas.drawText("\u20B9 " + operatorget.getProductPrice() + "  ", pageWidth - 40, 620, paint);


        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
        canvas.drawText("Special Price", 40, 660, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
        canvas.drawText("0.00", pageWidth - 40, 660, paint);


        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(25);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        canvas.drawText("Total Price", 40, 700, paint);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setTextSize(25);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setColor(Color.BLACK);
        canvas.drawText("\u20B9 " + operatorget.getProductPrice(), pageWidth - 40, 700, paint);


        document.finishPage(page);
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Axzif Invoice/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path + operatorget.getProductName() + operatorget.getOrder_number() + ".pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "www.journaldev.com", Snackbar.LENGTH_LONG).setAction(
                            "View", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(OrderDetailActivity.this, "Undo Clicked", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });

            snackbar.show();
//            Toast.makeText(this, "Invoice Downloaded", Toast.LENGTH_LONG).show();
//            openFolder(targetPdf);
        } catch (IOException e)
        {
            Log.e("main", "error " + e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }

    public void openFolder(String filePath) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(filePath);
        intent.setDataAndType(uri, "text/pdf");
        startActivity(Intent.createChooser(intent, "Open folder"));
    }

    @Override
    public void onClick(View v)
    {
        if (v == orderTracking)
        {
            if (status == true)
            {
                step_view.setVisibility(View.VISIBLE);
                orderTracking.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_keyboard_arrow_up_24, 0);
                orderTracking.setTextColor(this.getResources().getColor(R.color.black));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    orderTracking.setCompoundDrawableTintList(ColorStateList.valueOf(Color.BLACK));
                }
                status = false;
            }
            else if (status == false)
            {
                step_view.setVisibility(View.GONE);
                orderTracking.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_keyboard_arrow_down_black_24dp, 0);
                orderTracking.setTextColor(this.getResources().getColor(R.color.green));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    orderTracking.setCompoundDrawableTintList(ColorStateList.valueOf((this.getResources().getColor(R.color.green))));
                }
                status = true;
             }


    }



    }
}