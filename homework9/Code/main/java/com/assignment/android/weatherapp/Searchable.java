package com.assignment.android.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Searchable extends AppCompatActivity {
    String city;
    String state;
    String country;
    String flag="remove";
    Set<String> citySet = new HashSet<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        final String query = intent.getStringExtra("ADDRESS");

        //toolbar displaying the current location searched with a back navigation arrow
        Toolbar searchToolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(searchToolbar);
        getSupportActionBar().setTitle(query);
        searchToolbar.setTitleTextColor(0xFFFFFFFF);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final String[] details = query.split(",");
        city = details[0];
        if(details.length == 3){
            Log.d("in searchable",details[0]);
            //instantiating the default summary fragment
            Fragment summary = new summaryFragment(details[0],details[1],details[2]); //pass the JSONObject response to the next fragment
            //addToBackStack - to allow backward navigation
            getSupportFragmentManager().beginTransaction().replace(R.id.searchable_frame, summary, summary.getClass().getSimpleName()).addToBackStack(null).commit();
        }

        else{
            Log.d("in searchable",details[0]);
            //instantiating the default summary fragment
            Fragment summary = new summaryFragment(details[0],details[1]); //pass the JSONObject response to the next fragment
            //addToBackStack - to allow backward navigation
            getSupportFragmentManager().beginTransaction().replace(R.id.searchable_frame, summary, summary.getClass().getSimpleName()).addToBackStack(null).commit();
        }

        //on click of the Fav icon
        final FloatingActionButton fab = findViewById(R.id.fav_icon);
        fab.setOnClickListener(new View.OnClickListener() {

            //adding to the shared preference list
            final String PERSIST_FAVORITES = "favoriteLists";
            SharedPreferences sp = getSharedPreferences(PERSIST_FAVORITES,MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            @Override
            public void onClick(View view) {
                if(flag.equals("remove")){

                    String message = query + " was added to favorites";
                    //adding city to the sharedPreference
                    citySet.add(city);
                    editor.putStringSet("city", citySet);
                    editor.commit();

                    //display toast message on click of the fav_icon
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    //setting flag to add
                    flag = "add";

                    //toggling to remove icon
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.remove_fav));
                }//end of if

                else{

                    String message = query + " was removed from favorites";

                    //removing city from favorites
                    citySet.remove(city);
                    editor.remove(city);
                    editor.commit();
                    //setting to flag to remove
                    flag = "remove";

                    //toast message
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                    //toggling to remove icon
                    fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.add_fav));
                }

            }
        });

    }//end of onCreate method


    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    //display the progress bar before pulling the data from the backend

}//end of class Searchable
