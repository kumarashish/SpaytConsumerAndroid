package adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spaytconsumer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import intefaces.BookNowCallBack;
import models.ParkingModel;

public class ListAdapter extends BaseAdapter {
    Activity act;
    ArrayList<ParkingModel> pakingLocationList ;
    LayoutInflater inflater;
    BookNowCallBack callBack;
    public ListAdapter(Activity act,ArrayList<ParkingModel> pakingLocationList,BookNowCallBack callBack)
    {
        this.pakingLocationList=pakingLocationList;
        this.act=act;
        inflater = LayoutInflater.from(act);
        this.callBack=callBack;
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

        final ParkingModel model=pakingLocationList.get(i);
        view= inflater.inflate(R.layout.listview_row, viewGroup, false);
        Button booknow=(Button) view.findViewById(R.id.booknow);
        TextView placeName=(TextView)view.findViewById(R.id.placeName);
        ImageView image=(ImageView)view.findViewById(R.id.image);
        placeName.setText(model.getLocation_name());
        if(model.getImage_url().length()>0) {
            Picasso.with(act).load(model.getImage_url()).placeholder(R.drawable.parkingspot).resize(110, 110).into(image);
        }else{
            image.setImageResource(R.drawable.parkingspot);
        }
        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onBookButtonClick(model);
            }
        });

        return view;
    }
}
