package com.example.blinkit.order;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.example.blinkit.shopping.ShoppingActivity;

import java.util.List;

public class OrderListActivity extends ArrayAdapter<GroceryModel> {

    private final Context context;
    private final int resource;
    private final List<GroceryModel> groceryList;

    public OrderListActivity(Activity context, int resource, List<GroceryModel> groceryList) {
        super(context, resource, GlobalMap.orderModel);
        this.context = context;
        this.groceryList = groceryList;
        this.resource = resource;
    }

    @SuppressLint("ViewHolder")
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView name = convertView.findViewById(R.id.tvGroceryName);
        TextView price = convertView.findViewById(R.id.tvGroceryPrice);
        TextView unit = convertView.findViewById(R.id.tvUnit);
        Button add = convertView.findViewById(R.id.btAdd);
        Button subtract = convertView.findViewById(R.id.btSubtract);
        EditText quantity = convertView.findViewById(R.id.quantity);

        GroceryModel groceryModel = GlobalMap.orderModel.get(position);

        quantity.setText(String.valueOf(groceryModel.qty));
        name.setText(groceryModel.getGroceryName());
        price.setText(String.valueOf(groceryModel.getPrice()));
        unit.setText(groceryModel.getUnit());

        Glide.with(context)
                .load(groceryModel.getImgURL())
                .override(800, 400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        add.setOnClickListener(v -> {
            int prevQty = groceryModel.qty;
            groceryModel.qty++;
            quantity.setText(String.valueOf(groceryModel.qty));
            int previous = GlobalMap.totalPrice;
            int diff = groceryModel.qty - prevQty;
            GlobalMap.totalPrice = previous + diff * groceryModel.getPrice();
            ShoppingActivity.updateTotalBill(GlobalMap.totalPrice);
            GlobalMap.orderList.put(position, groceryModel.qty);
            notifyDataSetChanged();
        });

        subtract.setOnClickListener(v -> {
            if (groceryModel.qty > 0 && !groceryList.isEmpty()) {
                int prevQty = groceryModel.qty;
                groceryModel.qty--;
                quantity.setText(String.valueOf(groceryModel.qty));
                int previous = GlobalMap.totalPrice;
                int diff = prevQty - groceryModel.qty;
                GlobalMap.totalPrice = previous - diff * groceryModel.getPrice();
                ShoppingActivity.updateTotalBill(GlobalMap.totalPrice);
                if (groceryModel.qty != 0) {
                    GlobalMap.orderList.put(position, groceryModel.qty);
                } else {
                    GlobalMap.orderList.remove(position);
                    groceryList.remove(position);
                    notifyDataSetChanged();
                }
            }
            if (groceryList.isEmpty()) {
                ((Activity) context).finish();
            }
        });

        return convertView;
    }
}
