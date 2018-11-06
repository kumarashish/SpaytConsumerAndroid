package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.spaytconsumer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import models.ParkingModel;

public class ListAdapter extends BaseAdapter {
    Activity act;
    ArrayList<ParkingModel> pakingLocationList ;
    LayoutInflater inflater;
    public ListAdapter(Activity act,ArrayList<ParkingModel> pakingLocationList)
    {
        this.pakingLocationList=pakingLocationList;
        this.act=act;
        inflater = LayoutInflater.from(act);
    }

    @Override
    public int getCount() {
        return pakingLocationList.size();
    }

    @Override
    public Object getItem(int i) {
        return pakingLocationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ParkingModel model=pakingLocationList.get(i);
        view= inflater.inflate(R.layout.listview_row, viewGroup, false);
        TextView placeName=(TextView)view.findViewById(R.id.placeName);
        ImageView image=(ImageView)view.findViewById(R.id.image);
        placeName.setText(model.getLocation_name());
        if(model.getImage_url().length()>0) {
            Picasso.with(act).load(model.getImage_url()).placeholder(R.drawable.parkingspot).resize(110, 110).into(image);
        }else{
            image.setImageResource(R.drawable.parkingspot);
        }
        return view;
    }
}
