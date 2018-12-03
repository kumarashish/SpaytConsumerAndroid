package common;

/**
 * Created by ashish.kumar on 23-10-2018.
 */

public class Common {
    public static String baseUrl="https://api.spayt.de/user/";
    public static String register=baseUrl+"register?";
    public static String registerWithFb=baseUrl+"facebook_register";
    //public static String loginwithFb=baseUrl+"user/facebook_login";
    public static String login=baseUrl+"login";
    public static String forgetPassword=baseUrl+"forgot_password";
    public static String logout=baseUrl+"logout";
    public static String getCategories=baseUrl+"get_categories";
    public static String locationOffers=baseUrl+"business_location_offers";
    public static String getLocationByDistance=baseUrl+"distance_by_km?";
    public static String updateProfile=baseUrl+"update_prinfo";
    public static String googleMapsApiKey="AIzaSyARyIWs_8t0_hIrTPGqMsOPboaEa5T25Qs";
    public static String isTimerStartedUrl=baseUrl+"getPaymentAfterKillProcess";
    public static String getRecentView=baseUrl+"business_location_recently_viewed";
    public static String getlocationDetails=baseUrl+"location_details";
    public static String getUSerCarPlate=baseUrl+"getUserParkingCar";
    public static String getInvoices=baseUrl+"get_invoices";


    public static String userIdKey="user_id";
    public static String idKey="id";






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
