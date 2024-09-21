package com.example.blinkit.order;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blinkit.ActivityTracker.Tracker;
import com.example.blinkit.GlobalMap;
import com.example.blinkit.GroceryModel;
import com.example.blinkit.R;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class OrderActivity extends Activity {

    ListView listView;

    Button checkoutButton;

    TextView discount,saving,subtotal,grandTotal,discountTextView;

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Tracker.addActivity(this.getClass());

        listView=findViewById(R.id.listView);

        checkoutButton=findViewById(R.id.checkoutButton);
        discount=findViewById(R.id.discount);
        saving=findViewById(R.id.saving);
        subtotal=findViewById(R.id.subtotal);
        grandTotal=findViewById(R.id.grandTotal);
        discountTextView=findViewById(R.id.discountTextView);

        Iterator<Map.Entry<Integer, Integer>> it=GlobalMap.orderList.entrySet().iterator();


        Random r2=new Random();
        Random r1=new Random();
        int max=18,min=5;
        int max1=25,min1=10;
        int discountPrice=r1.nextInt(max-min)+min;

        discountTextView.setText("Extra "+discountPrice+"%"+" OFF");

        discount.setText("₹ "+String.valueOf(GlobalMap.totalPrice*discountPrice/100)+"."+String.valueOf(r1.nextInt(10)+String.valueOf(r1.nextInt(10))));

        StringTokenizer stringTokenizer=new StringTokenizer(discount.getText().toString(),"₹ ");

        saving.setText("-₹ "+String.format(String.valueOf(r1.nextInt(max1-min1)+min1+Double.parseDouble(stringTokenizer.nextToken())),"0.2f"));
        stringTokenizer=new StringTokenizer(discount.getText().toString(),"₹ ");
        subtotal.setText("₹ "+String.valueOf(GlobalMap.totalPrice-Double.parseDouble(stringTokenizer.nextToken())));
        stringTokenizer=new StringTokenizer(subtotal.getText().toString(),"₹ ");
        double subtotalPrice=Double.parseDouble(stringTokenizer.nextToken());
        @SuppressLint("DefaultLocale") double decimal=Double.parseDouble(String.format("%.2f", subtotalPrice * 0.18));
        Toast.makeText(this, ""+decimal, Toast.LENGTH_SHORT).show();

        grandTotal.setText(String.format("₹ %.2f/-", decimal+subtotalPrice));

        GlobalMap.finalPrice=decimal+subtotalPrice;



        GlobalMap.orderModel.clear();
        while(it.hasNext()) {
            GroceryModel g = GlobalMap.groceryModel.get(it.next().getKey());
            GlobalMap.orderModel.add(g);
        }

        OrderListActivity orderListActivity=new OrderListActivity(OrderActivity.this,R.layout.design_3,GlobalMap.orderModel);
        listView.setAdapter(orderListActivity);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShippingActivity.class));
                overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
        });



    }
    protected void onDestroy() {
        super.onDestroy();
        Tracker.removeActivity(this.getClass());
    }
}