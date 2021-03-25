package com.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodie.LoginDelivery;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Delivery_LoginPhone extends AppCompatActivity {
    EditText num;
    Button sendotp, signinemail;
    TextView txtsignup;
    CountryCodePicker cpp;
    String cp;
    FirebaseAuth FAuth;
    String numberr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_login_phone);

        num = (EditText) findViewById(R.id.Dphonenumber);
        sendotp = (Button) findViewById(R.id.Sendotp);
        cpp = (CountryCodePicker) findViewById(R.id.countrycode);
        cp="+880";
        signinemail = (Button) findViewById(R.id.DbtnEmail);
        txtsignup = (TextView) findViewById(R.id.Signupif);

        FAuth = FirebaseAuth.getInstance();

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberr = num.getText().toString().trim();
                String phonenumber = cp+ numberr;
                Intent b = new Intent(Delivery_LoginPhone.this, Delivery_sendOTP.class);
                b.putExtra("phonenumber", phonenumber);
                startActivity(b);
                finish();

            }
        });


        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Delivery_LoginPhone.this,RegistrationDelivery.class);
                startActivity(a);
                finish();
            }
        });

        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent em = new Intent(Delivery_LoginPhone.this, LoginDelivery.class);
                startActivity(em);
                finish();
            }
        });

    }
}



