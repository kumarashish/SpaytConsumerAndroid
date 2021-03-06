package common;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.spaytconsumer.R;

import fragments.Aroundme;
import models.ParkingModel;
import utils.Utils;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter  {
 Activity context;
    private View view;
    private  Drawable bgDrawableNormal;
    private  Drawable bgDrawablePressed;
    private final Handler handler = new Handler();

    private Marker marker;
    private boolean pressed = false;
public MarkerInfoWindowAdapter(Activity context) {
        this.context = context;
        }

@Override
public View getInfoWindow(Marker arg0) {
        return null;
        }

@Override
public View getInfoContents(Marker arg0) {
    final ParkingModel model = Aroundme.pakingLocationList.get((Integer)arg0.getTag());
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.infowindow, null);
    TextView placename = (TextView)view.findViewById(R.id.placeName);
    TextView address = (TextView)view.findViewById(R.id.address);
    final TextView contactnumber = (TextView)view.findViewById(R.id.phone);
    Button book= (Button) view.findViewById(R.id.booknow);


    placename.setText(model.getLocation_name().toUpperCase());
    address.setText(model.getStreet_name()+", "+model.getDoor_no());
    contactnumber.setText(model.getPhone_number());
    placename.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utils.showToast(context,"placename Clicked");
        }
    });
    book.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Utils.showToast(context,"Book Clicked");
        }
    });

    return view;
        }





    /**
     * This is called after a successful click
     */

}
