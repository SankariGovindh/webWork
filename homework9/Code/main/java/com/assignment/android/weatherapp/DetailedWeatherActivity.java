package com.assignment.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailedWeatherActivity extends AppCompatActivity {

    public String city;
    public String state;
    public String temperature;
    private ViewPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.calendar_today,
            R.drawable.weather_sunny,
            R.drawable.google_photos
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_weather);

        Intent intent = getIntent();
        city = intent.getStringExtra("CITY");
        state = intent.getStringExtra("STATE");
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

                //storing temperature value
                try{
                    JSONObject weatherTemp = response.getJSONObject("currently");
                    String tempData = weatherTemp.getString("temperature");
                    Float tempValue = Float.parseFloat(tempData);
                    temperature = String.valueOf(Math.round(tempValue));
                }

                catch(JSONException e){
                    //TODO
                }


                //passing data to all the fragments
                adapter.addFragment(new TabFragment(response),"Today");
                adapter.addFragment(new WeeklyFragment(response), "Weekly");
                adapter.addFragment(new PhotoFragment(city),"Photos");

                viewPager.setAdapter(adapter);
                tabLayout.setupWithViewPager(viewPager);
                setTabIcons();
                tabLayout.setOnTabSelectedListener(
                        new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                super.onTabSelected(tab);
                                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tabSelectedIconColor);
                                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {
                                super.onTabUnselected(tab);
                                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.tabUnselectedIconColor);
                                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {
                                super.onTabReselected(tab);
                            }
                        }
                );

            }//end of onResponse
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //TODO:handle error
            }
        });

        queue.add(jsonObjectRequest);

    }//end of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tweet_place:
                String url = "https://twitter.com/intent/tweet?text=Check Out "+city+","+state+"'s Weather! It is "+temperature+"F! CSCI571WeatherSearch";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

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
