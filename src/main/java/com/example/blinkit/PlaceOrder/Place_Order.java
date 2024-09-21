package com.example.blinkit.PlaceOrder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.blinkit.ActivityTracker.Tracker;
import com.example.blinkit.GlobalMap;
import com.example.blinkit.R;
import com.example.blinkit.order.OrderActivity;

public class Place_Order extends Activity {

    TextView shippingAddress,billingAddress,paymentPrice,contactInfo;
    Button backButton,crossButton,checkoutButton;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_place_order);
        Tracker.addActivity(this.getClass());

        shippingAddress=findViewById(R.id.shippingAddress);
        billingAddress=findViewById(R.id.billingAddress);
        paymentPrice=findViewById(R.id.paymentPrice);
        crossButton = findViewById(R.id.crossButton);
        backButton = findViewById(R.id.backButton);
        contactInfo=findViewById(R.id.contactInfo);
        checkoutButton=findViewById(R.id.checkoutButton);

        shippingAddress.setTextSize(11);
        shippingAddress.setText(GlobalMap.addressDefault);

        billingAddress.setTextSize(11);
        billingAddress.setText(GlobalMap.addressDefault);

        contactInfo.setTextSize(11);
        contactInfo.setText(GlobalMap.email);

        paymentPrice.setText(String.format("₹ %.2f/-", GlobalMap.finalPrice+49));


        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(getApplicationContext(), OrderActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(i);
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
                startActivity(new Intent(getApplicationContext(),Order_Placed.class));
            }
        });

    }
    protected void onDestroy() {
        super.onDestroy();
        Tracker.removeActivity(this.getClass());
    }
}