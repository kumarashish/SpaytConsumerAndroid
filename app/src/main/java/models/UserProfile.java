package models;

import org.json.JSONObject;

/**
 * Created by ashish.kumar on 23-10-2018.
 */

public class UserProfile {

    String first_name="";
    String last_name="";
    String user_id="";

    public UserProfile(String value)
    {
        try{
            JSONObject jsonObject=new JSONObject(value);
            first_name=jsonObject.isNull("first_name")?jsonObject.getString("username"):jsonObject.getString("first_name");
            last_name=jsonObject.isNull("last_name")?"":jsonObject.getString("last_name");
            user_id=jsonObject.isNull("user_id")?"":jsonObject.getString("user_id");
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
}
