package fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spaytconsumer.R;

/**
 * Created by ashish.kumar on 30-10-2018.
 */

public class Aroundme  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.around_me,
                container, false);


        return view;
    }
}