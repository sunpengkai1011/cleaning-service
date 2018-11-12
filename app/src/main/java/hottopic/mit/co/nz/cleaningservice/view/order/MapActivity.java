package hottopic.mit.co.nz.cleaningservice.view.order;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import hottopic.mit.co.nz.cleaningservice.BaseActivity;
import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.entities.network.response.PlaceResponse;
import hottopic.mit.co.nz.cleaningservice.presenter.order.MapPresenterImpl;
import hottopic.mit.co.nz.cleaningservice.utils.MapUtil;
import hottopic.mit.co.nz.cleaningservice.utils.PermissionUtil;

public class MapActivity extends BaseActivity implements
        IMapView, GoogleMap.OnMapLoadedCallback, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener{
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    private boolean mPermissionDenied = false;

    private TextView tv_title, tv_location_address;
    private RelativeLayout lyt_back, lyt_right;
    private String location;

    private MapPresenterImpl mapPresenter;
    private Animation animation_in, animation_out;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView(){
        setContentView(R.layout.activity_map);
        tv_title = findViewById(R.id.tv_title);
        tv_location_address = findViewById(R.id.tv_location_address);
        lyt_back = findViewById(R.id.lyt_back);
        lyt_right = findViewById(R.id.lyt_right);
    }

    @Override
    public void initData(){
        lyt_right.setVisibility(View.VISIBLE);
        tv_title.setText("MAP");
        lyt_back.setVisibility(View.VISIBLE);

        animation_in = AnimationUtils.loadAnimation(this, R.anim.anim_view_in);
        animation_out = AnimationUtils.loadAnimation(this, R.anim.anim_view_out);

        mapPresenter = new MapPresenterImpl(this, this);
        location = getIntent().getStringExtra(Constants.KEY_INTENT_LOCATION);
        mapPresenter.placeRequest(location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frg_map);
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void initListener(){
        lyt_back.setOnClickListener(this);
        lyt_right.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Init the Google Map and set the listeners.
        mMap = googleMap;
        enableMyLocation();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lyt_back:
                this.finish();
                break;
            case R.id.lyt_right:
                new AlertDialog.Builder(this).setMessage("Do you want to open the Google Maps to navigation?").
                        setPositiveButton("Sure", (dialogInterface, i) -> {
                            MapUtil.intentToGoogleMap(MapActivity.this, location);
                        }).setNegativeButton("Cancel", (dialogInterface, i) -> {
                        }).show();
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (View.GONE == tv_location_address.getVisibility()) {
            animation_in.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                        tv_location_address.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            tv_location_address.startAnimation(animation_in);
        }else{
            animation_out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    tv_location_address.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            tv_location_address.startAnimation(animation_out);
        }
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtil.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtil.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtil.requestPermission(MapActivity.this, Constants.LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void placeResponse(PlaceResponse response) {
        tv_location_address.setText(response.getCandidates().get(0).getFormatted_address());
        MapUtil.addMark(mMap, response.getCandidates().get(0));
        MapUtil.displayArea(mMap, this, response.getCandidates().get(0).getGeometry().getViewport(), 10);
    }

    @Override
    public void placeResponseError() {
        Toast.makeText(this, "The location queries failed", Toast.LENGTH_SHORT).show();
    }
}
