package com.cu_dev.graffitimogul;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.ByteBuffer;

public class TagLocationActivity extends AppCompatActivity {

    private static final String TAG = "TAG_LOCATION_ACTIVITY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_location);

        setImageView(extractBitmap());
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
}
