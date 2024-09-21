package com.example.blinkit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CustomizedList2 extends ArrayAdapter<GroceryModel> {

    Context cont;

    int source;

    String imgUrl;

  //  List<GroceryModel> list;

    String gid="";

    CustomizedList2(Context cont,int source,List<GroceryModel> list){
        super(cont,source,list);

        this.cont=cont;
        this.source=source;
        GlobalMap.groceryModel=list;
    }

    @NonNull
    public View getView(final int position, View convetView, @NonNull ViewGroup parent){


        LayoutInflater inflater=LayoutInflater.from(cont);

        @SuppressLint("ViewHolder") View view=inflater.inflate(source,null,false);

        ImageView iv=view.findViewById(R.id.imageView);
        TextView name=view.findViewById(R.id.tvGroceryName);
        TextView price=view.findViewById(R.id.tvGroceryPrice);
        TextView unit=view.findViewById(R.id.tvUnit);
        Button btUpdate=view.findViewById(R.id.btUpdate);
        Button btDelete=view.findViewById(R.id.btDelete);




        GroceryModel listModel = GlobalMap.groceryModel.get(position);
        gid=listModel.groceryID;
        imgUrl=listModel.imgURL;

        name.setText(String.valueOf(listModel.getGroceryName()));
        price.setText(String.valueOf(listModel.getPrice()));
        unit.setText(String.valueOf(listModel.getUnit()));


        Glide.with(cont)
                .load(listModel.getImgURL())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);

            }
        });


        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(cont.getApplicationContext(),Update_Grocery.class);
                intent.putExtra("position",position);
                cont.startActivity(intent);

            }
        });

        return view;
    }

    void showDialog(int position)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(cont);
        alert.setTitle("ARE YOU SURE YOU WANT TO DELETE ?");

        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GlobalMap.groceryModel.remove(position);
                onDelete();
                notifyDataSetChanged();     // It actually removes from the adapter
                Toast.makeText(cont, "RECORD DELETED !!!", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog al = alert.create();
        al.show();

    }

    public void onDelete(){

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl(imgUrl);



        Query query=databaseReference.child("grocery").orderByChild("groceryID").equalTo(gid);



        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    snapshot1.getRef().removeValue();

                    Toast.makeText(cont, "hello", Toast.LENGTH_SHORT).show();
                }

                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
