package common;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;

import models.UserProfile;
import network.WebApiCall;
import utils.Validation;

/**
 * Created by ashish.kumar on 23-10-2018.
 */

public class AppController extends Application {
    WebApiCall apiCall;
    Validation validation;
    UserProfile profile;
    PrefManager prefManager;
    String address;
    LatLng currentLocation;
    @Override
    public void onCreate() {

        apiCall=new WebApiCall(this);
        profile=new UserProfile();
        prefManager=new PrefManager(this);
        super.onCreate();

    }

    public PrefManager getPrefManager() {
        return prefManager;
    }

    public UserProfile getProfile() {
        return profile;
    }

    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    public WebApiCall getApiCall() {
        return apiCall;
    }

    public void setAddress(String address, LatLng loc) {
        this.address = address;
        this.currentLocation=loc;
    }
    public void setCurrentAddress(String address) {
        this.address = address;

    }

    public void setLocation(LatLng loc) {
        double latitude = loc.latitude;
        double longitude = loc.longitude;
        currentLocation = loc;
    }


    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public String getAddress() {
        return address;
    }
}
