package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.spaytconsumer.R;

import java.util.ArrayList;
import java.util.List;

import intefaces.ButtonClickListner;
import models.OrderModel;

public class OrderAdapter extends BaseAdapter {
    ButtonClickListner listner;
    Activity act;
    LayoutInflater inflater;
    ArrayList<OrderModel.OrderItems> list;
    public OrderAdapter(Activity act, List<OrderModel.OrderItems> list){
        this.act=act;
        this.list.addAll(list);
        listner=(ButtonClickListner)act;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final OrderModel.OrderItems model=list.get(position);
        if (convertView == null) {
            holder=new ViewHolder();
            convertView= inflater.inflate(R.layout.order_row, parent, false);
            holder.details=(Button) convertView.findViewById(R.id.details);
            holder.amount=(TextView)convertView.findViewById(R.id.amount);
            holder.date=(TextView)convertView.findViewById(R.id.orderdate);
            holder.orderId=(TextView)convertView.findViewById(R.id.orderId);
            holder.status=(TextView)convertView.findViewById(R.id.status);

        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        holder.orderId.setText("Order Id : "+model.getId());
        holder.date.setText("Date : "+model.getUpdatedOn());
        holder.amount.setText("Amount : "+model.getNetAmount());
        holder.status.setText("Status : "+model.getStatus());
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.OnDetailsClicked(model.getId());
            }
        });
        convertView.setTag(holder);
        return convertView;
    }

    public class ViewHolder{
        TextView orderId,date,amount,status;
        Button details;
    }
}
