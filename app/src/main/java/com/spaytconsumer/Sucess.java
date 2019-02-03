package com.spaytconsumer;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Sucess extends Activity implements View.OnClickListener{
    @BindView(R.id.done)
    Button done;
    @BindView(R.id.layout)
    android.support.constraint.ConstraintLayout bottomView;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sucess_screen);
        ButterKnife.bind(this);
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) &&(Build.VERSION.SDK_INT <26)) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }else if(Build.VERSION.SDK_INT >=26){
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)bottomView.getLayoutParams();
            params.bottomMargin=120;
        }
        done.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.done:
                Intent intent = new Intent(Sucess.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }

    }
}
