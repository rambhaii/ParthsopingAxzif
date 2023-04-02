package com.app.axzif.Register.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.app.axzif.Dashboard.ui.ProfileDetail;
import com.app.axzif.Login.ui.LoginActivity;
import com.app.axzif.R;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {

    Button tv_signup;
    TextView bt_login;
    Loader loader;
    EditText name,emailid,Phonenumber,Password,cPassword;
   String id="";
   String number="";
   CircleImageView edit_profile;
   ImageView upload;
    int SELECT_PICTURE = 200;
    String imgProfile="";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        id=(getIntent().getStringExtra( "id"));

        number=(getIntent().getStringExtra("number"));
        getID();
    }

    private void getID() {

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);


        name=findViewById(R.id.name);
        emailid=findViewById(R.id.emailid);
        Phonenumber=findViewById(R.id.Phonenumber);
        Password=findViewById(R.id.Password);
        cPassword=findViewById(R.id.cPassword);
        upload=findViewById(R.id.upload);
        edit_profile=findViewById(R.id.edit_profile);

        Phonenumber.setText(number);


        bt_login=findViewById(R.id.bt_login);
        tv_signup=findViewById(R.id.tv_signup);

        tv_signup.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        upload.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Register User");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onClick(View v)
    {
        if (v==upload)
        {
            verifyStoragePermissions();
            chooseImage();
        }


        if(v==tv_signup)
        {
            if(name.getText().toString().isEmpty())
            {
                name.setError("please Enter Your Name");
                name.requestFocus();
                return;
            }
            if(emailid.getText().toString().isEmpty())
            {
                emailid.setError("please Enter Your E-mail");
                emailid.requestFocus();
                return;
            }
            if(Password.getText().toString().isEmpty())
            {
                Password.setError("please Enter Password");
                Password.requestFocus();
                return;
            }
            if(cPassword.getText().toString().isEmpty())
            {
                cPassword.setError("please Enter Confirm Password");
                cPassword.requestFocus();
                return;
            }
            if(!Password.getText().toString().equals(cPassword.getText().toString()))
            {
                Toast.makeText(this, "Password Does Not match", Toast.LENGTH_SHORT).show();
            }
            if (!UtilMethods.INSTANCE.isValidEmail(emailid.getText().toString().trim())){

                Toast.makeText(this, "please enter valid email id ", Toast.LENGTH_SHORT).show();
                return;
            }
            if (imgProfile.isEmpty()){

                Toast.makeText(this, "please select image ", Toast.LENGTH_SHORT).show();
                return;
            }


            if (UtilMethods.INSTANCE.isNetworkAvialable(RegisteredActivity.this)) {
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.signup(RegisteredActivity.this, name.getText().toString().trim(),
                        emailid.getText().toString().trim(),
                        Password.getText().toString().trim(), loader,RegisteredActivity.this,id,"","","","","",
                "", Phonenumber.getText().toString().trim(), imgProfile);
            } else {
                UtilMethods.INSTANCE.NetworkError(RegisteredActivity.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }

         if(v==bt_login){

             startActivity(new Intent(RegisteredActivity.this, LoginActivity.class));


        }


    }
    public void verifyStoragePermissions()
    {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(RegisteredActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImageUri, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);
                    c.close();
                    imgProfile = picturePath;
                    Log.d("imagvjhbge", imgProfile);
                    edit_profile.setImageURI(selectedImageUri);
                }


            }
        }
    }
}
