package com.example.blinkit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Update_Grocery extends Activity {

    Button btUpdate, btCancle;
    ImageView imageView;

    EditText etGroceryName, etGroceryPrice, etGroceryUnit, etQuantity;

    int position;
    int flag = 0;

    DatabaseReference databaseReference;

    StorageReference firebaseStorage, groceryStorage;

    Uri imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grocery);

        btUpdate = findViewById(R.id.btUpdate);
        btCancle = findViewById(R.id.btCancle);
        imageView = findViewById(R.id.imageView);
        etGroceryName = findViewById(R.id.etGroceryName);
        etGroceryPrice = findViewById(R.id.etGroceryPrice);
        etGroceryUnit = findViewById(R.id.etGroceryUnit);
        etQuantity = findViewById(R.id.etQuantity);

        Intent intent = getIntent();

        position = intent.getIntExtra("position", 0);

        GroceryModel groceryModel = GlobalMap.groceryModel.get(position);

        etGroceryName.setText(groceryModel.getGroceryName());
        etGroceryPrice.setText(String.valueOf(groceryModel.getPrice()));
        etGroceryUnit.setText(String.valueOf(groceryModel.getUnit()));
        etQuantity.setText(String.valueOf(groceryModel.getQuantity()));
        String imgURI = groceryModel.getImgURL();

        Glide.with(this)
                .load(imgURI)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent1, 1);
            }
        });


        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUpdate(position);

            }


        });

        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {

        if (reqCode == 1 && resCode == RESULT_OK) {
            imgPath = data.getData();
            imageView.setImageURI(imgPath);
            flag = 1;

        }

    }

    public void onUpdate(int position) {
        databaseReference = FirebaseDatabase.getInstance().getReference("grocery");
        String name = etGroceryName.getText().toString();
        int price = Integer.parseInt(etGroceryPrice.getText().toString());
        String unit = etGroceryUnit.getText().toString();
        int quantity = Integer.parseInt(etQuantity.getText().toString());


        GroceryModel groceryModel = GlobalMap.groceryModel.get(position);

        if (flag == 0) {
            imgPath = Uri.parse(groceryModel.getImgURL());
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(groceryModel.getImgURL());

        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        });

      
        databaseReference = FirebaseDatabase.getInstance().getReference("grocery");


        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://blinkit-b0ff5.appspot.com/grocery");

        groceryStorage = storageReference.child(name);

        groceryStorage.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                groceryStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        GroceryModel tempG = new GroceryModel(groceryModel.getGroceryID(), name, uri.toString(), price, quantity, unit);
                        GlobalMap.groceryModel.set(position, tempG);
                        databaseReference.child(groceryModel.getGroceryID()).setValue(tempG);
                        Toast.makeText(getApplicationContext(), "Grocery Added", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Exception" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


    }
}