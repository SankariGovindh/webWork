package com.assignment.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import org.json.JSONObject;

public class DetailedWeatherActivity extends AppCompatActivity {


    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.information_outline,
            R.drawable.weather_sunny,
            R.drawable.weather_sunny
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);

        Intent intent = getIntent();
        final String city = intent.getStringExtra("CITY");
        final String state = intent.getStringExtra("STATE");
        final String json_lat = intent.getStringExtra("LATITUDE");
        final String json_lon = intent.getStringExtra("LONGITUDE");

        //displaying the back button and enabling navigation back to the home page
        Toolbar detailToolBar = findViewById(R.id.details_toolbar);
        setSupportActionBar(detailToolBar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(city+","+state);

        //displaying the tabs below the toolbar
        viewPager =  findViewById(R.id.pager);
        tabLayout =  findViewById(R.id.tabs);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //reading data from the darkSky API
        final RequestQueue queue = Volley.newRequestQueue(this);

        //calling the backend darkSky API
        String url = "http://nodeapp571.us-west-1.elasticbeanstalk.com/currentLocationCall?latitude="+json_lat+"&longitude="+json_lon;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {

                //passing data to all the fragments
                adapter.addFragment(new TabFragment(response),"Today");
                adapter.addFragment(new WeeklyFragment(response), "Weekly");
                adapter.addFragment(new PhotoFragment(city),"Photos");

                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                setTabIcons();

            }//end of onResponse
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //TODO:handle error
            }
        });

        queue.add(jsonObjectRequest);

    }//end of onCreate

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void setTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
}
