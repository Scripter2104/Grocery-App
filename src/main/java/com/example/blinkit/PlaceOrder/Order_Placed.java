package com.example.blinkit.PlaceOrder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.blinkit.R;
import com.example.blinkit.shopping.ShoppingActivity;

public class Order_Placed extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        showOrderPlacedAnimation();


        new Handler().postDelayed(this::shoppingActivity, 2000); // Finish after 2 seconds
    }

    private void shoppingActivity() {
        startActivity(new Intent(getApplicationContext(), ShoppingActivity.class));
    }

    private void showOrderPlacedAnimation() {
        LinearLayout orderPlacedContainer = findViewById(R.id.orderPlacedContainer);
        orderPlacedContainer.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.order_placed);
        orderPlacedContainer.startAnimation(animation);

        // Hide the animation after it completes
        new Handler().postDelayed(() -> orderPlacedContainer.setVisibility(View.GONE), 2000);
    }
}
