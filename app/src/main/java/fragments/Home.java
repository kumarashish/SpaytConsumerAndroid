package fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.spaytconsumer.R;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import common.AppController;
import common.Common;
import common.LocationSearch;
import intefaces.WebApiResponseCallback;
import models.CategoryModel;
import utils.Utils;

/**
 * Created by ashish.kumar on 30-10-2018.
 */

public class Home  extends Fragment implements View.OnClickListener, WebApiResponseCallback,OnMapReadyCallback {

    Button logout;
    AppController controller;
    TextView city,category;
    Dialog progressDailog;
    CategoryModel model=null;
    View view1;
    View view2;
    GoogleMap gmap_view;
    private MapView map_view;
    Bundle savedInstanceState;
    Button back;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,
                container, false);
        ButterKnife.bind(getActivity());
        controller = (AppController) getActivity().getApplicationContext();
        logout=(Button)view.findViewById(R.id.logout);
        city=(TextView) view.findViewById(R.id.location_name);
        category=(TextView)view.findViewById(R.id.category);
        view1=(LinearLayout)view.findViewById(R.id.view1);
        view2=(LinearLayout)view.findViewById(R.id.view2);
        back=(Button) view.findViewById(R.id.back);
        map_view=(MapView)view.findViewById(R.id.map_view);
        if(controller.getAddress().length()>0) {
            city.setText(controller.getAddress());
            getBusinessLocations();

        }
        logout.setOnClickListener(this);
        city.setOnClickListener(this);
        category.setOnClickListener(this);
        back.setOnClickListener(this);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Common.googleMapsApiKey);
        }
        map_view.onCreate(mapViewBundle);
        map_view.getMapAsync(this);
        return view;
    }

    public void getBusinessLocations()
    {
        if(model==null) {
            progressDailog = Utils.showPogress(getActivity());
            controller.getApiCall().getData(Common.getCategories, this);
        }else {
            category.setText(model.getCategoryName());
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

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.logout:
                controller.getPrefManager().setUserLoggedIn(false);
                Utils.Logout(getActivity());
                break;

            case R.id.location_name:
                startActivityForResult(new Intent(getActivity(), LocationSearch.class),22);
                break;

            case R.id.category:

                view1.setVisibility(View.GONE);
                view2.setVisibility(View.VISIBLE);


                break;
            case R.id.back:

                view2.setVisibility(View.GONE);
                view1.setVisibility(View.VISIBLE);


                break;
        }
    }

    public void setCityName(String name) {
        if (city != null) {
            city.setText(name);
            getBusinessLocations();
        }
    }

    public void getData(String lat, String lon) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 22) && (resultCode == -1)) {
            city.setText(controller.getAddress());
        }
    }

    @Override
    public void onSucess(String value) {
        if(value.length()>0)
        {JSONArray jsonArray=Utils.getJSonArray(value);
        try {
            model = new CategoryModel(jsonArray.getJSONObject(0));
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(model!=null)
                    {
                        category.setText(model.getCategoryName());
                    }
                }
            });
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
        }else {
            utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        }
        if(progressDailog!=null)
        {
            progressDailog.cancel();
        }
    }

    @Override
    public void onError(String value) {
        utils.Utils.showToast(getActivity(), utils.Utils.getMessage(value));
        if(progressDailog!=null)
        {
            progressDailog.cancel();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap_view = googleMap;
        gmap_view.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap_view.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
}
