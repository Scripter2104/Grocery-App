package com.example.blinkit.shopping;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.blinkit.ActivityTracker.Tracker;
import com.example.blinkit.GlobalMap;
import com.example.blinkit.GroceryModel;
import com.example.blinkit.R;
import com.example.blinkit.order.OrderActivity;
import com.example.blinkit.order.OrderListActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ShoppingActivity extends Activity {

    @SuppressLint("StaticFieldLeak")
    static EditText totalBill;
    ListView shoppingListView;

    ImageView proceedButton;

    //  List<GroceryModel> list=new ArrayList<GroceryModel>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Tracker.addActivity(this.getClass());



        shoppingListView = findViewById(R.id.shoppinglistView);

        totalBill = findViewById(R.id.etTotalPrice);

        proceedButton=findViewById(R.id.proceedButton);

        totalBill.setText("₹ " +0+ "/-");

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("grocery");

        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GlobalMap.groceryModel.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    GroceryModel groceryModel = snap.getValue(GroceryModel.class);
                    assert groceryModel != null;
                    groceryModel.qty=0;
                    GlobalMap.groceryModel.add(groceryModel);
                }

               // Toast.makeText(getApplicationContext(), "" + GlobalMap.groceryModel.size(), Toast.LENGTH_LONG).show();
                ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(ShoppingActivity.this, R.layout.design_3);
                shoppingListView.setAdapter(shoppingListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalMap.itemSelected!=0) {
                    startActivity(new Intent(getApplicationContext(), OrderActivity.class));

                }
                else{
                    Toast.makeText(ShoppingActivity.this, "Select an item", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    protected void onDestroy() {
        super.onDestroy();
        Tracker.removeActivity(this.getClass());
    }

    @SuppressLint("SetTextI18n")
    static public void updateTotalBill(int price) {
        totalBill.setText("₹ " + price + "/-");
    }
}