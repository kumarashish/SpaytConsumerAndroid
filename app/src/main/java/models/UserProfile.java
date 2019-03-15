package models;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 23-10-2018.
 */

public class UserProfile {

    String first_name="";
    String last_name="";
    String user_id="";
    String email="";

    public UserProfile(String value)
    {
        try{
            JSONObject jsonObject1=new JSONObject(value);
            JSONObject jsonObject=jsonObject1.getJSONObject("consumer_details");
            first_name=jsonObject.isNull("first_name")?"":jsonObject.getString("first_name");
            last_name=jsonObject.isNull("last_name")?"":jsonObject.getString("last_name");
            user_id=jsonObject.isNull("consumer_id")?"":jsonObject.getString("consumer_id");
            email=jsonObject.isNull("email")?"":jsonObject.getString("email");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }
    public UserProfile(JSONObject jsonObject)
    {
        try{

            first_name=jsonObject.isNull("first_name")?"":jsonObject.getString("first_name");
            last_name=jsonObject.isNull("last_name")?"":jsonObject.getString("last_name");
            user_id=jsonObject.isNull("consumer_id")?"":jsonObject.getString("consumer_id");
            email=jsonObject.isNull("email")?"":jsonObject.getString("email");
        }catch (Exception ex)
        {
            ex.fillInStackTrace();
        }
    }
    public UserProfile(){}

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }
}
