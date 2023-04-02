package com.app.axzif.Dashboard.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.axzif.Register.ui.RegisteredActivity;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.app.axzif.Login.dto.DataLogin;
import com.app.axzif.R;
import com.app.axzif.Utils.ApplicationConstant;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileDetail extends AppCompatActivity implements View.OnClickListener {
    TextView tvname,tvmobile,tvemail,tvaddress,city,state,country,zipcode;
    ImageView btnsignin,upload;
    CircleImageView edit_profile,image_view;
    LinearLayout lin_profile,line_updateprofile;
    int SELECT_PICTURE = 200;
    String imgProfile="";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    Button update;
    Loader loader;
    EditText editt_name,edit_contact,edit_email,edit_city,edit_zipcode,edit_address1,edit_address2,edit_state,edit_country;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        getId();


    }

    private void getId() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
           tvname =findViewById(R.id.tvname);
           tvmobile=findViewById(R.id.tvmobile);
           tvemail=findViewById(R.id.tvemail);
            tvaddress=findViewById(R.id.address);
        state=findViewById(R.id.state);
        country=findViewById(R.id.country);
        zipcode=findViewById(R.id.zipcode);

        btnsignin=findViewById(R.id.btnsignin);
        city=findViewById(R.id.city);
        lin_profile=findViewById(R.id.lin_profile);
        line_updateprofile=findViewById(R.id.line_updateprofile);
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);



        btnsignin.setOnClickListener(this);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String response = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        DataLogin balanceCheckResponse = new Gson().fromJson(response, DataLogin.class);
       Log.d("jfdhgkdjfghjkdgh",new Gson().fromJson(response, DataLogin.class).toString());
         user_id=balanceCheckResponse.getUserId()+"";
        String name=balanceCheckResponse.getName();
        String mobile=balanceCheckResponse.getMobile();
        String email=balanceCheckResponse.getEmail();
        String address=balanceCheckResponse.getAddress1();
        tvaddress.setText(address);
        tvemail.setText(email);
        tvmobile.setText(mobile);
        tvname.setText(name);
        city.setText(balanceCheckResponse.getCity()+" "+balanceCheckResponse.getState());
        state.setText(balanceCheckResponse.getState());
        country.setText(balanceCheckResponse.getCountry());
        zipcode.setText(balanceCheckResponse.getZip());

       //////////////////////////////update profiel///////////////////////////////////////////
        image_view=findViewById(R.id.image_view);
        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseUrl + balanceCheckResponse.getProfile())
                .fitCenter()
                .error(R.drawable.looo)
                .into(image_view);

        upload=findViewById(R.id.upload);
        edit_profile=findViewById(R.id.edit_profile);
        editt_name=findViewById(R.id.editt_name);
        edit_contact=findViewById(R.id.edit_contact);
        edit_email=findViewById(R.id.edit_email);
        edit_city=findViewById(R.id.edit_city);
        edit_zipcode=findViewById(R.id.edit_zipcode);
        edit_address1=findViewById(R.id.edit_address1);
        edit_address2=findViewById(R.id.edit_address2);
        edit_state=findViewById(R.id.edit_state);
        edit_country=findViewById(R.id.edit_country);
        update=findViewById(R.id.update);
        upload.setOnClickListener(this);
        update.setOnClickListener(this);

        editt_name.setText(balanceCheckResponse.getName());
        edit_contact.setText(balanceCheckResponse.getMobile());
        edit_email.setText(balanceCheckResponse.getEmail());
        edit_city.setText(balanceCheckResponse.getCity());
        edit_zipcode.setText(balanceCheckResponse.getZip());
        edit_address1.setText(balanceCheckResponse.getAddress1());
        edit_address2.setText(balanceCheckResponse.getAddress2());
        edit_state.setText(balanceCheckResponse.getState());
        edit_country.setText(balanceCheckResponse.getCountry());



    }

    @Override
    public void onClick(View v) {
        if (v==btnsignin)
        {
            lin_profile.setVisibility(View.GONE);
            line_updateprofile.setVisibility(View.VISIBLE);
        }
        if (v==upload)
        {
            verifyStoragePermissions();
            chooseImage();
        }
        if (v==update)
        {
            updateProfile();
        }

    }

    private void updateProfile()
    {
        if(editt_name.getText().toString().isEmpty())
         {
             editt_name.setError("please Enter Your Name");
            editt_name.requestFocus();
         }
        else if(imgProfile.isEmpty())
        {
            Toast.makeText(this, "Please select Image ", Toast.LENGTH_SHORT).show();
        }
      else  if(edit_email.getText().toString().isEmpty())
        {
            edit_email.setError("please Enter Your E-mail");
            edit_email.requestFocus();
        }
    else  if(edit_contact.getText().toString().isEmpty())
    {
        edit_contact.setError("please Enter Your contact");
        edit_contact.requestFocus();
    }
    else if(edit_city.getText().toString().isEmpty())
    {
        edit_city.setError("please Enter Your city");
        edit_city.requestFocus();
    }
    else if(edit_zipcode.getText().toString().isEmpty())
    {
        edit_zipcode.setError("please Enter Your zipcode");
        edit_zipcode.requestFocus();
    }
    else if(edit_address1.getText().toString().isEmpty())
    {
        edit_address1.setError("please Enter Your Address");
        edit_address1.requestFocus();
    }
    else if(edit_address2.getText().toString().isEmpty())
    {
        edit_address2.setError("please Enter Your Address");
        edit_address2.requestFocus();
    }
    else if(edit_state.getText().toString().isEmpty())
    {
        edit_state.setError("please Enter Your state");
        edit_state.requestFocus();
    } else if(edit_country.getText().toString().isEmpty())
    {
        edit_country.setError("please Enter Your country");
        edit_country.requestFocus();
    }
    else {
            if (UtilMethods.INSTANCE.isNetworkAvialable(ProfileDetail.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

   Log.d("djsdjkgdbjf",user_id);

                UtilMethods.INSTANCE.signup(ProfileDetail.this, editt_name.getText().toString().trim(),
                        edit_email.getText().toString().trim(),"",
                        loader,ProfileDetail.this,user_id,edit_address1.getText().toString().trim(),
                        edit_address2.getText().toString().trim(),
                        edit_state.getText().toString(),edit_country.getText().toString(),edit_city.getText().toString(),edit_zipcode.getText().toString(),edit_contact.getText().toString(),
                        imgProfile);

            }


        }
    }

    public void verifyStoragePermissions()
    {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(ProfileDetail.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void chooseImage()
    {
        Intent pickimage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickimage, 2);

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2)
            {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri)
                {
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImageUri, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    imgProfile = picturePath;
                    Log.d("imagvjhbge",imgProfile);
                    edit_profile.setImageURI(selectedImageUri);
                }

               /* if (null != selectedImageUri)
                {
                   *//* Uri selectedImage = data.getData();*//*
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImageUri, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    imgProfile=picturePath;
                    Log.d("img",imgProfile);
                    profile.setImageURI(selectedImageUri);
                    c.close();

                    //profile.setImageURI(selectedImageUri);
                }
                else
                {
                    Toast.makeText(getLocation, "Error", Toast.LENGTH_SHORT).show();
                }*/
            }
        }
    }
}
