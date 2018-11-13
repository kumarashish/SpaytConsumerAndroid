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

public abstract class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter ,View.OnTouchListener {
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


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (0 <= event.getX() && event.getX() <= view.getWidth() && 0 <= event.getY() && event.getY() <= view.getHeight()) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startPress();
                    break;

                // We need to delay releasing of the view a little so it shows the
                // pressed state on the screen
                case MotionEvent.ACTION_UP:
                    handler.postDelayed(confirmClickRunnable, 150);
                    break;

                case MotionEvent.ACTION_CANCEL:
                    endPress();
                    break;
                default:
                    break;
            }
        } else {
            // If the touch goes outside of the view's area
            // (like when moving finger out of the pressed button)
            // just release the press
            endPress();
        }
        return false;
    }
    private void startPress() {
        if (!pressed) {
            pressed = true;
            handler.removeCallbacks(confirmClickRunnable);
            view.setBackgroundDrawable(bgDrawablePressed);
            if (marker != null)
                marker.showInfoWindow();
        }
    }

    private boolean endPress() {
        if (pressed) {
            this.pressed = false;
            handler.removeCallbacks(confirmClickRunnable);
            view.setBackgroundDrawable(bgDrawableNormal);
            if (marker != null)
                marker.showInfoWindow();
            return true;
        } else
            return false;
    }

    private final Runnable confirmClickRunnable = new Runnable() {
        public void run() {
            if (endPress()) {
                onClickConfirmed(view, marker);
            }
        }
    };

    /**
     * This is called after a successful click
     */
    protected abstract void onClickConfirmed(View v, Marker marker);
}
