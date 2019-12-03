package com.assignment.android.weatherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class WeeklyFragment extends Fragment  {

    public JSONObject weatherData;
    public LineChart lineChart;
    public TextView textView;
    Float[] minArray = new Float[8];
    Float[] maxArray = new Float[8];

    public WeeklyFragment(JSONObject weatherJSON){
        weatherData = weatherJSON;
    }//end of constructor WeeklyFragment

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }//end of onCreate


    //below set of code for the display of the tab contents
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.weekly_layout, container, false);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lineChart = view.findViewById(R.id.temperatureChart);
        textView = view.findViewById(R.id.textView);

        //storing array data from JSONObject
        try {

            JSONObject weekly = weatherData.getJSONObject("daily");

            //storing the values for the card
            String summary = weekly.getString("summary");
            String icon = weekly.getString("icon");

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

            textView.setText(summary);


            JSONArray daily = weekly.getJSONArray("data");
            for (int i = 0; i < daily.length(); i++) {
                JSONObject current = daily.getJSONObject(i);
                minArray[i] = Float.parseFloat(current.getString("temperatureLow"));
                maxArray[i] = Float.parseFloat(current.getString("temperatureHigh"));
            }
        } catch (JSONException e) {
            //TODO:handle error
        }

        ArrayList<Entry> minTemperature = new ArrayList<>();
        ArrayList<Entry> maxTemperature = new ArrayList<>();

        for(int i=0;i<minArray.length;i++){
            Log.d("outside for loop:",minArray[i].toString());
            Log.d("length of minarray:",String.valueOf(minArray.length));
        }//end of for loop


        //adding entry points
        for(int i=0; i< minArray.length; i++){

            Log.d("in for loop-entry points:",String.valueOf(minArray.length));
            Log.d("value of min array:",minArray[i].toString());
            minTemperature.add(new Entry(i,minArray[i]));
            maxTemperature.add(new Entry(i,maxArray[i]));

        }//end of for loop


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>(); //array to hold the value for the chart

        LineDataSet lineDataSet2 = new LineDataSet(maxTemperature,"Maximum Temperature");
        lineDataSet2.setColor(Color.YELLOW);

        LineDataSet lineDataSet1 = new LineDataSet(minTemperature,"Minimum Temperature");
        lineDataSet1.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets)); //assigning the data to the actual chart
        lineChart.getXAxis().setTextColor(0xffffffff);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.getAxisLeft().setTextColor(0xffffffff);
        lineChart.getAxisRight().setTextColor(0xffffffff);

        //customizing the legend values
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(0xffffffff);
        legend.setTextSize(14);

    }//end of onViewCreated

}//end of class WeeklyFragment


