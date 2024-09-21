package com.example.blinkit.logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blinkit.GlobalMap;
import com.example.blinkit.R;
import com.example.blinkit.shopping.ShoppingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {

    ImageView imageViewSignIn;

    TextView etEmail,etPassword,registerButton;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        imageViewSignIn=findViewById(R.id.imageViewSignIn);
        registerButton=findViewById(R.id.registerButton);

        firebaseAuth=FirebaseAuth.getInstance();

        //if(!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {

            imageViewSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                    firebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "logged in", Toast.LENGTH_LONG).show();
                                GlobalMap.email=etEmail.getText().toString();
                                startActivity(new Intent(getApplicationContext(), ShoppingActivity.class));
                            }

                        }
                    });
                }
                    }
            });
        //}

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });


    }
}