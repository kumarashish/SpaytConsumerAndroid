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
    public static String getCategories=baseUrl+"user/get_categories";
    public static String locationOffers=baseUrl+"consumers/business_location_offers";
    public static String getLocationByDistance=baseUrl+"user/distance_by_km?";
    public static String updateProfile=baseUrl+"user/update_prinfo";
    public static String googleMapsApiKey="AIzaSyARyIWs_8t0_hIrTPGqMsOPboaEa5T25Qs";
    public static String isTimerStartedUrl=baseUrl+"user/getPaymentAfterKillProcess";
    public static String getRecentView=baseUrl+"user/business_location_recently_viewed";






    //        URL =  http://api.spayt.de/user/getPaymentAfterKillProcess
  //  Request Json {
        //"user_id" = 69;
   // }
    //    business_locations_by_city
//    #define kCategories @"get_categories"
//            #define kUpdateProfile @""
//            #define kLocations @"distance_by_km"
//            #define kLocationDetails @"location_details"
//            #define kRecentlyViewed @"business_location_recently_viewed"
//            #define kSaveViewed @"business_location_recently_veiw_saved"
//            #define kOffers @"business_location_offers"
//            #define kOrder @"consumers_orders"
//            #define kTIMER @"AfterTimerStart"
//            #define kUSERPARKING @"getUserParkingCar"
//            #define kISTIMERSTART @"getPaymentAfterKillProcess"
//            #define kInvoiceList @"get_invoices"
    public static String getRegisterUrl( String fname, String lname, String email, String password)
    {
        return  register+"email="+email+"&password="+password+"&first_name="+fname+"&last_name="+lname;
    }
public static String getGetLocationByDistance(Double lat,Double lon,int distance)
{
    return   getLocationByDistance+"latitude="+lat+"&longitude="+lon+"&distance="+distance;
}

    // login with fb
  //  @"fb_id" :strSocial_id, @"email" :strEmail,@"username":strUserName,@"device_id":device_id,@"access_token":token_id

    //login "email" :txt_email.text, @"password"
    //register "email" :txt_email.text, @"password" :txt_password.text,@"first_name":txt_fname.text,@"last_name":txt_lname.text
}
