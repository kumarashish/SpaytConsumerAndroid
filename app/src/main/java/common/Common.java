package common;

/**
 * Created by ashish.kumar on 23-10-2018.
 */

public class Common {
    public static String baseUrl="https://api.spayt.de/";
    public static String register=baseUrl+"consumers/register?";
    public static String registerWithFb=baseUrl+"user/facebook_register";
    //public static String loginwithFb=baseUrl+"user/facebook_login";
    public static String login=baseUrl+"consumers/login";
    public static String forgetPassword=baseUrl+"user/forgot_password";
    public static String logout=baseUrl+"consumers/logout";
    public static String getCategories=baseUrl+"consumers/get_categories";
    public static String locationOffers=baseUrl+"consumers/business_location_offers";
    public static String getLocationByDistance=baseUrl+"user/distance_by_km";

    public static String getRegisterUrl( String fname, String lname, String email, String password)
    {
        return  register+"email="+email+"&password="+password+"&first_name="+fname+"&last_name="+lname;
    }


    // login with fb
  //  @"fb_id" :strSocial_id, @"email" :strEmail,@"username":strUserName,@"device_id":device_id,@"access_token":token_id

    //login "email" :txt_email.text, @"password"
    //register "email" :txt_email.text, @"password" :txt_password.text,@"first_name":txt_fname.text,@"last_name":txt_lname.text
}
