package com.example.blinkit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class AdminAccess extends Activity {

    Button btAddGrocery,btShowGrocery,btUpdateDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_acess);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btAddGrocery = findViewById(R.id.btAddGrocery);
        btShowGrocery=findViewById(R.id.btShowGrocery);
        btUpdateDelete=findViewById(R.id.btUpdateDelete);

        btAddGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddGrocery.class));
            }
        });



        btShowGrocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(), ShowGrocery.class));

            }
        });

        btUpdateDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), Update_Delete.class));
            }
        });



    }
}