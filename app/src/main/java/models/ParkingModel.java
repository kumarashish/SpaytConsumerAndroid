package models;

import org.json.JSONArray;
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
    Opening_Hours opening_hours;
    Parking_Fees parking_fees;

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

    public void setBusinessDetails(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray locationDetails = jsonObject.getJSONArray("Location Details");
            JSONArray openingHourArray = jsonObject.getJSONArray("Opening Hours");
            JSONArray parkingArray = jsonObject.getJSONArray("Parking Fees");
            if(parking_fees!=null) {
                parking_fees = new Parking_Fees(parkingArray.getJSONObject(0));
            }else{
                parking_fees = new Parking_Fees();
            }
            if(opening_hours!=null) {
                opening_hours = new Opening_Hours(openingHourArray.getJSONObject(0));
            }else{
opening_hours=new Opening_Hours();
            }
            setBusinessLocationId(locationDetails.getJSONObject(0));
        } catch (Exception ex) {
            ex.fillInStackTrace();
        }

    }

    public Opening_Hours getOpening_hours() {
        return opening_hours;
    }

    public Parking_Fees getParking_fees() {
        return parking_fees;
    }

    public class Opening_Hours{

        String  monday_mode="N/A";
        String tuesday_mode="N/A";
        String wednesday_mode="N/A";
        String thursday_mode="N/A";
        String friday_mode="N/A";
        String saturday_mode="N/A";
        String sunday_mode="N/A";

        String mondayTimming="00:00";
        String tuesdayTimming="00:00";
        String wednesdayTimming="00:00";
        String thursdayTimming="00:00";
        String fridayTimming="00:00";
        String saturdayTimming="00:00";
        String sundayTimming="00:00";
public Opening_Hours(){}
        public Opening_Hours(JSONObject jsonObject) {
            try {
                monday_mode = jsonObject.isNull("monday_mode") ? "" : jsonObject.getString("monday_mode");
                mondayTimming = jsonObject.getString("monday_morning_from") + " - " + jsonObject.getString("monday_morning_to");
                if (monday_mode.equalsIgnoreCase("A") && (jsonObject.getString("monday_afternoon_from").length() > 0)) {
                    mondayTimming += "\n" + jsonObject.getString("monday_afternoon_from") + " - " + jsonObject.getString("monday_afternoon_to");
                }
                tuesday_mode = jsonObject.isNull("tuesday_mode") ? "" : jsonObject.getString("tuesday_mode");
                tuesdayTimming = jsonObject.getString("tuesday_morning_from") + " - " + jsonObject.getString("tuesday_morning_to");
                if (tuesday_mode.equalsIgnoreCase("A") && (jsonObject.getString("tuesday_afternoon_from").length() > 0)) {
                    tuesdayTimming += "\n" + jsonObject.getString("tuesday_afternoon_from") + " - " + jsonObject.getString("tuesday_afternoon_to");
                }
                wednesday_mode = jsonObject.isNull("wednesday_mode") ? "" : jsonObject.getString("wednesday_mode");
                wednesdayTimming = jsonObject.getString("wednesday_morning_from") + " - " + jsonObject.getString("wednesday_morning_to");
                if (wednesday_mode.equalsIgnoreCase("A") && (jsonObject.getString("wednesday_afternoon_from").length() > 0)) {
                    wednesdayTimming += "\n" + jsonObject.getString("wednesday_afternoon_from") + " - " + jsonObject.getString("wednesday_afternoon_to");
                }
                thursday_mode = jsonObject.isNull("thursday_mode") ? "" : jsonObject.getString("thursday_mode");
                thursdayTimming = jsonObject.getString("thursday_morning_from") + " - " + jsonObject.getString("thursday_morning_to");
                if (thursday_mode.equalsIgnoreCase("A") && (jsonObject.getString("thursday_afternoon_from").length() > 0)) {
                    thursdayTimming += "\n" + jsonObject.getString("thursday_afternoon_from") + " - " + jsonObject.getString("thursday_afternoon_to");
                }
                friday_mode = jsonObject.isNull("friday_mode") ? "" : jsonObject.getString("friday_mode");
                fridayTimming = jsonObject.getString("friday_morning_from") + " - " + jsonObject.getString("friday_morning_to");
                if (friday_mode.equalsIgnoreCase("A") && (jsonObject.getString("friday_afternoon_from").length() > 0)) {
                    fridayTimming += "\n" + jsonObject.getString("friday_afternoon_from") + " - " + jsonObject.getString("friday_afternoon_to");
                }
                saturday_mode = jsonObject.isNull("saturday_mode") ? "" : jsonObject.getString("saturday_mode");
                saturdayTimming = jsonObject.getString("saturday_morning_from") + " - " + jsonObject.getString("saturday_morning_to");
                if (saturday_mode.equalsIgnoreCase("A") && (jsonObject.getString("saturday_afternoon_from").length() > 0)) {
                    saturdayTimming += "\n" + jsonObject.getString("saturday_afternoon_from") + " - " + jsonObject.getString("saturday_afternoon_to");
                }
                sunday_mode = jsonObject.isNull("sunday_mode") ? "" : jsonObject.getString("sunday_mode");
                sundayTimming = jsonObject.getString("sunday_morning_from") + " - " + jsonObject.getString("sunday_morning_to");
                if (sunday_mode.equalsIgnoreCase("A") && (jsonObject.getString("sunday_afternoon_from").length() > 0)) {
                    sundayTimming += "\n" + jsonObject.getString("sunday_afternoon_from") + " - " + jsonObject.getString("sunday_afternoon_to");
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }

        public String getFridayTimming() {
            return fridayTimming;
        }

        public String getMondayTimming() {
            return mondayTimming;
        }

        public String getSaturdayTimming() {
            return saturdayTimming;
        }

        public String getSundayTimming() {
            return sundayTimming;
        }

        public String getThursdayTimming() {
            return thursdayTimming;
        }

        public String getTuesdayTimming() {
            return tuesdayTimming;
        }

        public String getWednesdayTimming() {
            return wednesdayTimming;
        }
    }
    public class Parking_Fees{
        String minimum_parking_hours="0";
                String maximum_parking_fees="0";
                        String parking_fee_per_hour="0";
                        public Parking_Fees(){}
        public  Parking_Fees(JSONObject jsonObject)
        {
           try{
               maximum_parking_fees=jsonObject.isNull( "maximum_parking_fees")?"":jsonObject.getString("maximum_parking_fees");
               parking_fee_per_hour=jsonObject.isNull( "parking_fee_per_hour")?"":jsonObject.getString("parking_fee_per_hour");
               minimum_parking_hours=jsonObject.isNull( "minimum_parking_hours")?"":jsonObject.getString("minimum_parking_hours");
           }catch (Exception ex)
           {
               ex.fillInStackTrace();
           }
        }


        public String getMaximum_parking_fees() {
            return maximum_parking_fees;
        }

        public String getMinimum_parking_hours() {
            return minimum_parking_hours;
        }

        public String getParking_fee_per_hour() {
            return parking_fee_per_hour;
        }
    }
    public void setBusinessLocationId(JSONObject jsonObject)
    {try{
        business_id=jsonObject.getString("id");
    }catch (Exception ex)
    {
        ex.fillInStackTrace();
    }
    }
}
