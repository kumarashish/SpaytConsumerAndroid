package common;

import android.app.Application;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

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
    String address="";
    LatLng currentLocation,aroundMe;
    @Override
    public void onCreate() {

        apiCall=new WebApiCall(this);

        prefManager=new PrefManager(this);
        if(prefManager.getUserProfile().length()>0)
        {
            if (prefManager.getUserProfile().length() > 0) {
                Gson gson = new Gson();
                profile = gson.fromJson(prefManager.getUserProfile(), UserProfile.class);
            }
        }else {
            profile=new UserProfile();
        }
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
        Gson gson = new Gson();
        String userProfileString = gson.toJson(profile);
        prefManager.setUserProfile(userProfileString);

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

    public void setAroundME(LatLng loc)
    {
        double latitude = loc.latitude;
        double longitude = loc.longitude;
        aroundMe = loc;
    }

    public LatLng getAroundMe() {
        return aroundMe;
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    public String getAddress() {
        return address;
    }
}
