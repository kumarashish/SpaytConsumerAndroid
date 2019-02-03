package models;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 05-12-2018.
 */

public class UserTimers {

    String user_id;
    String order_id;
    String business_location_id;
    String start_time;
    String end_time;
    String id="";

    public UserTimers(JSONObject jobject)
    {
        try{
            user_id=jobject.isNull("user_id")?"":jobject.getString("user_id");
            order_id=jobject.isNull("order_id")?"":jobject.getString("order_id");
            business_location_id=jobject.isNull("business_location_id")?"":jobject.getString("business_location_id");
            start_time=jobject.isNull("start_time")?"":jobject.getString("start_time");
            end_time=jobject.isNull("end_time")?"":jobject.getString("end_time");
            id=jobject.isNull("id")?"":jobject.getString("id");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }

    public String getUser_id() {
        return user_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getBusiness_location_id() {
        return business_location_id;
    }

    public String getId() {
        return id;
    }
}
