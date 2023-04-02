package com.app.axzif.Splash.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.axzif.Login.ui.LoginActivity;
import com.app.axzif.R;
import com.app.axzif.Register.ui.RegisteredActivity;

public class SelectAccountActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_login,signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);

        signup=findViewById(R.id.signup);
        bt_login=findViewById(R.id.bt_login);

        signup.setOnClickListener(this);
        bt_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(bt_login==v){

            Intent intent = new Intent(SelectAccountActivity.this, LoginActivity.class);
            startActivity(intent);

          //  finish();

        }
       if(signup==v){


           Intent intent = new Intent(SelectAccountActivity.this, RegisteredActivity.class);
           startActivity(intent);

          // finish();


        }

    }
}