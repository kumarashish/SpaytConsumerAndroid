package common;

import android.app.Application;

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
}
