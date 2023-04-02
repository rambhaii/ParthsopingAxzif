package com.app.axzif.Login.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.app.axzif.R;
import com.app.axzif.Utils.FragmentActivityMessage;
import com.app.axzif.Utils.GlobalBus;
import com.app.axzif.Utils.Loader;
import com.app.axzif.Utils.UtilMethods;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public Button btLogin;
    public Button FwdokButton;
    public Button cancelButton;
    public TextInputLayout tilMobile;
    public TextInputLayout tilPass;
    public TextInputLayout tilMobileFwp;

    public AutoCompleteTextView edMobile;
    public EditText otpenter;
    public  EditText  edMobileFwp;
    public TextView forgotpass;
    TextView textotp,resendotp,resend;
    String otpget;

    Loader loader;
    String[] recent;
    String[] recentNumber;
    CheckBox rememberCheck;
    private static CountDownTimer countDownTimer;

    LinearLayout otp_layout;
    Button bt_verify_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        getId();

    }

    public void getId() {

        otp_layout=findViewById(R.id.otp_layout);
        bt_verify_otp=findViewById(R.id.bt_verify_otp);
        bt_verify_otp.setOnClickListener(this);
        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        btLogin=(Button)findViewById(R.id.bt_login);

        tilMobile=(TextInputLayout)findViewById(R.id.til_mobile);
        tilPass=(TextInputLayout)findViewById(R.id.til_pass);
        rememberCheck = (CheckBox) findViewById(R.id.check_pass);

        edMobile=(AutoCompleteTextView)findViewById(R.id.mobilenumber);
        otpenter=(EditText)findViewById(R.id.otpenter);

        forgotpass=(TextView) findViewById(R.id.tv_forgotpass);



        setListners();

    }


    public void setListners() {

        btLogin.setOnClickListener(this);
        forgotpass.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v==btLogin)
        {
            //   startActivity(new Intent(LoginActivity.this, MainActivity.class));

            if(!edMobile.getText().toString().trim().isEmpty())
            {
                String phone="+91"+edMobile.getText().toString().trim();

                if (!Pattern.matches("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$", phone))
                {
                    edMobile.setError("Enter a valid Phone Number");
                }
                else {


                    if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {

                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);

                        UtilMethods.INSTANCE.secureLogin(LoginActivity.this, edMobile.getText().toString().trim(), loader, this);

                    } else {
                        UtilMethods.INSTANCE.NetworkError(LoginActivity.this, getResources().getString(R.string.network_error_title),
                                getResources().getString(R.string.network_error_message));
                    }
                }

            }
            else {


                Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();


            }



        }


        if(v==bt_verify_otp)
        {
            if(otpget.equalsIgnoreCase(otpenter.getText().toString().trim())){


                if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.verifyOtp(LoginActivity.this, edMobile.getText().toString().trim(),otpenter.getText().toString().trim()
                            ,loader,this);

                } else {
                    UtilMethods.INSTANCE.NetworkError(LoginActivity.this, getResources().getString(R.string.network_error_title),
                            getResources().getString(R.string.network_error_message));
                }


            }else {


                Toast.makeText(this, "Please enter valid Otp", Toast.LENGTH_SHORT).show();


            }



        }








        if(v==forgotpass)
        {
            OpenDialogFwd();

        }

    }



    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("otpdetail")) {

            edMobile.setFocusable(false);

            otpenter.setText(""+ activityFragmentMessage.getFrom());

            otpget=activityFragmentMessage.getFrom();

            otp_layout.setVisibility(View.VISIBLE);
            bt_verify_otp.setVisibility(View.VISIBLE);
            btLogin.setVisibility(View.GONE);




        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    public void OpenDialogFwd() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.forgotpass, null);

        edMobileFwp = (EditText) view.findViewById(R.id.ed_mobile_fwp);
        tilMobileFwp=(TextInputLayout)view.findViewById(R.id.til_mobile_fwp);
        FwdokButton = (Button) view.findViewById(R.id.okButton);
        cancelButton = (Button) view.findViewById(R.id.cancelButton);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edMobileFwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateMobileFwp()) {
                    return;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FwdokButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateMobileFwp()) {
                    return;
                }



            }
        });

        dialog.show();
    }


    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                resendotp.setText(hms);//set text
            }

            public void onFinish() {

                resendotp.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);

                countDownTimer = null;//set CountDownTimer to null
                //  resendotp.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }

    public boolean validateMobileFwp(){
        if (edMobileFwp.getText().toString().trim().isEmpty()) {
            tilMobileFwp.setError(getString(R.string.err_msg_mobile));
            //  requestFocus(edMobileFwp);
            return false;
        } else {
            tilMobileFwp.setErrorEnabled(false);
            FwdokButton.setEnabled(true);
        }

        return true;
    }


}
