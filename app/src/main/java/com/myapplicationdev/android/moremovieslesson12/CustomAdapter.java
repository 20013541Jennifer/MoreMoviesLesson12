package com.myapplicationdev.android.moremovieslesson12;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Movie> movies;

    TextView tvTitle, tvGenre, tvRating, tvYears;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects){
        super(context,resource,objects);

        parent_context = context;
        layout_id = resource;
        movies = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        tvTitle = rowView.findViewById(R.id.tvTitle);
        tvGenre = rowView.findViewById(R.id.tvGenree);
        tvYears = rowView.findViewById(R.id.tvYearr);
        ImageView ivRating = rowView.findViewById(R.id.imageView);

        // Obtain the Android Version information based on the position
        Movie currentSong = movies.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentSong.getTitle());
        tvGenre.setText((currentSong.getGenre()));
        tvYears.setText(currentSong.getYear()+"");

        String rating = currentSong.getRating();

        if (rating.equalsIgnoreCase("G")){
            ivRating.setImageResource(R.drawable.rating_g);
        } else if (rating.equalsIgnoreCase("PG")){
            ivRating.setImageResource(R.drawable.rating_pg);
        }else if (rating.equalsIgnoreCase("NC16")){
            ivRating.setImageResource(R.drawable.rating_nc16);
        }else if (rating.equalsIgnoreCase("M18")){
            ivRating.setImageResource(R.drawable.rating_m18);
        }else if (rating.equalsIgnoreCase("R21")){
            ivRating.setImageResource(R.drawable.rating_r21);
        }

        return rowView;
    }
}
