package com.assignment.android.weatherapp;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomListAdapter extends ArrayAdapter {

    //referencing the activity
    private final Activity context;

    //referencing the date in the week
    private final String[] dateArray;

    //referencing the weather icons
    private final Integer[] iconArray;

    //referencing the min and max temperatures
    private final String[] minTempArray;
    private final String[] maxTempArray;

    public CustomListAdapter(Activity context,String[] dateArrayParam,Integer[] iconArrayParam, String[] minTempArrayParam,
                             String[] maxTempArrayParam){
        super(context,R.layout.activity_listview, minTempArrayParam);
        this.context = context;
        this.dateArray = dateArrayParam;
        this.iconArray = iconArrayParam;
        this.minTempArray = minTempArrayParam;
        this.maxTempArray = maxTempArrayParam;

    }//end of constructor

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_listview,null,true);

        TextView dateField = (TextView) rowView.findViewById(R.id.date);
        ImageView weatherIcon = (ImageView) rowView.findViewById(R.id.weatherIcon);
        TextView minTempField = (TextView) rowView.findViewById(R.id.minTemp);
        TextView maxTempField = (TextView) rowView.findViewById(R.id.maxTemp);

        //setting the values passed
        dateField.setText(dateArray[position]);
        weatherIcon.setImageResource(iconArray[position]);
        minTempField.setText(minTempArray[position]);
        maxTempField.setText(maxTempArray[position]);

        return rowView;

    }//end of getView



}
