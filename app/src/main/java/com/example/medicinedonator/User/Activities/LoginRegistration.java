package com.example.medicinedonator.User.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.medicinedonator.R;
import com.example.medicinedonator.User.Fragments.LoginFragment;
import com.example.medicinedonator.User.Fragments.RegisterFragment;

public class LoginRegistration extends AppCompatActivity {

    TextView txtLogin,txtSignup,btnLOGIN,btnSIGNUP,txt1;

    RegisterFragment registerFragment = new RegisterFragment();
    LoginFragment loginFragment = new LoginFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_registration);
        txtLogin = findViewById(R.id.txtLogin);
        txtSignup = findViewById(R.id.txtSignup);
        btnLOGIN = findViewById(R.id.txtLOGIN);
        btnSIGNUP = findViewById(R.id.txtSIGNUP);
        txt1 = findViewById(R.id.txt1);
        btnSIGNUP.setVisibility(View.GONE);
        txtLogin.setBackground(ContextCompat.getDrawable(LoginRegistration.this, R.drawable.round_button_blue));
        txtSignup.setBackground(ContextCompat.getDrawable(LoginRegistration.this, R.drawable.round_button_white));
        txt1.setText("LOGIN");


        getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSignup.setBackground(ContextCompat.getDrawable(LoginRegistration.this, R.drawable.round_button_white));
                txtLogin.setBackground(ContextCompat.getDrawable(LoginRegistration.this, R.drawable.round_button_blue));
                btnSIGNUP.setVisibility(View.GONE);
                btnLOGIN.setVisibility(View.VISIBLE);
                txt1.setText("LOGIN");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, registerFragment).commit();

            }
        });
        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSignup.setBackground(ContextCompat.getDrawable(LoginRegistration.this, R.drawable.round_button_blue));
                txtLogin.setBackground(ContextCompat.getDrawable(LoginRegistration.this, R.drawable.round_button_white));
                btnLOGIN.setVisibility(View.GONE);
                btnSIGNUP.setVisibility(View.VISIBLE);
                txt1.setText("SIGNUP");
                getSupportFragmentManager().beginTransaction().replace(R.id.container, loginFragment).commit();

            }
        });

    }
}