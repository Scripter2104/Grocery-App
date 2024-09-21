package com.example.blinkit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddGrocery extends Activity {

    Button btAdd,btCancle;
    ImageView imageView;

    EditText etGroceryName,etGroceryPrice,etGroceryUnit,etQuantity;

    DatabaseReference databaseReference;
    StorageReference storageReference,groceryStorage;

    Uri imgPath=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        btAdd=findViewById(R.id.btAdd);
        btCancle=findViewById(R.id.btCancle);
        imageView=findViewById(R.id.imageView);
        etGroceryName=findViewById(R.id.etGroceryName);
        etGroceryPrice=findViewById(R.id.etGroceryPrice);
        etGroceryUnit=findViewById(R.id.etGroceryUnit);
        etQuantity=findViewById(R.id.etQuantity);

        btCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference= FirebaseDatabase.getInstance().getReference("grocery");
                storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://blinkit-b0ff5.appspot.com/grocery");

                String name=etGroceryName.getText().toString();

                String gid=databaseReference.push().getKey();
                groceryStorage=storageReference.child(name);

                groceryStorage.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String name=etGroceryName.getText().toString();
                        int price=Integer.parseInt(etGroceryPrice.getText().toString());
                        String unit=etGroceryUnit.getText().toString();
                        int qty=Integer.parseInt(etQuantity.getText().toString());

                        groceryStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                GroceryModel groceryModel=new GroceryModel(gid,name,uri.toString(),price,qty,unit);
                                assert gid != null;
                                databaseReference.child(gid).setValue(groceryModel);
                                Toast.makeText(getApplicationContext(),"Grocery Added",Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Exception"+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {

                    }
                });
            }
        });


    }

    @Override
    public void onActivityResult(int reqCode,int resCode,Intent data) {

        if(reqCode==1 && resCode==RESULT_OK){
            imgPath=data.getData();
            imageView.setImageURI(imgPath);

        }

    }
}