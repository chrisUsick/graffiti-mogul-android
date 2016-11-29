package com.cu_dev.graffitimogul;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class TagLocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "TAG_LOCATION_ACTIVITY";
    private static final int PERM_REQUEST_LOCATION = 1;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_location);

        setImageView(extractBitmap());
        mGoogleApiClient = null;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    private void setImageView(Bitmap bitmap) {
        ImageView imageView = (ImageView)findViewById(R.id.image);
        if (bitmap != null) {
            Log.d(TAG, "Loading bitmap: " + bitmap.getByteCount());
            imageView.setImageBitmap(bitmap);
        } else {
            Log.d(TAG, "Bitmap is null");
        }
    }

    private Bitmap extractBitmap() {
        Intent intent = getIntent();
        return (Bitmap)intent.getExtras().get("data");
    }

    public void submitClick(View v) {
        Toast.makeText(this, "Uploading thumbnail", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "Checking Permission");
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERM_REQUEST_LOCATION);
        } else {
            Log.d(TAG, "getting location name");
            Geocoder geocoder = new Geocoder(this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (mLastLocation != null) {
                try {
                    List<Address> addresses =
                            geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(),1);
                    Address address = addresses.get(0);
                    TextView nameView = (TextView)findViewById(R.id.name);
                    if (address != null) {
                        Log.d(TAG, "Found Address: " + address.toString());
                        nameView.setText(address.getAddressLine(0));
                    } else {
                        nameView.setText("Lat Lon: + " + mLastLocation.getLatitude() + ", " + mLastLocation.getLongitude());
                    }
                } catch (IOException e) {
                    Log.d(TAG, "IO Exception in get geocode", e);
                }

            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Connection to google api builder failed");
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }
}
