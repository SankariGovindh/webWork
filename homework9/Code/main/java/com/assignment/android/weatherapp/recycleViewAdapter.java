package com.assignment.android.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class recycleViewAdapter extends RecyclerView.Adapter<recycleViewAdapter.recycleViewHolder>
{

    private Context context;
    private ArrayList<recycleView> recycleList;

    public recycleViewAdapter(Context viewContext,ArrayList<recycleView> imgList){
        context = viewContext;
        recycleList = imgList;

    }//end of constructor

    @NonNull
    @Override
    public recycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_layout, parent, false);
        return new recycleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull recycleViewHolder holder, int position) {
        //getting the current item of the arrayList and the corresponding URL of the image
        recycleView currentItem = recycleList.get(position);
        String imageUrl = currentItem.getImageUrl();

        //using Picasso to pull the image from the internet
        Picasso.with(context).load(imageUrl).fit().centerInside().into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return recycleList.size();
    }

    //view-holder class
    public class recycleViewHolder extends RecyclerView.ViewHolder
    {
        //creating reference for the imageView
        public ImageView imageView;
        public recycleViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }//end of recycleViewHolder


}//end of class
