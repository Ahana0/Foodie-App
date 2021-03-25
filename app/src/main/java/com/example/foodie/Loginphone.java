package com.example.foodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class Loginphone extends AppCompatActivity {


    EditText num;
    TextView txtsignup;
    Button sendotp,signinemail;
    CountryCodePicker cpp;
    FirebaseAuth FAuth;
    String numberr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginphone);

        num=(EditText)findViewById(R.id.number);
        sendotp=(Button)findViewById(R.id.otp);
        cpp=(CountryCodePicker)findViewById(R.id.CountryCode);
        signinemail=(Button)findViewById(R.id.btnEmail);
        txtsignup=(TextView)findViewById(R.id.acsignup);

        FAuth=FirebaseAuth.getInstance();

        sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberr=num.getText().toString().trim();
                String phonenumber= "+880" + numberr;
                Intent b=new Intent(Loginphone.this,Delivery_sendOTP.class);
                b.putExtra("phonenumber",phonenumber);
                startActivity(b);
                finish();

            }
        });

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a=new Intent(Loginphone.this,Registration.class);
                startActivity(a);
                finish();
            }
        });

        signinemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent em=new Intent(Loginphone.this, Login.class);
                startActivity(em);
                finish();
            }
        });

    }
}