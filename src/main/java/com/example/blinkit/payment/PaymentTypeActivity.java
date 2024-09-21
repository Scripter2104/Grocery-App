package com.example.blinkit.payment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blinkit.ActivityTracker.Tracker;
import com.example.blinkit.PlaceOrder.Place_Order;
import com.example.blinkit.R;
import com.example.blinkit.order.OrderActivity;

public class PaymentTypeActivity extends Activity {

    Button checkoutButton,crossButton, backButton;

    RadioButton codButton,upiButton,netBankingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_type);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Tracker.addActivity(this.getClass());

        checkoutButton=findViewById(R.id.checkoutButton);

        codButton=findViewById(R.id.codButton);
        upiButton=findViewById(R.id.upiButton);
        netBankingButton=findViewById(R.id.netBankingButton);
        crossButton = findViewById(R.id.crossButton);
        backButton = findViewById(R.id.backButton);

        upiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codButton.setChecked(false);
                netBankingButton.setChecked(false);
                checkoutButton.setVisibility(View.VISIBLE);
            }
        });

        netBankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codButton.setChecked(false);
                upiButton.setChecked(false);
                checkoutButton.setVisibility(View.VISIBLE);
            }
        });

        codButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upiButton.setChecked(false);
                netBankingButton.setChecked(false);
                checkoutButton.setVisibility(View.VISIBLE);
            }
        });

        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Class<? extends Activity> fourthLastActivity = Tracker.getThirdLastActivity();
                if (fourthLastActivity != null) {
                    Intent intent = new Intent(getApplicationContext(), fourthLastActivity);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not enough activities in the stack", Toast.LENGTH_SHORT).show();
                }
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
                finish();
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Place_Order.class));
            }
        });


    }
    protected void onDestroy() {
        super.onDestroy();
        Tracker.removeActivity(this.getClass());
    }
}