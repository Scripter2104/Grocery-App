package com.example.blinkit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Update_Delete extends Activity {

    ListView updateDeleteListView;

    List<GroceryModel> list=new ArrayList<>();

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        updateDeleteListView = findViewById(R.id.updateDeleteListView);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("grocery");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    GroceryModel listModel = snap.getValue(GroceryModel.class);
                    assert listModel != null;
                    //GlobalMap.map.put(listModel.groceryID,listModel.groceryName);
                    list.add(listModel);

                }

               // Toast.makeText(getApplicationContext(), ""+GlobalMap.map.size(), Toast.LENGTH_SHORT).show();
                CustomizedList2 customizedList2 = new CustomizedList2(Update_Delete.this, R.layout.design2, list);
                updateDeleteListView.setAdapter(customizedList2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }
}