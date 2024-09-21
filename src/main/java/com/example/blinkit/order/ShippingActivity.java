package com.example.blinkit.order;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.blinkit.ActivityTracker.Tracker;
import com.example.blinkit.GlobalMap;
import com.example.blinkit.R;
import com.example.blinkit.payment.PaymentActivity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ShippingActivity extends Activity implements LocationListener {

    public static Address address;
    Button crossButton, currentLocationButton, checkOutButton;
    TextView liveLocationTextView, defaultAddress;
    Location location;
    LocationManager locationManager;
    boolean isNet, isGps;
    double latitude = 0, longitude = 0;
    Geocoder geocoder;
    RadioButton defaultRadioButton, liveLocationRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Tracker.addActivity(this.getClass());

        crossButton = findViewById(R.id.crossButton);
        currentLocationButton = findViewById(R.id.currentLocationButton);
        liveLocationTextView = findViewById(R.id.liveLocationTextView);
        defaultRadioButton = findViewById(R.id.defaultRadioButton);
        liveLocationRadioButton = findViewById(R.id.liveLocationRadioButton);
        checkOutButton = findViewById(R.id.checkoutButton);
        defaultAddress = findViewById(R.id.defaultAddress);


        crossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        isNet = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);


        currentLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();

                try {
                    getAddress();
                    liveLocationRadioButton.setVisibility(View.VISIBLE);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        defaultRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveLocationRadioButton.setChecked(false);
            }
        });

        liveLocationRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultRadioButton.setChecked(false);
            }
        });

        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultRadioButton.isChecked() && !liveLocationRadioButton.isChecked() || !defaultRadioButton.isChecked() && liveLocationRadioButton.isChecked()) {
                    if (defaultRadioButton.isChecked() && !liveLocationRadioButton.isChecked()) {
                        GlobalMap.addressDefault = defaultAddress.getText().toString();
                    } else {
                        GlobalMap.addressDefault = address.getAddressLine(0);
                    }

                    startActivity(new Intent(getApplicationContext(), PaymentActivity.class));


                } else {
                    Toast.makeText(ShippingActivity.this, "Select Address", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    public void getLocation() {

        if (isNet) {


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 10, this);

            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }

            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

        }

    }

    public void getAddress() throws IOException {
        geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> list = geocoder.getFromLocation(latitude, longitude, 1);
        assert list != null;
        address = list.get(0);
        liveLocationTextView.setPadding(60, 0, 0, 0);
        liveLocationTextView.setTextSize(11);
        // StringTokenizer st=new StringTokenizer(address.getAddressLine(0),address.getSubLocality());
        liveLocationTextView.setText(address.getAddressLine(0));

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    protected void onDestroy() {
        super.onDestroy();
        Tracker.removeActivity(this.getClass());





    }
}