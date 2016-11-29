package com.cu_dev.graffitimogul;

import android.*;
import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cu_dev.graffitimogul.domain.Tag;
import com.cu_dev.graffitimogul.web.AbstractRequest;
import com.cu_dev.graffitimogul.web.FetchTags;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity  implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,
        TabLayout.OnTabSelectedListener,
        AbstractRequest.RequestCallback<List<Tag>>,
        GetTagsList {

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "HOME_ACTIVITY";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int REQUEST_FETCH_TAGS = 0;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private ViewPager viewPager;
    private HomePagerAdapter mHomePagerAdapter;
    private List<Tag> mTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        startGoogleSignin();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.addTab(tabLayout.newTab().setText("Map"));
//        tabLayout.addTab(tabLayout.newTab().setText("List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        viewPager = (ViewPager) findViewById(R.id.pager);
        mHomePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mHomePagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);

        new FetchTags(REQUEST_FETCH_TAGS, this).execute(new FetchTags.Options());

    }

    private void startGoogleSignin() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signIn();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
            startTagLocationActivity(resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            showSignInStatus(true, acct.getDisplayName());
        } else {
            // Signed out, show unauthenticated UI.
            showSignInStatus(false, null);
        }
    }

    private void showSignInStatus(boolean success, String name) {
        String message = success ? "Thank you for signing in " + name: "Unable to sign in";
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }






    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Google api client connection failed");
    }



    @Override
    public void onClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        switch (v.getId()) {
            case R.id.fab:
                takePicture();
                break;
        }
    }

    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    protected void startTagLocationActivity(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Intent intent = new Intent(this, TagLocationActivity.class);
            intent.putExtras(extras);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Failed to get the image", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        Log.d(TAG, "Tab selected: " + tab.getText().toString());
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Log.d(TAG, "Tab unselected: " + tab.getText().toString());
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.d(TAG, "Tab reselected: " + tab.getText().toString());
        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onRequest(int requestId, List<Tag> tags) {
        Log.d(TAG, "Got request with id: " + requestId);
        switch (requestId) {
            case REQUEST_FETCH_TAGS:
                Log.d(TAG, "updating list of tags");
                mTags = tags;
                mHomePagerAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public List<Tag> getTagList() {
        return (mTags == null) ? new ArrayList<Tag>() : mTags;
    }
}
