package models;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 05-12-2018.
 */

public class LocationDetails {
    String orderid;
    String business_id;
    String location_name;
    String street_name;
    String door_no;
    String city;
    String zip_code;
    String latitude;
    String longitude;
    String phone_number;
    String image_url;

    public LocationDetails(JSONObject jobject) {
        try {
            orderid = jobject.isNull("id") ? "" : jobject.getString("id");
            business_id = jobject.isNull("business_id") ? "" : jobject.getString("business_id");
            location_name = jobject.isNull("location_name") ? "" : jobject.getString("location_name");
            street_name = jobject.isNull("street_name") ? "" : jobject.getString("street_name");
            door_no = jobject.isNull("door_no") ? "" : jobject.getString("door_no");
            city = jobject.isNull("city") ? "" : jobject.getString("city");
            zip_code = jobject.isNull("zip_code") ? "" : jobject.getString("zip_code");
            latitude = jobject.isNull("latitude") ? "" : jobject.getString("latitude");
            longitude = jobject.isNull("longitude") ? "" : jobject.getString("longitude");
            phone_number = jobject.isNull("phone_number") ? "" : jobject.getString("phone_number");
            image_url = jobject.isNull("image_url") ? "" : jobject.getString("image_url");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }
    }

    public String getCity() {
        return city;
    }

    public String getDoor_no() {
        return door_no;
    }

    public String getLocation_name() {
        return location_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getStreet_name() {
        return street_name;
    }

    public String getZip_code() {
        return zip_code;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getOrderid() {
        return orderid;
    }
}
