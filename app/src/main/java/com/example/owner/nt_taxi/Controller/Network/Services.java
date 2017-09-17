package com.example.owner.nt_taxi.Controller.Network;

import android.content.Context;

import com.example.owner.nt_taxi.Model.Location;
import com.example.owner.nt_taxi.Model.constants_class;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;



public class Services {

    private static String url = "http://192.168.61.2/nt_taxi_BackEnd/";


    public static void Login(String email, String password, String category,
                             RequestCallback requestCallback, Context context){

        HashMap<String,String> values = new HashMap<>();
        values.put("email",email);
        values.put("password",password);
        values.put("category",category);

        BaseRequest.DoPost(values,requestCallback, url +  "login.php",context);
    }

    public static void Register(String name, String email, String password, String number,
                                String category, String location, RequestCallback requestCallback, Context context) {

        HashMap<String,String> values = new HashMap<>();
        values.put("name",name);
        values.put("email",email);
        values.put("password",password);
        values.put("number",number);
        values.put("category",category);
        values.put("location", location + ", Egypt");

        BaseRequest.DoPost(values,requestCallback, url + "registration.php",context);

    }

    public static void getDriversLocation(RequestCallback requestCallback,Context context){
        HashMap<String,String> values = new HashMap<>();
        BaseRequest.DoGet(values,requestCallback, url + "getlocations.php?token=" +
                constants_class.sharedPreferences.getString(constants_class.Token,""),context);
    }

    public static void getDirection(String Origin, String Destination,
                                    RequestCallback requestCallback, Context context){

        HashMap<String,String> values = new HashMap<>();
        BaseRequest.DoGet(values,requestCallback, "https://maps.googleapis.com/maps/api/directions/json?origin=" + Origin +"&destination=" + Destination +"&key=AIzaSyAQPU4c_fou92gdNPvsP_IVr8hlqNZn3cI",context);

    }

    public static void requestTaxi(Location DriverInfo, String location, String dropLocation,
                                   LatLng current, LatLng From, LatLng To,
                                   RequestCallback requestCallback, Context context){

        HashMap<String,String> values = new HashMap<>();
        values.put("token",constants_class.sharedPreferences.getString(constants_class.Token,""));
        values.put("driver_id",DriverInfo.getId());
        values.put("driver_name",DriverInfo.getName());
        values.put("driver_email",DriverInfo.getEmail());
        values.put("name",constants_class.sharedPreferences.getString(constants_class.UserName,""));
        values.put("phone",constants_class.sharedPreferences.getString(constants_class.Number,""));
        values.put("location",location);
        values.put("droplocation",dropLocation);
        values.put("latitude", String.valueOf(current.latitude));
        values.put("longitude", String.valueOf(current.longitude));
        values.put("from_latitude", String.valueOf(From.latitude));
        values.put("from_longitude", String.valueOf(From.longitude));
        values.put("to_latitude", String.valueOf(To.latitude));
        values.put("to_longitude", String.valueOf(To.longitude));

        BaseRequest.DoPost(values,requestCallback, url + "requesttaxi.php",context);



    }

    public static void HistoryList(RequestCallback requestCallback, Context context){

        HashMap<String,String> values = new HashMap<>();
        values.put("token",constants_class.sharedPreferences.getString(constants_class.Token,""));


        BaseRequest.DoPost(values,requestCallback, url + "user-rides-list.php",context);

    }


    public static void Update_Profile(String email, String name, String location, String encoded,
                                      RequestCallback requestCallback, Context context) {

        HashMap<String, String> values = new HashMap<>();
        values.put("token", constants_class.sharedPreferences.getString(constants_class.Token, ""));
        values.put("name", name);
        values.put("email", email);
        values.put("location", location + ", Egypt");
        values.put("encoded_string", encoded);
        values.put("ID", constants_class.sharedPreferences.getString(constants_class.UserID, ""));

        BaseRequest.DoPost(values, requestCallback, url + "UpdateProfile.php", context);
    }
}
