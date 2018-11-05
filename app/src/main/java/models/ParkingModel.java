package models;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 05-11-2018.
 */

public class ParkingModel {
    String id;
    String business_id;
    String location_name;
    String street_name;
    String door_no;
    String zip_code;
    String latitude;
    String longitude;
    String phone_number;
    String image_url;
    String distance;

    public ParkingModel(JSONObject jsonObject) {
        try {
            this.id = jsonObject.isNull("id") ? "" : jsonObject.getString("id");
            this.business_id = jsonObject.isNull("business_id") ? "" : jsonObject.getString("business_id");
            this.location_name = jsonObject.isNull("location_name") ? "" : jsonObject.getString("location_name");
            this.street_name = jsonObject.isNull("street_name") ? "" : jsonObject.getString("street_name");
            this.door_no = jsonObject.isNull("door_no") ? "" : jsonObject.getString("door_no");
            this.zip_code = jsonObject.isNull("zip_code") ? "" : jsonObject.getString("zip_code");
            this.latitude = jsonObject.isNull("latitude") ? "" : jsonObject.getString("latitude");
            this.longitude = jsonObject.isNull("longitude") ? "" : jsonObject.getString("longitude");
            this.phone_number = jsonObject.isNull("phone_number") ? "" : jsonObject.getString("phone_number");
            this.image_url = jsonObject.isNull("image_url") ? "" : jsonObject.getString("image_url");
            this.distance = jsonObject.isNull("distance") ? "" : jsonObject.getString("distance");
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getDistance() {
        return distance;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public String getDoor_no() {
        return door_no;
    }

    public String getId() {
        return id;
    }

    public String getImage_url() {
        return image_url;
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
}
