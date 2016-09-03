package sample.contentprovider.com.content_provider;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ankit on 9/3/2016.
 */
public class JSONExample {

    private static Context context;
    private static JSONExample mInstance;

    public JSONExample(Context context) {
        this.context = context;
    }

    public static JSONExample getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new JSONExample(context);
        }
        return mInstance;
    }

    String sampleJson = "{\"name\":\"harry\"," +
            "\"Roll\":1," +
            "\"address\":" +
            "{\"city\":\"Dhandbad\"," +
            "\"pin\":232323}," +
            "\"marks\":[100,23,24]}";

    public void run(){
        m(sampleJson);


        String name;
        int roll;
        MyAdress address;
        ArrayList<Integer> marks;

        try {

            JSONObject mainObj = new JSONObject(sampleJson);

            name = mainObj.getString("name");
            roll = mainObj.getInt("Roll");

            JSONObject addressObject = mainObj.getJSONObject("address");
            address = new MyAdress(addressObject.getString("city"),addressObject.getInt("pin"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void m(String msg){
        Log.d("JSONExample",msg);
    }

    class MyAdress{
        String city;
        int pin;

        public MyAdress(String city, int pin) {
            this.city = city;
            this.pin = pin;
        }
    }

    
}
