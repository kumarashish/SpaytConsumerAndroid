package models;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 03-12-2018.
 */

public class OrderDetailsModel {
    String order_id;
    String dt;
    String gross_amount;
    String discount_amount;
    String tax_amount;
    String location_name;
    String street_name;
    String door_no;
    String city;
    String zip_code;
    String phone_number;
    String consumer_id;
    String start_time;
    String end_time;
    String total_fees;
    String transactionid;
    String status;

    public OrderDetailsModel(JSONObject jobject)
    {
        try{
            this.order_id=jobject.isNull("order_id")?"":jobject.getString("order_id");
            this.dt=jobject.isNull("dt")?"":jobject.getString("dt");;
            this.gross_amount=jobject.isNull("gross_amount")?"":jobject.getString("gross_amount");
            this.discount_amount=jobject.isNull("discount_amount")?"":jobject.getString("discount_amount");
            this.tax_amount=jobject.isNull("tax_amount")?"":jobject.getString("tax_amount");
            this.location_name=jobject.isNull("location_name")?"":jobject.getString("location_name");
            this.street_name=jobject.isNull("street_name")?"":jobject.getString("street_name");
            this.door_no=jobject.isNull("door_no")?"":jobject.getString("door_no");
            this.city=jobject.isNull("city")?"":jobject.getString("city");
            this.zip_code=jobject.isNull("zip_code")?"":jobject.getString("zip_code");
            this.phone_number=jobject.isNull("phone_number")?"":jobject.getString("phone_number");
            this.consumer_id=jobject.isNull("consumer_id")?"":jobject.getString("consumer_id");
            this.start_time=jobject.isNull("start_time")?"":jobject.getString("start_time");
            this.end_time=jobject.isNull("end_time")?"":jobject.getString("end_time");
            this.total_fees=jobject.isNull("total_fees")?"":jobject.getString("total_fees");
            this.transactionid=jobject.isNull("transactionid")?"":jobject.getString("transactionid");
            this.status=jobject.isNull("status")?"":jobject.getString("status");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getDoor_no() {
        return door_no;
    }

    public String getStatus() {
        return status;
    }

    public String getCity() {
        return city;
    }

    public String getConsumer_id() {
        return consumer_id;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public String getDt() {
        return dt;
    }

    public String getGross_amount() {
        return gross_amount;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public String getTotal_fees() {
        return total_fees;
    }

    public String getTransactionid() {
        return transactionid;
    }
}
