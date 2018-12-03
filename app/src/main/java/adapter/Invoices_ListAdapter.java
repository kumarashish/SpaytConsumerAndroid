package adapter;

import android.app.Activity;
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

import models.OrderDetailsModel;
import models.ParkingModel;

/**
 * Created by ashish.kumar on 03-12-2018.
 */

public class Invoices_ListAdapter extends BaseAdapter{
    ArrayList<OrderDetailsModel> list;
    Activity act;
    LayoutInflater inflater;
   public Invoices_ListAdapter(ArrayList<OrderDetailsModel> list, Activity act)
   {
       this.list=list;
       this.act=act;
       inflater = LayoutInflater.from(act);
   }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final OrderDetailsModel model=list.get(position);
        view= inflater.inflate(R.layout.invoices_row, viewGroup, false);

        TextView placeName=(TextView)view.findViewById(R.id.placename);
        TextView date=(TextView)view.findViewById(R.id.date);
        TextView amount=(TextView)view.findViewById(R.id.amount);

        placeName.setText(model.getLocation_name());
        date.setText(model.getDt());
        amount.setText(model.getGross_amount());


        return view;
    }
}
