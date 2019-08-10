package adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spaytconsumer.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import intefaces.InvoiceButtonClickListner;
import models.OrderDetailsModel;
import models.OrderInvoiceModel;
import models.ParkingModel;

/**
 * Created by ashish.kumar on 03-12-2018.
 */

public class Invoices_ListAdapter extends BaseAdapter  {
    Activity activity;
    List<OrderInvoiceModel.OrderDatum> orderData;
    LayoutInflater inflater;
    InvoiceButtonClickListner callback;
    public Invoices_ListAdapter(Activity activity, List<OrderInvoiceModel.OrderDatum> orderData, InvoiceButtonClickListner callback) {
        this.activity=activity;
        this.orderData=orderData;
        inflater = LayoutInflater.from(activity);
        this.callback=callback;
    }

    @Override
    public int getCount() {
        return orderData.size();
    }

    @Override
    public Object getItem(int position) {
        return orderData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final OrderInvoiceModel.OrderDatum model=orderData.get(position);
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.order_row_new, parent, false);
            holder.name=(TextView)convertView.findViewById(R.id.name);
            holder.amount=(TextView)convertView.findViewById(R.id.net_amount);
            holder.paymentstatus=(TextView)convertView.findViewById(R.id.payment_status);
            holder.orderstatus=(TextView)convertView.findViewById(R.id.order_status);
            holder.date=(TextView)convertView.findViewById(R.id.order_date);
            holder.download=(Button) convertView.findViewById(R.id.download);
            holder.email=(Button) convertView.findViewById(R.id.email);



        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        convertView.setTag(holder);
        holder.name.setText("Order Id : "+model.getId());
        holder.amount.setText("Net Amount : "+model.getNetAmount()+" €");
        holder.date.setText("Order Date : "+model.getUpdatedOn());
        holder.orderstatus.setText("Order status : "+model.getStatus());
        if(model.getPaypalTransactionId()!=null)
        {
            holder.paymentstatus.setText("Your Order Id : "+model.getId()+" has been paid.\nPaypal Id : "+model.getPaypalTransactionId());
        }else{
            holder.paymentstatus.setText("Payment : pending");

        }

holder.download.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        callback.onDownloadClick(model.getId());
    }
});
        holder.email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onEmailClick(model.getId());
            }
        });
        return convertView;
    }
    public class ViewHolder{
        TextView name,amount,orderstatus,date,paymentstatus;
        Button download,email;

    }
}

