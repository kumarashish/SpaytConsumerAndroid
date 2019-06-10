package network;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import common.AppController;
import common.Common;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    AppController controller;

    @Override
    public void onTokenRefresh() {
        controller = (AppController) getApplicationContext();
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d("Firebase", "token " + FirebaseInstanceId.getInstance().getToken());
        registerToken(token);
    }

    private void registerToken(String token) {
        controller.getPrefManager().setFcmToken(token);

    }
}
