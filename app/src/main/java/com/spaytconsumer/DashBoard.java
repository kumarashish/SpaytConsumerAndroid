package com.spaytconsumer;

import android.*;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import fragments.Account;
import fragments.Aroundme;
import fragments.Home;
import fragments.Loyality;
import fragments.Offers;
import models.LocationDetails;
import models.OrderDetailsModel;
import models.UserTimers;
import okhttp3.internal.Util;
import utils.Utils;

public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener, BottomNavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    AppController controller;
    @BindView(R.id.dashdoard)
    View content;
    Button logout;
    FrameLayout frameLayout;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private static final String TAG = "LocationActivity";
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    Dialog progressDialog;
    Fragment currentFragment;
    final int permission = 2;
    private LocationManager mlocManager;
    Spinner rangeSelector;
    int []rangeArray={10,20,30,40,50,60,70,80,90,100};

    @BindView(R.id.navigation)
    android.support.design.widget.BottomNavigationView bottomView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        ButterKnife.bind(this);
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) &&(Build.VERSION.SDK_INT <26)) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }else if(Build.VERSION.SDK_INT >=26){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)bottomView.getLayoutParams();
            params.bottomMargin=120;
        }

        controller = (AppController) getApplicationContext();
        frameLayout=(FrameLayout)content.findViewById(R.id.frame) ;
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                if(Utils.isNetworkAvailable(DashBoard.this))
                {
            final String response= controller.getApiCall().postFlormData(Common.isTimerStartedUrl,controller.getProfile().getUser_id());
              //  final String response= controller.getApiCall().postFlormData(Common.isTimerStartedUrl,"68");
                    final boolean[] status=Utils.isTimerStarted(response);

                if(status.length>0) {
                    if (status[0] == true) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (status[1] == true) {
                                    if (status[2] == false) {
                                        Intent in = new Intent(DashBoard.this,PaymentPage.class);
                                        PaymentPage.locationDetails = new LocationDetails(Utils.getLocationDetails(response));
                                        PaymentPage.timers = new UserTimers(Utils.getTimers(response));
                                        in.putExtra("pendingamount", Utils.getTotalParkingAmount(response));
                                        startActivity(in);
                                        finish();
                                        /////navigate to payment page

                                    }
                                } else {
                                    /////navigate to payment page running timer
                                    Intent in = new Intent(DashBoard.this, TimerClass.class);
                                    TimerClass.timers = new UserTimers(Utils.getTimers(response));
                                    startActivity(in);
                                }
                            }

                        });

                    }
                }
                Log.d("response",response);
                }

            }
        });
        t.start();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
                layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
                // set your width here
                layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
        loadfragment(1);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mGoogleApiClient = new GoogleApiClient.Builder(DashBoard.this)
                .addApi(Places.GEO_DATA_API)
                .addApi(LocationServices.API)
                .enableAutoManage(DashBoard.this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        createLocationRequest();
        mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            fetchCurrentLocation();
        } else {
            showGpsDisabledAlert();
        }
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    protected void stopLocationUpdates() {
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) DashBoard.this);
            Log.d(TAG, "Location update stopped .......................");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }
    private  void showGpsDisabledAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, Do you want to   enable it to  ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),22);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        finish();

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            String address = utils.Utils.getCompleteAddressString(DashBoard.this, location.getLatitude(), location.getLongitude());
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            controller.setAddress(address, loc);
            controller.setAroundME(loc);
            stopLocationUpdates();
            updateScreen();
            if( progressDialog!=null) {
                progressDialog.cancel();
            }



        }
    }

    public void updateScreen() {
        if (currentFragment != null) {
            if (controller.getCurrentLocation() != null) {
                ((Home) currentFragment).setCityName( controller.getAddress());
            }
        }
    }
    public void fetchCurrentLocation()
    {
        if (ActivityCompat.checkSelfPermission(DashBoard.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashBoard.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(DashBoard.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    permission);
        }
        progressDialog = Utils.showPogress(DashBoard.this);
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {

                // This method will be executed once the timer is over
                // Start your app main activity
                if (mGoogleApiClient.isConnected()) {
                    if (ActivityCompat.checkSelfPermission(DashBoard.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DashBoard.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions(DashBoard.this,
                                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                                permission);
                        progressDialog.cancel();

                        return;
                    }


                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,DashBoard.this);
                }

            }
        }, 2000);
    }

    public void loadfragment(int value) {
        Fragment fragmentA = null;
        if (fragmentA == null) {
            switch (value) {
                case 1:
                    fragmentA = new Home();
                    currentFragment = fragmentA;

                    break;
                case 2:
                    fragmentA = new Offers();
                    currentFragment = null;


                    break;
                case 3:
                    fragmentA = new Aroundme();
                    currentFragment = null;

                    break;
                case 4:
                    fragmentA = new Loyality();
                    currentFragment =null;

                    break;
                case 5:
                    fragmentA = new Account();
                    currentFragment = null;

                    break;
            }
        }

        android.app.FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragmentA);
        fragmentTransaction.commit();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                loadfragment(1);
                return true;
            case R.id.navigation_offers:
                loadfragment(2);
                return true;
            case R.id.navigation_around_me:
                loadfragment(3);
                return true;
            case R.id.navigation_Loyality:
                loadfragment(4);
                return true;
            case R.id.navigation_account:
                loadfragment(5);
                return true;
        }
        return false;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        Log.i(TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.e(TAG, "Google Places API connection suspended.");
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.e(TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permission: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient.isConnected()) {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, DashBoard.this);
                    }else{

                    }

                } else {

                    Toast.makeText(DashBoard.this,"Please provide permission to read current location.",Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }

    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT < 16) {
            // Hide the status bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // Hide the action bar
            getSupportActionBar().hide();
        } else {
            // Hide the status bar
            //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            //Hide the action bar
            getSupportActionBar().hide();
                Window w = getWindow(); // in Activity's onCreate() for instance
                w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
    }
}
