package prelogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.spaytconsumer.DashBoard;
import com.spaytconsumer.R;

import common.AppController;

/**
 * Created by ashish.kumar on 22-10-2018.
 */

public class Splash extends Activity{
    AppController controller;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        controller=(AppController) getApplicationContext();
        runThread();
    }

    public void runThread()
    {
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(controller.getPrefManager().isUserLoggedIn())
                {
                    startActivity(new Intent(Splash.this,DashBoard.class));
                }else{
                    startActivity(new Intent(Splash.this, Login.class));
                }
            finish();
            }
        }, 5000);
    }
}
