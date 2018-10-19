package hottopic.mit.co.nz.cleaningservice.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.model.Marker;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import hottopic.mit.co.nz.cleaningservice.Constants;
import hottopic.mit.co.nz.cleaningservice.R;
import hottopic.mit.co.nz.cleaningservice.adapter.LegRouteAdapter;
import hottopic.mit.co.nz.cleaningservice.entities.LegInfo;
import hottopic.mit.co.nz.cleaningservice.entities.RouteResponse;
import hottopic.mit.co.nz.cleaningservice.utils.GeneralUtil;
import hottopic.mit.co.nz.cleaningservice.utils.MapUtil;
import hottopic.mit.co.nz.cleaningservice.utils.PermissionUtil;

public class MapActivity extends FragmentActivity implements
        GoogleMap.OnMapLoadedCallback,
        RecyclerViewPager.OnPageChangedListener,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener{
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;

    private boolean mPermissionDenied = false;

    private TextView tv_title, tv_num_markers, tv_distance, tv_duration;
    private CardView cv_route_info;
    private RelativeLayout lyt_back, lyt_navigate;
    private RecyclerViewPager rvp_routes;
    private RouteResponse response;

    private int currentLeg = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initView(){
        tv_title = findViewById(R.id.tv_title);
        lyt_back = findViewById(R.id.lyt_back);
        lyt_navigate = findViewById(R.id.lyt_navigate);
        cv_route_info = findViewById(R.id.cv_route_info);
        tv_num_markers = findViewById(R.id.tv_num_markers);
        tv_distance = findViewById(R.id.tv_distance);
        tv_duration = findViewById(R.id.tv_duration);
    }

    private void initData(){
        lyt_navigate.setVisibility(View.VISIBLE);
        tv_title.setText(R.string.title_mapAct);
        lyt_back.setVisibility(View.VISIBLE);

        response = (RouteResponse) getIntent().getSerializableExtra("response");

        rvp_routes = findViewById(R.id.rvp_routes);
        rvp_routes.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL, false));
        LegRouteAdapter adapter = new LegRouteAdapter(this, response.getRoutes().get(0).getLegs());
        rvp_routes.addOnPageChangedListener(this);
        rvp_routes.setAdapter(adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.frg_map);
        mapFragment.getMapAsync(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void initListener(){
        lyt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapActivity.this.finish();
            }
        });
        lyt_navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapUtil.intentToGoogleMap(MapActivity.this, response.getRoutes().get(0));
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        MapUtil.addMarkers(mMap, response.getRoutes().get(0).getLegs());
        MapUtil.drawRoute(mMap, this, response.getRoutes().get(0).getLegs());
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtil.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
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
    public void OnPageChanged(int i, int i1) {
        currentLeg = i1;
        drawLegRoute(i1);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (cv_route_info.getVisibility() == View.VISIBLE){
            cv_route_info.setVisibility(View.GONE);
        }else {
            cv_route_info.setVisibility(View.VISIBLE);
        }
        rvp_routes.setVisibility(View.GONE);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (rvp_routes.getVisibility() == View.VISIBLE){
            drawMarkandRoute();
        }else {
            drawLegRoute(currentLeg);
        }
        cv_route_info.setVisibility(View.GONE);
    }

    private void drawMarkandRoute(){
        mMap.clear();
        MapUtil.addMarkers(mMap, response.getRoutes().get(0).getLegs());
        MapUtil.drawRoute(mMap, this, response.getRoutes().get(0).getLegs());
        rvp_routes.setVisibility(View.GONE);
    }

    private void drawLegRoute(int position){
        mMap.clear();
        MapUtil.addMarkers(mMap, response.getRoutes().get(0).getLegs());
        MapUtil.drawRoute(mMap, this, response.getRoutes().get(0).getLegs(), position);
        rvp_routes.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapLoaded() {
        MapUtil.displayArea(mMap, this, response.getRoutes().get(0).getBounds(), 40);
    }

    private int[] getTotalDistanceAndDuration(){
        int totalDuration = 0;
        int totalDistance = 0;
        for(LegInfo info : response.getRoutes().get(0).getLegs()){
            totalDuration += info.getDuration().getValue();
            totalDistance += info.getDistance().getValue();
        }
        return new int[]{totalDistance, totalDuration};
    }
}
