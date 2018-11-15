package fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.spaytconsumer.BookKnow;
import com.spaytconsumer.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutorCompletionService;

import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import common.MarkerInfoWindowAdapter;
import intefaces.BookNowCallBack;
import intefaces.WebApiResponseCallback;
import models.ParkingModel;
import utils.Utils;

/**
 * Created by ashish.kumar on 30-10-2018.
 */

public class Aroundme  extends Fragment  implements View.OnClickListener, WebApiResponseCallback,OnMapReadyCallback, BookNowCallBack {


    AppController controller;

    Dialog progressDailog;
   int selectedView=1;

    GoogleMap gmap_view;
    private MapView map_view;
    Bundle savedInstanceState;
    int apiCall = 0;
   final  int getRecetView = 1;
          final  int getNearBYLocation = 2;
    public static ArrayList<ParkingModel> pakingLocationList = new ArrayList<>();
    int range=10;
    Spinner rangeSelector;
    int []rangeArray={10,20,30,40,50,60,70,80,90,100};
    ImageView viewSwitchIcon;
    ListView listView;
    Button aroudnMe,recentView;
    LinearLayout range_llt;
    TextView heading;
BookNowCallBack callBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.around_me,
                container, false);
        ButterKnife.bind(getActivity());
        controller = (AppController) getActivity().getApplicationContext();
        callBack=this;
        map_view = (MapView) view.findViewById(R.id.map_view);
        rangeSelector=(Spinner) view.findViewById(R.id.range);
        viewSwitchIcon=(ImageView)view.findViewById(R.id.viewSwitchIcon);
        listView=(ListView) view.findViewById(R.id.listView);
        aroudnMe=(Button)view.findViewById(R.id.aroundme);
        recentView=(Button)view.findViewById(R.id.recentView);
       range_llt=(LinearLayout)view.findViewById(R.id.range_llt);
        heading=(TextView)view.findViewById(R.id.heading);
        aroudnMe.setOnClickListener(this);
        recentView.setOnClickListener(this);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Common.googleMapsApiKey);
        }
        map_view.onCreate(mapViewBundle);
        map_view.getMapAsync(this);
        viewSwitchIcon.setOnClickListener(this);
        getData();
        rangeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(range!=rangeArray[i])
                {
                    range=rangeArray[i];
                    getData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    public void getData()
    {
        if (Utils.isNetworkAvailable(getActivity())) {
            apiCall = getNearBYLocation;
            progressDailog = Utils.showPogress(getActivity());
            //  controller.getApiCall().getData(Common.getGetLocationByDistance(controller.getAroundMe().latitude,controller.getAroundMe().longitude,10),Aroundme.this);
            controller.getApiCall().getData("https://api.spayt.de/user/distance_by_km?latitude=52.520006599999995&longitude=13.404954&distance="+range,Aroundme.this);

        }
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
                    final ParkingModel model = pakingLocationList.get(i);
                    MarkerOptions markerOptions = new MarkerOptions();

                    // Setting position for the marker
                    markerOptions.position(new LatLng(Double.parseDouble(model.getLatitude()), Double.parseDouble(model.getLongitude())));

                    int height = 100;
                    int width = 100;
                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.parking);
                    Bitmap b = bitmapdraw.getBitmap();
                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    // Setting custom icon for the marker
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                    // Setting title for the infowindow
                    markerOptions.title(model.getLocation_name());


                    builder.include(markerOptions.getPosition());

                    // Adding the marker to the map

                    Marker marker = gmap_view.addMarker(markerOptions);
                    marker.setTag(i);
                    MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(getActivity());
                    gmap_view.setInfoWindowAdapter(markerInfoWindowAdapter);
                    gmap_view.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                        @Override
                        public void onInfoWindowClick(Marker marker) {
                            BookKnow.model=pakingLocationList.get((Integer)marker.getTag());
                            Intent intent = new Intent(getActivity(), BookKnow.class);
                            startActivity(intent);
                            marker.hideInfoWindow();
                        }

                    });
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
            case R.id.viewSwitchIcon:
                if(selectedView==1)
                {
                    selectedView=2;
                    viewSwitchIcon.setImageResource(R.drawable.map_icon);
                    map_view.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                }else {
                    selectedView=1;
                    viewSwitchIcon.setImageResource(R.drawable.listview_icon);
                    map_view.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
                break;
            case R.id.aroundme:
                getData();
                range_llt.setVisibility(View.VISIBLE);
                heading.setText("");
                recentView.setBackground(getActivity().getResources().getDrawable(R.drawable.right_grey_button));
                aroudnMe.setBackground(getActivity().getResources().getDrawable(R.drawable.left_blue_button));
                recentView.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                aroudnMe.setTextColor(getActivity().getResources().getColor(R.color.white));
                break;
            case R.id.recentView:
                getRecentView();
                range_llt.setVisibility(View.INVISIBLE);
                heading.setText("Kurzlich besucht");
                recentView.setBackground(getActivity().getResources().getDrawable(R.drawable.right_blue_button));
                aroudnMe.setBackground(getActivity().getResources().getDrawable(R.drawable.left_grey_button));
                aroudnMe.setTextColor(getActivity().getResources().getColor(android.R.color.black));
                recentView.setTextColor(getActivity().getResources().getColor(R.color.white));
                break;


        }
    }


    public void getRecentView()
    {
        if (Utils.isNetworkAvailable(getActivity())) {
            apiCall = getRecetView;
            progressDailog = Utils.showPogress(getActivity());
               //controller.getApiCall().postFlormData(Common.getRecentView,controller.getProfile().getUser_id(),Aroundme.this);
            controller.getApiCall().postFlormData(Common.getRecentView,Common.userIdKey,"68",Aroundme.this);

        }
    }



    @Override
    public void onSucess(String value) {
        switch (apiCall) {
            case getNearBYLocation:
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
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(new adapter.ListAdapter(getActivity(), pakingLocationList,  callBack));
                            }
                        });
                        addMarkers();
                    }
                } else {

                    Utils.showToast(getActivity(), "Sorry ! No  places found to your near by location");
                }
                break;
            case getRecetView:
                if(!value.contains("msg"))
                {
                    try {
                        JSONArray jsonArray = new JSONArray(value);
                        pakingLocationList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                pakingLocationList.add(new ParkingModel(jsonArray.getJSONObject(i)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        if (pakingLocationList.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    listView.setAdapter(new adapter.ListAdapter(getActivity(), pakingLocationList,  callBack));
                                }
                            });
                            addMarkers();
                        }
                    }catch (JSONException e)
                    {
                        e.fillInStackTrace();
                    }
                }else{
                    Utils.showToast(getActivity(),"No Recent Views");
                }
                break;
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

    @Override
    public void onBookButtonClick(ParkingModel model) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), BookKnow.class));
            }
        });
    }
}