package com.example.blinkit.payment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.blinkit.ActivityTracker.Tracker;
import com.example.blinkit.GlobalMap;
import com.example.blinkit.R;
import com.example.blinkit.order.OrderActivity;

public class PaymentActivity extends Activity {

    TextView addressTextView,smsUpdate;
    Button crossButton, backButton,checkoutButton;

    CheckBox smsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Tracker.addActivity(this.getClass());

        addressTextView = findViewById(R.id.addressTextView);
        crossButton = findViewById(R.id.crossButton);
        backButton = findViewById(R.id.backButton);
        smsUpdate=findViewById(R.id.smsUpdate);
        smsCheckBox=findViewById(R.id.smsCheckBox);
        checkoutButton=findViewById(R.id.checkoutButton);


        addressTextView.setTextSize(11);
        addressTextView.setText(GlobalMap.addressDefault);
        addressTextView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);




        smsCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smsCheckBox.isChecked()){
                    smsUpdate.setTypeface(null, Typeface.BOLD);
                }
                else{
                    smsUpdate.setTypeface(null,Typeface.NORMAL);
                }
            }
        });



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
                startActivity(new Intent(getApplicationContext(), PaymentTypeActivity.class));
            }
        });





    }
    protected void onDestroy() {
        super.onDestroy();
        Tracker.removeActivity(this.getClass());
    }
}