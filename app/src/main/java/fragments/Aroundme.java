package fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spaytconsumer.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import common.LocationSearch;
import intefaces.WebApiResponseCallback;
import models.CategoryModel;
import models.ParkingModel;
import utils.Utils;

/**
 * Created by ashish.kumar on 30-10-2018.
 */

public class Aroundme  extends Fragment  implements View.OnClickListener, WebApiResponseCallback,OnMapReadyCallback {


    AppController controller;

    Dialog progressDailog;


    GoogleMap gmap_view;
    private MapView map_view;
    Bundle savedInstanceState;
    int apiCall = 0;
    int getCategory = 1, getNearBYLocation = 2;
    ArrayList<ParkingModel> pakingLocationList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.around_me,
                container, false);
        ButterKnife.bind(getActivity());
        controller = (AppController) getActivity().getApplicationContext();
        map_view = (MapView) view.findViewById(R.id.map_view);


        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Common.googleMapsApiKey);
        }
        map_view.onCreate(mapViewBundle);
        map_view.getMapAsync(this);
        if (Utils.isNetworkAvailable(getActivity())) {
            apiCall = getNearBYLocation;
            progressDailog = Utils.showPogress(getActivity());
            //  controller.getApiCall().getData(Common.getGetLocationByDistance(controller.getAroundMe().latitude,controller.getAroundMe().longitude,10),Aroundme.this);
            controller.getApiCall().getData("https://api.spayt.de/user/distance_by_km?latitude=52.520006599999995&longitude=13.404954&distance=10",Aroundme.this);

        }

        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(Common.googleMapsApiKey);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(Common.googleMapsApiKey, mapViewBundle);
        }

        map_view.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();

        map_view.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

        map_view.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

        map_view.onStop();

    }

    @Override
    public void onPause() {

        map_view.onPause();

        super.onPause();
    }

    @Override
    public void onDestroy() {
        map_view.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        map_view.onLowMemory();

    }


    public void addMarkers() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LatLngBounds bounds;
                gmap_view.clear();
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (int i = 0; i < pakingLocationList.size(); i++) {
                    ParkingModel model = pakingLocationList.get(i);
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting position for the marker
                    markerOptions.position(new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongitude())));

                    // Setting custom icon for the marker
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.parking));

                    // Setting title for the infowindow
                    markerOptions.title(model.getLocation_name());
                    builder.include(markerOptions.getPosition());
                    // Adding the marker to the map
                    gmap_view.addMarker(markerOptions);
                }
                if (pakingLocationList.size() > 1) {
                    try {
                        int width = getResources().getDisplayMetrics().widthPixels;
                        int height = map_view.getHeight();
                        int padding = (int) (width * 0.12);
                        gmap_view.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), width, height, padding));
                        //  gmap_view.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 15));
                    } catch (Exception ex) {
                        ex.fillInStackTrace();
                        Log.d("error", ex.fillInStackTrace().toString());
                    }
                } else {
                    float zoomLevel = (float) 15.0;
                    LatLng latLng = new LatLng(Double.parseDouble(pakingLocationList.get(0).getLatitude()), Double.parseDouble(pakingLocationList.get(0).getLongitude()));
                    gmap_view.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));

                }
            }
        });
    }
    //gmap_view.moveCamera(CameraUpdateFactory.newLatLng(controller.getCurrentLocation()));
//        gmap_view.animateCamera(CameraUpdateFactory.newLatLngBounds(builder .build(), 100));

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    @Override
    public void onSucess(String value) {

        if (utils.Utils.getLocationsArray(value).length() > 0) {
            pakingLocationList.clear();
            JSONArray jsonArray = utils.Utils.getLocationsArray(value);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    pakingLocationList.add(new ParkingModel(jsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (pakingLocationList.size() > 0) {
                addMarkers();
            }
        } else {
            Utils.showToast(getActivity(), "Sorry ! No  places found to your near by location");
        }


        if (progressDailog != null) {
            progressDailog.cancel();
        }
    }

    @Override
    public void onError(String value) {
        utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        if (progressDailog != null) {
            progressDailog.cancel();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap_view = googleMap;
//        gmap_view.setMinZoomPreference(12);
//        LatLng ny = new LatLng(40.7143528, -74.0059731);
//        gmap_view.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}