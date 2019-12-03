package com.assignment.android.weatherapp;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private ViewPager firstViewPager;
    private TabLayout mTabLayout;
    private FavPageAdapter dotAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final RequestQueue queue = Volley.newRequestQueue(this);
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTabLayout = findViewById(R.id.tab_layout);
        firstViewPager = findViewById(R.id.view_pager);
        dotAdapter = new FavPageAdapter(getSupportFragmentManager()); //instantiating the dotAdapter

        requestPermission();

        //setting the toolbar for the app
        Toolbar weatherAppToolBar = findViewById(R.id.app_toolbar);
        setSupportActionBar(weatherAppToolBar);
        getSupportActionBar().setTitle("WeatherApp");
        weatherAppToolBar.setTitleTextColor(0xFFFFFFFF);

        //call IP-API to get the current location and store the JSON
        String url = "http://ip-api.com/json";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            public void onResponse(JSONObject response) {
            Log.d("before try ip-api",response.toString());
                try{

                    final String city = response.getString("city");
                    String state = response.getString("region");
                    String country = response.getString("countryCode");

                    //instantiating the default summary fragment
                    dotAdapter.addFragment(new summaryFragment(city,state,country),"DEFAULT");
                    firstViewPager.setOffscreenPageLimit(1); //# of fragments to be loaded into the memory
                    firstViewPager.setAdapter(dotAdapter); //linking the viewPager and the FavPageAdapter
                    firstViewPager.setCurrentItem(0); //setting the current item to be on top of the stack
                    mTabLayout.setupWithViewPager(firstViewPager, true); //linking the viewPager and the tab layout

                    //adding functionality for removing favorite
                    final FloatingActionButton fab = findViewById(R.id.fab);
                    fab.setOnClickListener(new View.OnClickListener() {

                           String message = "The city is removed from favorites";

                           @Override
                           public void onClick(View view) {

                                 //removing the fragment from the view
                                 int position = firstViewPager.getCurrentItem();
                                 Log.d("position value when removed:",String.valueOf(position));
                                 dotAdapter.removeFragment(position);
                                 dotAdapter.notifyDataSetChanged();

                               //displaying the toast message
                                 Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                           }//end of onClick
                    });
                   /*Fragment summary=new summaryFragment(city,state,country);
                   getSupportFragmentManager().beginTransaction().replace(R.id.fragment_frame, summary, summary.getClass().getSimpleName()).addToBackStack(null).commit();*/

                }//end of try block

                catch(JSONException e){
                    //TODO:handle error
                }//end of catch block
            }//end of onResponse
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //TODO
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
        searchView.setQueryHint("Search...");
        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate",null,null);
        View searchPlate = searchView.findViewById(searchPlateId);
        if(searchPlate != null){
            searchPlate.setBackgroundColor(Color.DKGRAY);
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("Search...", null, null);
            TextView searchText = searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.WHITE);
                searchText.setHintTextColor(Color.DKGRAY);
            }
        }//end of outer if

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
              @Override
              public boolean onQueryTextSubmit(String query) {
                  //calling the Searchable activity
                  Intent intent = new Intent(getApplicationContext(), Searchable.class);
                  intent.putExtra("ADDRESS",query);
                  startActivity(intent);
                  return true;
              }//end of onQueryTextSubmit

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }//end of onCreateOptionsMenu

    @Override
    public void onResume(){


        //if city not in the favorites list then add the fragment

        final String PERSIST_FAVORITES = "favoriteLists";
        SharedPreferences sp = getSharedPreferences(PERSIST_FAVORITES,MODE_PRIVATE);
        //on clicking the back button in the searchable, read the values from the sharedPreference and add an additional fragment
        Set<String> citySet = sp.getStringSet("city",null);

        for(String city: citySet){

                Fragment fragment = getSupportFragmentManager().findFragmentByTag(city);
                if(fragment == null){
                    Log.d("printing the city in the set:",city);
                    dotAdapter.addFragment(new summaryFragment(city),city);
                    dotAdapter.notifyDataSetChanged();
                }

        }//end of for loop

        super.onResume();

    }//end of onResume

    //onActivityResult() callback to pull the data sent from the searchable activity

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
}
