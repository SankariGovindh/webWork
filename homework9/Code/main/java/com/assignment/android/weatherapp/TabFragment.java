package com.assignment.android.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class TabFragment extends Fragment {


    public JSONObject weatherData;

   public TabFragment(JSONObject weatherJSON){
       weatherData = weatherJSON;
   }//end of TabFragment constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    //below set of code for the display of the tab contents
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView wind = view.findViewById(R.id.todayWind);
        final TextView press  = view.findViewById(R.id.todayPressure);
        final TextView precip = view.findViewById(R.id.todayPrecip);
        final TextView temp =  view.findViewById(R.id.todayTemp);
        final TextView summary = view.findViewById(R.id.summaryDetails);
        final TextView hum = view.findViewById(R.id.todayHumidity);
        final TextView vis = view.findViewById(R.id.todayVis);
        final TextView cover = view.findViewById(R.id.todayCover);
        final TextView oz = view.findViewById(R.id.todayOzone);
        final ImageView image = view.findViewById(R.id.todaySummary);

        try{
            JSONObject weatherTemp = weatherData.getJSONObject("currently");
            String windSpeed = weatherTemp.getString("windSpeed");
            String pressure = weatherTemp.getString("pressure");
            String precipitation = weatherTemp.getString("precipIntensity");
            String temperature = weatherTemp.getString("temperature");
            String icon = weatherTemp.getString("icon");
            String humidity = String.valueOf(Math.round(Float.parseFloat(weatherTemp.getString("humidity"))*100));
            String visibility = weatherTemp.getString("visibility");
            String cloudCover = weatherTemp.getString("cloudCover");
            String ozone = weatherTemp.getString("ozone");

            //setting the values
            wind.setText(windSpeed+" mph");
            press.setText(pressure+" mb");
            precip.setText(precipitation+" mmph");
            Float tempValue = Float.parseFloat(temperature);
            String tempVal = String.valueOf(Math.round(tempValue));
            temp.setText(tempVal);
            hum.setText(humidity+"%");
            vis.setText(visibility+" km");
            cover.setText(cloudCover+"%");
            oz.setText(ozone+" DU");


            if(icon.equals("clear-day")){
                image.setImageResource(R.drawable.weather_sunny);
                summary.setText("clear day");
                image.setColorFilter(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.yellow), android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            if(icon.equals("clear-night")){
                image.setImageResource(R.drawable.weather_night);
                summary.setText("clear night");
            }

            if(icon.equals("rain")){
                image.setImageResource(R.drawable.weather_rainy);
                summary.setText("rain");
            }

            if(icon.equals("sleet")){
                image.setImageResource(R.drawable.weather_snowy_rainy);
                summary.setText("sleet");
            }

            if(icon.equals("snow")){
                image.setImageResource(R.drawable.weather_snowy);
                summary.setText("snow");
            }

            if(icon.equals("wind")){
                image.setImageResource(R.drawable.weather_windy_variant);
                summary.setText("wind");
            }

            if(icon.equals("fog")){
                image.setImageResource(R.drawable.weather_fog);
                summary.setText("fog");
            }

            if(icon.equals("cloudy")){
                image.setImageResource(R.drawable.weather_cloudy);
                summary.setText("cloudy");
            }

            if(icon.equals("partly-cloudy-night")){
                image.setImageResource(R.drawable.weather_night_partly_cloudy);
                summary.setText("partly cloudy night");
            }

            if(icon.equals("partly-cloudy-day")){
                image.setImageResource(R.drawable.weather_partly_cloudy);
                summary.setText("partly cloudy day");
            }

        }

        catch(JSONException e){
            //TODO
        }

    }//end of onViewCreated

}

