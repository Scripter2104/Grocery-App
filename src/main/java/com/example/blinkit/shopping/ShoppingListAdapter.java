package com.example.blinkit.shopping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.blinkit.GlobalMap;
import com.example.blinkit.GroceryModel;
import com.example.blinkit.R;

public class ShoppingListAdapter extends ArrayAdapter<GroceryModel> {

    Context cont;

    int source;




    ShoppingListAdapter(Context cont, int source) {
        super(cont, source, GlobalMap.groceryModel);
        this.cont = cont;
        this.source = source;


    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(cont);

        @SuppressLint("ViewHolder") View view = layoutInflater.inflate(source, null, false);


        ImageView imageView = view.findViewById(R.id.imageView);
        TextView name = view.findViewById(R.id.tvGroceryName);
        TextView price = view.findViewById(R.id.tvGroceryPrice);
        TextView unit = view.findViewById(R.id.tvUnit);
        Button add = view.findViewById(R.id.btAdd);
        Button subtract = view.findViewById(R.id.btSubtract);
        EditText quantity = view.findViewById(R.id.quantity);


        GroceryModel listModel = GlobalMap.groceryModel.get(position);

        quantity.setText("0");


        name.setText(String.valueOf(listModel.getGroceryName()));
        price.setText(String.valueOf(listModel.getPrice()));
        unit.setText(String.valueOf(listModel.getUnit()));


        Glide.with(cont)
                .load(listModel.getImgURL())
                .override(800, 400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevQty = listModel.qty;
                quantity.setText(String.valueOf(++listModel.qty));
                int previous = GlobalMap.totalPrice;
                int diff = listModel.qty - prevQty;
                GlobalMap.totalPrice = previous + diff * listModel.getPrice();
                ShoppingActivity.updateTotalBill(GlobalMap.totalPrice);
                GlobalMap.orderList.put(position, listModel.qty);
                GlobalMap.itemSelected++;

            }
        });

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listModel.qty > 0) {
                    int prevQty = listModel.qty;
                    quantity.setText(String.valueOf(--listModel.qty));
                    int previous = GlobalMap.totalPrice;
                    int diff = prevQty - listModel.qty;
                    GlobalMap.totalPrice = previous - diff * listModel.getPrice();
                    ShoppingActivity.updateTotalBill(GlobalMap.totalPrice);
                    if(listModel.qty!=0) {
                        GlobalMap.orderList.put(position, listModel.qty);
                    }
                    else{
                        GlobalMap.orderList.remove(position);
                        GlobalMap.itemSelected--;
                    }
                }
            }
        });


        return view;
    }
}
