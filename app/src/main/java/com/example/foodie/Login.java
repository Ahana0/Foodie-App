package com.example.foodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextInputLayout email, pass;
    Button Signin, Signinphone;
    TextView Forgotpassword;
    TextView txt;
    FirebaseAuth FAuth;
    String em;
    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (TextInputLayout) findViewById(R.id.Demail);
        pass = (TextInputLayout) findViewById(R.id.Dpassword);
        Signin = (Button) findViewById(R.id.Loginbtn);
        txt = (TextView) findViewById(R.id.donot);
        Forgotpassword = (TextView) findViewById(R.id.Dforgotpass);
        Signinphone = (Button) findViewById(R.id.Dphonebtn);
        FAuth = FirebaseAuth.getInstance();

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                em = email.getEditText().getText().toString().trim();
                pwd = pass.getEditText().getText().toString().trim();
                if (isValid()) {
                    final ProgressDialog mDialog = new ProgressDialog(Login.this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Logging in...");
                    mDialog.show();
                    FAuth.signInWithEmailAndPassword(em, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                mDialog.dismiss();
                                if (FAuth.getCurrentUser().isEmailVerified()) {
                                    mDialog.dismiss();
                                    Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                    Intent z = new Intent(Login.this, CustomerFoodPanel_BottomNavigation.class);
                                    startActivity(z);
                                    finish();


                                } else {
                                    ReusableCodeForAll.ShowAlert(Login.this, "", "Please Verify your Email");
                                }

                            } else {

                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(Login.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }

            }
        });

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Register = new Intent(Login.this, Registration.class);
                startActivity(Register);
                finish();

            }
        });

        Forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(Login.this, ForgotPassword.class);
                startActivity(a);
                finish();

            }
        });

        Signinphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent q = new Intent(Login.this, Loginphone.class);
                startActivity(q);
                finish();
            }
        });
    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isvalidemail = false, isvalidpassword = false, isvalid = false;
        if (TextUtils.isEmpty(em)) {
            email.setErrorEnabled(true);
            email.setError("Email is required");
        } else {
            if (em.matches(emailpattern)) {
                isvalidemail = true;
            } else {
                email.setErrorEnabled(true);
                email.setError("Enter a valid Email Address");
            }

        }
        if (TextUtils.isEmpty(pwd)) {
            pass.setErrorEnabled(true);
            pass.setError("Password is required");
        } else {
            isvalidpassword = true;
        }
        isvalid = (isvalidemail && isvalidpassword) ? true : false;
        return isvalid;
    }
}