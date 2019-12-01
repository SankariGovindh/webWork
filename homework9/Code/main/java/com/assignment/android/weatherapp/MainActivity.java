package com.assignment.android.weatherapp;

import java.lang.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();

        //setting the toolbar for the app
        Toolbar weatherAppToolBar = findViewById(R.id.app_toolbar);
        setSupportActionBar(weatherAppToolBar);
        getSupportActionBar().setTitle("WeatherApp");
        weatherAppToolBar.setTitleTextColor(0xFFFFFFFF);

        //textView declaration of card1
        final TextView textView = findViewById(R.id.temperature); //temperature + icon
        final TextView degree = findViewById(R.id.degree); //degree sign
        final TextView symbol = findViewById(R.id.symbol); //fahrenheit symbol
        final TextView info = findViewById(R.id.summary); //summary of the weather
        final TextView location = findViewById(R.id.location); //location details + information icon
        final RequestQueue queue = Volley.newRequestQueue(this);

        //textView declaration of card2
        final TextView humidView = findViewById(R.id.humidity);
        final TextView windView = findViewById(R.id.wind);
        final TextView visibleView = findViewById(R.id.visibility);
        final TextView pressureView = findViewById(R.id.pressure);

        final String currentDate[] = new String[8] ;
        final Integer iconId[] = new Integer[8];
        final String minTemperature[] = new String[8];
        final String maxTemperature[] = new String[8];
        final ListView listView = findViewById(R.id.weekly_table);
        final CustomListAdapter weeklyList = new CustomListAdapter(this,currentDate,iconId, minTemperature,maxTemperature);

        //call IP-API to get the current location and store the JSON
        String url = "http://ip-api.com/json";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {

                try{
                    final String json_lat = response.getString("lat");
                    final String json_lon = response.getString("lon");

                    //storing values to display in the end of the card1
                    final String country = response.getString("countryCode");
                    final String city = response.getString("city");
                    final String state = response.getString("region");

                    //code block to make backend call to fetch weather data
                    String url_weather = "http://nodeapp571.us-west-1.elasticbeanstalk.com/currentLocationCall?latitude="+json_lat+"&longitude="+json_lon;
                    Log.d("url:",url_weather);
                    final JsonObjectRequest weatherJSON = new JsonObjectRequest(Request.Method.GET, url_weather, null, new Response.Listener<JSONObject>()
                    {
                        public void onResponse(JSONObject response) {

                            Log.d("outside the try block:","hello");

                            try{
                                Log.d("outside for loop:","hello");

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
                                    Date tempDate = new Date(Long.parseLong(current.getString("time")));
                                    currentDate[i] = (String.valueOf(tempDate));

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

                        location.setText(city+","+state+","+country);
                        Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.information_outline,null);
                        img.setBounds(0,0,60,60);
                        location.setCompoundDrawables(null, null, img, null );

                        CardView cardView = findViewById(R.id.card1);
                        cardView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, DetailedWeatherActivity.class);
                                intent.putExtra("CITY",city);
                                Log.d("put city done",city);
                                intent.putExtra("STATE",state);
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

    }//end of onCreate method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_place).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }//searchView

    //main activity loses focus and the search gains the focus
    @Override
    public boolean onSearchRequested() {

        //reading the input from the user

        //code the autocomplete logic
        return super.onSearchRequested();

        //setOnDismissListener()
        //setOnCancelListener()

    }//end of onSearchRequested

    protected void requestPermission(){

        final int REQUEST_LOCATION = 1;

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            return ;
        }//end of if

        else{

            if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                Toast.makeText(this,"This application requires access to location.", Toast.LENGTH_SHORT).show();
            }//condition to display the need for permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION); //code to request for the location access

        }//code block for enabling the permission

    }//end of requestPermission

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
}
