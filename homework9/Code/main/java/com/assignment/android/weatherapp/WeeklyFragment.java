package com.assignment.android.weatherapp;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
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

        //storing array data from JSONObject
        try {
            JSONObject weekly = weatherData.getJSONObject("daily");
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
            Log.d("value of minarray:",minArray[i].toString());
            minTemperature.add(new Entry(i,minArray[i]));
            maxTemperature.add(new Entry(i,maxArray[i]));

        }//end of for loop


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(maxTemperature,"maxTemperature");
        lineDataSet1.setColor(Color.YELLOW);

        LineDataSet lineDataSet2 = new LineDataSet(minTemperature,"minTemperature");
        lineDataSet2.setColor(Color.BLUE);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        lineChart.setData(new LineData(lineDataSets)); //assigning the data to the actual chart
        lineChart.setVisibleXRangeMaximum(65f); //viewPort*/


    }//end of onViewCreated

}//end of class WeeklyFragment


