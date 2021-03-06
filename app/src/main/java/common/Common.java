package common;

/**
 * Created by ashish.kumar on 23-10-2018.
 */

   public class Common {
   public static String baseUrl = "https://api.spayt.de/user/";
   public static String baseUrlNew = "https://api.spayt.de/";
   public static String register = baseUrl + "register?";
   public static String registerWithFb = baseUrlNew + "consumers/login_with_facebook";
   //public static String loginwithFb=baseUrl+"user/facebook_login";
   public static String login = baseUrlNew + "consumers/login";
   public static String forgetPassword = baseUrl + "forgot_password";
   public static String logout = baseUrl + "logout";
   public static String getCategories = baseUrl + "get_categories";
   public static String locationOffers = baseUrl + "business_location_offers";
   public static String getLocationByDistance = baseUrl + "distance_by_km?";
   public static String updateProfile = baseUrl + "update_prinfo";
   public static String googleMapsApiKey = "AIzaSyARyIWs_8t0_hIrTPGqMsOPboaEa5T25Qs";
   public static String isTimerStartedUrl = baseUrl + "getPaymentAfterKillProcess";
   public static String getRecentView = baseUrl + "business_location_recently_viewed";
   public static String getlocationDetails = baseUrl + "location_details";
   public static String getUSerCarPlate = baseUrl + "getUserParkingCar";
   public static String getSaveUSerCarPlateNumber = baseUrl + "getUserParkingCar";
   public static String getInvoices = baseUrlNew + "order/get_my_consumer_orders";
   public static  String sendOrderPdfEmail=baseUrlNew+"/order/send_order_pdf_email";
   public static  String downloadOrderPdfInvoice=baseUrlNew+"/order/download_order_pdf";
   public static String getStartedTimerUrl = baseUrl + "AfterTimerStart";
   public static String getUpdateOrder = baseUrl + "consumers_orders";
   public static String getQRCode = baseUrlNew + "consumers/get_my_qrcode";
   public static String getOutstandingOrder = baseUrlNew + "order/get_outstanding_consumer_orders";
   public static String getOutstandingOrderDetails = baseUrlNew + "order/get_consumer_order_details";
   public static String updatePaymentStatus = baseUrlNew + "order/update_payment";
   public static String[] afterTimerStartKeys = {"IsTimerStarted", "IsTimerStoped", "IsPaymentDone", "business_location_id", "start_time", "end_time", "user_id", "amount", "parking_carplate_no", "timer_id"};
   public static String[] updatePaymentKeys = {"business_location_id", "order_id", "transactionid", "total_fees", "user_id", "parking_hours", "status", "parking_carplate_no"};
   public static String[] updatePaymentStatusKeys = {"id", "transactionid"};
   public static String userIdKey = "user_id";
   public static String[] id={"id"};
   public static String idKey = "id";
   public static String paypalClientIdSanbox = "ATEDn8H26GtM_VpLMrIWrTwvJCxc6d_xWNa0W34PL9FqLwEg41FRrjlLZppbhR_ShSG50ztPZ6bpPBXz";
   public static String paypalClientSecretSanbox = "EF0a3sqSFCDwlYmpiTGhJNrEapL_qi2Gdktsh5lOhJTtMoxuDdgkjK-BcoKJeznwYrEegLRpHwh5fTgB";
   public static String paypalClientIdLive = "AWjXaMC3FMmr70GbAEHKVpCXQQ5XyZ745RQ-yFq_2Vu7E-AEog9xi_XokV0De0CJr2Q_QlWzannKEQzp";
   public static String paypalClientSecretLive = "EEMUpvzO1MwUT8k7qUyhFViS0TINM-qUh5bLnyI6pz5GudwBFlE50roQHK3CK-NwpoaQwpg1H9x8Fu4n";


   public static String getRegisterUrl(String fname, String lname, String email, String password) {
      return register + "email=" + email + "&password=" + password + "&first_name=" + fname + "&last_name=" + lname;
   }

   public static String getGetLocationByDistance(Double lat, Double lon, int distance) {
      return getLocationByDistance + "latitude=" + lat + "&longitude=" + lon + "&distance=" + distance;
   }


}
