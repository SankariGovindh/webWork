package com.assignment.android.weatherapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class PhotoFragment extends Fragment {

    private TextView textView;
    public String location;
    private RecyclerView mRecyclerView;
    private recycleViewAdapter mRecycleAdapter;
    private ArrayList<recycleView> mImageList;
   String images;


    public PhotoFragment(String city){
        location = city;
    }//end of PhotoFragment constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //calling the google image API to fetch image
        final RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://www.googleapis.com/customsearch/v1?q=Los%20Angeles&cx=000066031221280904308:1qngeof0co6&imgSize=large&imgType=news&searchType=image&key=AIzaSyCH2oPI34OIf0_qlzyL9zTIMfBUxEgZur4";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                textView.setText(response.toString());
                Log.d("response logging",response.toString());

                try{
                    //parsing the data from the JSON
                    JSONArray itemsArray = response.getJSONArray("items");

                    //looping through the array to store the values
                    for(int i=0;i<itemsArray.length();i++){
                        JSONObject items= itemsArray.getJSONObject(i);
                        images = items.getString("link");
                        mImageList.add(new recycleView(images)); //instantiating the recycleView object
                    }//end of for loop

                    mRecycleAdapter = new recycleViewAdapter(getActivity().getApplicationContext(),mImageList);
                    mRecyclerView.setAdapter(mRecycleAdapter); //linking the adapter and the view

                }//end of try block

               catch(JSONException e){
                    //TODO
               }

            }//end of onResponse
        },new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                //TODO:handle error
            }
        });

        queue.add(jsonObjectRequest);

    }//end of onCreate

    //below set of code for the display of the tab contents
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.photo_layout, container, false);
    }//end of onCreateView

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textView);
        textView.setText("Fragment Photo");

        //creating reference for the recycler view
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mImageList = new ArrayList<>();

    }

}
