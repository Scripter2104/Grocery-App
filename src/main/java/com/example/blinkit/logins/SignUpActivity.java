package com.example.blinkit.logins;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blinkit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etRePassword;

    TextView signUpButton;

    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    ImageView imageViewSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRePassword = findViewById(R.id.etRePassword);
        signUpButton=findViewById(R.id.signUpButton);

        imageViewSignIn = findViewById(R.id.imageViewSignIn);


            imageViewSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!etName.getText().toString().isEmpty()&& !etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty() && !etRePassword.getText().toString().isEmpty()) {

                        databaseReference = FirebaseDatabase.getInstance().getReference("User Data");

                        firebaseAuth = FirebaseAuth.getInstance();
                        String uid = databaseReference.push().getKey();

                        if (etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                            UserData userData = new UserData(uid, etName.getText().toString(), etEmail.getText().toString(), etPassword.getText().toString());
                            assert uid != null;
                            databaseReference.child(uid).setValue(userData);

                            firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        etName.setText("");
                                        etPassword.setText("");
                                        etEmail.setText("");
                                        etRePassword.setText("");

                                        Toast.makeText(getApplicationContext(), "Registration succssefull", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    }

                                }
                            });

                        }
                    }

                }
            });



        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "logged in", Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });


    }
}