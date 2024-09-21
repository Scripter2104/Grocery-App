package com.example.blinkit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class CustomizedList extends ArrayAdapter<GroceryModel> {

    Context cont;

    int source;

    //List<GroceryModel> list;

    CustomizedList(Context cont,int source,List<GroceryModel> list){
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

        GroceryModel listModel = GlobalMap.groceryModel.get(position);

        name.setText(String.valueOf(listModel.getGroceryName()));
        price.setText(String.valueOf(listModel.getPrice()));
        unit.setText(String.valueOf(listModel.getUnit()));


        Glide.with(cont)
                .load(listModel.getImgURL())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);

        return view;
    }



}
