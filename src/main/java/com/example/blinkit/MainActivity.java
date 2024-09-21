package com.example.blinkit;



import static com.example.blinkit.R.color.black;
import static com.example.blinkit.R.color.white;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.blinkit.logins.AdminLogin;

public class MainActivity extends Activity {

    Button loginButton,singUpButton;
    TextView adminAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loginButton=findViewById(R.id.loginButton);
        singUpButton=findViewById(R.id.signUpButton);
        adminAccess=findViewById(R.id.adminAccess);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                singUpButton.setBackgroundResource(R.drawable.button_2_color);
                singUpButton.setTextColor(getResources().getColor(R.color.black));
                loginButton.setBackgroundResource(R.drawable.empty_button_border);
                loginButton.setTextColor(getResources().getColor(black));
                startActivity(new Intent(getApplication(),com.example.blinkit.logins.LoginActivity.class));
            }
        });

        singUpButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                loginButton.setBackgroundResource(R.drawable.button_2_color);
                loginButton.setTextColor(getResources().getColor(R.color.black));
                singUpButton.setBackgroundResource(R.drawable.empty_button_border);
                singUpButton.setTextColor(getResources().getColor(black));
                startActivity(new Intent(getApplication(),com.example.blinkit.logins.SignUpActivity.class));
            }
        });

        adminAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminLogin.class));
            }
        });

    }
}