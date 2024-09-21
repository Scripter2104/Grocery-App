package com.example.blinkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShowGrocery extends Activity {


    ListView listView;

    List<GroceryModel> list=new ArrayList<>();
    DatabaseReference databaseReference=null;


    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_grocery);

        listView=findViewById(R.id.listView);

        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        databaseReference=firebaseDatabase.getReference("grocery");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap:snapshot.getChildren()){
                    GroceryModel listModel=snap.getValue(GroceryModel.class);
                    list.add(listModel);
                }
                CustomizedList customizedList=new CustomizedList(getApplicationContext(),R.layout.design,list);
                listView.setAdapter(customizedList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        CustomizedList customizedList=new CustomizedList(this,R.layout.activity_show_grocery,list);

        listView.setAdapter(customizedList);


    }
}