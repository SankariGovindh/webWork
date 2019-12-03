package com.assignment.android.weatherapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;


public class summaryFragment extends Fragment {

    String details;
    String address;
    String city;
    String state;
    String country;

    //called from the main activity
    public summaryFragment(String iCity,String iState,String iCountry){
        city = iCity;
        state = iState;
        country = iCountry;
        details = iCity+","+iState+","+iCountry;
    }//end of summaryFragment constructor

    //called from the searchable class
    public summaryFragment(String iCity,String iCountry){
        city = iCity;
        country = iCountry;
        details = iCity + "," + iCountry;
    }

    //for adding the favorite city
    public summaryFragment(String iCity){
        city = iCity;
        details = iCity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        savedInstanceState = null;
        super.onCreate(savedInstanceState);

    }//end of onCreate method

    //layout display
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if(v==null){
            v = inflater.inflate(R.layout.summary_layout, container, false);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //textView declaration of card1
        final TextView textView = view.findViewById(R.id.temperature); //temperature + icon
        final TextView degree = view.findViewById(R.id.degree); //degree sign
        final TextView symbol = view.findViewById(R.id.symbol); //fahrenheit symbol
        final TextView info = view.findViewById(R.id.summary); //summary of the weather
        final TextView location = view.findViewById(R.id.location); //location details + information icon
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        final CardView cardView = view.findViewById(R.id.card1);

        //textView declaration of card2
        final TextView humidView = view.findViewById(R.id.humidity);
        final TextView windView = view.findViewById(R.id.wind);
        final TextView visibleView = view.findViewById(R.id.visibility);
        final TextView pressureView = view.findViewById(R.id.pressure);

        final String currentDate[] = new String[8] ;
        final Integer iconId[] = new Integer[8];
        final String minTemperature[] = new String[8];
        final String maxTemperature[] = new String[8];
        final ListView listView = view.findViewById(R.id.weekly_table);
        final CustomListAdapter weeklyList = new CustomListAdapter(getActivity(),currentDate,iconId, minTemperature,maxTemperature);

        //call IP-API to get the current location and store the JSON
        String url = "http://10.0.2.2:8081/callGoogleAPI?address="+details; //call to the google API to return the lat,lng and formatted_address
        Log.d("before calling the google API:",url);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {

                try{

                    JSONArray googleLocation = response.getJSONArray("results");
                    JSONObject coordinates = googleLocation.getJSONObject(0);
                    address = coordinates.getString("formatted_address"); //storing values to display in the end of the card1
                    JSONObject geometry = coordinates.getJSONObject("geometry");
                    JSONObject values = geometry.getJSONObject("location");
                    final String json_lat = values.getString("lat");
                    final String json_lon = values.getString("lng");

                    //code block to make backend call to fetch weather data
                    String url_weather = "http://nodeapp571.us-west-1.elasticbeanstalk.com/currentLocationCall?latitude="+json_lat+"&longitude="+json_lon;
                    Log.d("url:",url_weather);
                    final JsonObjectRequest weatherJSON = new JsonObjectRequest(Request.Method.GET, url_weather, null, new Response.Listener<JSONObject>()
                    {
                        public void onResponse(JSONObject response) {

                            try{

                                //storing values for card 1
                                JSONObject tempVar = response.getJSONObject("currently");
                                String icon = tempVar.getString("icon");
                                Float tempValue = Float.parseFloat(tempVar.getString("temperature"));
                                String temperature = String.valueOf(Math.round(tempValue));
                                String summary = tempVar.getString("summary");

                                //storing values for card 2
                                String humidity = String.valueOf(Math.round(Float.parseFloat(tempVar.getString("humidity"))*100));
                                String wind = tempVar.getString("windSpeed");
                                String visibility = tempVar.getString("visibility");
                                String pressure = tempVar.getString("pressure");

                                if(icon.equals("clear-day")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_sunny, 0, 0, 0);
                                }

                                if(icon.equals("clear-night")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_night, 0, 0, 0);
                                }

                                if(icon.equals("rain")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_rainy, 0, 0, 0);
                                }

                                if(icon.equals("sleet")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_snowy_rainy, 0, 0, 0);
                                }

                                if(icon.equals("snow")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_snowy, 0, 0, 0);
                                }

                                if(icon.equals("wind")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_windy_variant, 0, 0, 0);
                                }

                                if(icon.equals("fog")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_fog, 0, 0, 0);
                                }

                                if(icon.equals("cloudy")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_cloudy, 0, 0, 0);
                                }

                                if(icon.equals("partly-cloudy-night")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_night_partly_cloudy, 0, 0, 0);
                                }

                                if(icon.equals("partly-cloudy-day")){
                                    textView.setCompoundDrawablesWithIntrinsicBounds( R.drawable.weather_partly_cloudy, 0, 0, 0);
                                }

                                //storing values for card 3
                                JSONObject weekly = response.getJSONObject("daily");
                                JSONArray daily = weekly.getJSONArray("data");

                                Log.d("outside for loop:",String.valueOf(daily.length()));

                                for(int i=0;i<daily.length();i++){

                                    JSONObject current = daily.getJSONObject(i);

                                    //storing date value by converting the string value
                                    Long timestamp = current.getLong("time");
                                    Date day_of_week = new Date(timestamp*1000);
                                    String pattern = "MM/dd/YYYY";
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                                    String weekDay = simpleDateFormat.format(day_of_week);
                                    currentDate[i] = weekDay;

                                    //storing resource ID of the drawable image
                                    String drawableName = "";
                                    //Resources r = getResources();
                                    Log.d("in on response:",drawableName);
                                    if(current.getString("icon").equals("clear-day")){
                                        drawableName = "weather_sunny";
                                    }

                                    if(current.getString("icon").equals("clear-night")){
                                        drawableName = "weather_night";
                                    }

                                    if(current.getString("icon").equals("rain")){
                                        drawableName = "weather_rainy";
                                    }

                                    if(current.getString("icon").equals("sleet")){
                                        drawableName = "weather_snowy_rainy";
                                    }

                                    if(current.getString("icon").equals("snow")){
                                        drawableName = "weather_snowy";
                                    }

                                    if(current.getString("icon").equals("wind")){
                                        drawableName = "weather_windy_variant";
                                    }

                                    if(current.getString("icon").equals("fog")){
                                        drawableName = "weather_fog";
                                    }

                                    if(current.getString("icon").equals("cloudy")){
                                        drawableName = "weather_cloudy";
                                    }

                                    if(current.getString("icon").equals("partly-cloudy-night")){
                                        drawableName = "weather_night_partly_cloudy";

                                    }

                                    if(current.getString("icon").equals("partly-cloudy-day")){
                                        drawableName = "weather_partly_cloudy";
                                    }

                                    //icon ID store

                                    int drawableId = getResId(drawableName, R.drawable.class);
                                    Log.d("resource id:",String.valueOf(drawableId));
                                    iconId[i] = drawableId;

                                    //storing minimum and maximum temperatures rounded off to two places
                                    Float tempMin = Float.parseFloat(current.getString("temperatureLow"));
                                    minTemperature[i] = (String.valueOf(Math.round(tempMin)));

                                    Float tempMax = Float.parseFloat(current.getString("temperatureHigh"));
                                    maxTemperature[i] = (String.valueOf(Math.round(tempMax)));


                                }//end of for loop

                                for(int i=0;i<iconId.length;i++){
                                    Log.d("icon ID values:",iconId[i].toString());
                                }

                                Log.d("setting list:",weeklyList.toString());
                                listView.setAdapter(weeklyList);
                                textView.setText(temperature);
                                degree.setText("o");
                                symbol.setText("F");
                                info.setText(summary);
                                humidView.setText(humidity+"%");
                                windView.setText(wind+" mph");
                                visibleView.setText(visibility+" km");
                                pressureView.setText(pressure+" mb");


                            }//end of try

                            catch(JSONException e){
                                //TODO:handle error
                            }//end of catch

                        }//end of onResponse
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            textView.setText("error:"+error);
                        }
                    });

                    location.setText(address);

                    cardView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), DetailedWeatherActivity.class);
                            intent.putExtra("CITY",city);
                            intent.putExtra("STATE",state);
                            intent.putExtra("COUNTRY",country);
                            intent.putExtra("LATITUDE",json_lat);
                            intent.putExtra("LONGITUDE",json_lon);
                            startActivity(intent);
                        }
                    });

                    queue.add(weatherJSON);
                }//end of outer try block

                catch(JSONException e){
                    //handle error
                    textView.setText(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
            }
        });
        queue.add(jsonObjectRequest);

    }//end of onViewCreated

    public static int getResId(String resName, Class<?> c) {

        try {
            Log.d("from getResId:",resName);
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }//end of getResId


}//end of class
